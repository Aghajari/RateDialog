package com.aghajari.ratedialog;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.ArrayList;

public class RateDialogLayout extends ViewGroup{
  public static float scale = 0.0F;
  private static float deviceScale = 0.0F;
  public static final int LEFT = 0;
  public static final int RIGHT = 1; 
  public static final int BOTH = 2; 
  public static final int TOP = 0; 
  public static final int BOTTOM = 1; 
  public static boolean disableAccessibility = false;
  
  public RateDialogLayout(Context context) { super(context); }
  public RateDialogLayout(Context context, AttributeSet attrs, int defStyle) {super(context,attrs,defStyle);}
  
  public static void setDeviceScale(float scale) { 
	  deviceScale = scale; 
	}
  
  public static void setUserScale(float userScale) {
    if (Float.compare(deviceScale, userScale) == 0) {
      scale = 1.0F;
    } else
      scale = deviceScale / userScale;
  }
  
  public static float getDeviceScale() { return deviceScale; }
  
  protected void onLayout(boolean changed, int l, int t, int r, int b)
  {
    int count = getChildCount();
    for (int i = 0; i < count; i++) {
      View child = getChildAt(i);
      if (child.getVisibility() != 8) {
        if ((child.getLayoutParams() instanceof LayoutParams)) {
          LayoutParams lp = 
            (LayoutParams)child.getLayoutParams();
          child.layout(lp.left, lp.top, 
            lp.left + child.getMeasuredWidth(), 
            lp.top + child.getMeasuredHeight());
        }
        else {
          //child.layout(0, 0, child.getLayoutParams().width, child.getLayoutParams().height);
        }
      }
    }
  }
  

  public void addChildrenForAccessibility(ArrayList<View> c)
  {
    if (disableAccessibility)
      return;
    super.addChildrenForAccessibility(c);
  }
  
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
  {
    measureChildren(widthMeasureSpec, heightMeasureSpec);
    setMeasuredDimension(resolveSize(this.getLayoutParams().width, widthMeasureSpec), 
      resolveSize(this.getLayoutParams().height, heightMeasureSpec));
  }
  
  public static class LayoutParams extends ViewGroup.LayoutParams {
    public int left;
    public int top;
    
    public LayoutParams(int left, int top, int width, int height) {
    super(width,height);
    this.width = width;
    this.height = height;
      this.left = left;
      this.top = top;
    }
    
    
    public LayoutParams() {
    	super(0,0); 
    }
    
    public void setFromUserPlane(int left, int top, int width, int height)
    {
      this.left = Math.round(left * RateDialogLayout.scale);
      this.top = Math.round(top * RateDialogLayout.scale);
      this.width = (width > 0 ? Math.round(width * RateDialogLayout.scale) : width);
      this.height = (height > 0 ? Math.round(height * RateDialogLayout.scale) : height);
    }
  }
  
 
}