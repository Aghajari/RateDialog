package com.aghajari.ratedialog;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;

class ViewUtils {

	  public static int getWidth(View v) { 
		  return v.getLayoutParams().width; 
	  }
	  
	  public static int getHeight(View v) {
	    return v.getLayoutParams().height;
	  }
	  
	  public static int getLeft(View v){
		  RateDialogLayout.LayoutParams lp = (RateDialogLayout.LayoutParams)v.getLayoutParams();
	    return lp.left;
	  }
	  
	  public static int getTop(View v){
		RateDialogLayout.LayoutParams lp = (RateDialogLayout.LayoutParams)v.getLayoutParams();
	    return lp.top;
	  }

	  public static void setWidth(View v,int width){
		    ViewGroup.LayoutParams lp = v.getLayoutParams();
		    lp.width = width;
		    requestLayout(v);
	   }
	  
	  public static void setHeight(View v,int height) {
	    ViewGroup.LayoutParams lp = v.getLayoutParams();
	    lp.height = height;
	    requestLayout(v);
	  }
	  
	  public static void setLeft(View v,int left) {
		  RateDialogLayout.LayoutParams lp = (RateDialogLayout.LayoutParams)v.getLayoutParams();
	    lp.left = left;
	    requestLayout(v);
	  }
	  
	  public static void setTop(View v,int top) {
	    RateDialogLayout.LayoutParams lp = (RateDialogLayout.LayoutParams)v.getLayoutParams();
	    lp.top = top;
	    requestLayout(v);
	  }
	  
	  private static void requestLayout(View v) { 
		  ViewParent parent = v.getParent();
	    if (parent != null) {
	      parent.requestLayout();
	    }
	  }
	  
	  public static int getTextWidth (TextView v) {
		return (int) MeasureStringWidth(new Paint(),v.getText().toString(),v.getTypeface(),v.getTextSize());
	  }
	  
	  private static float MeasureStringWidth(Paint paint,String Text, Typeface Typeface, float TextSize)
	  {
	    paint.setTextSize(TextSize);
	    paint.setTypeface(Typeface);
	    paint.setStrokeWidth(0.0F);
	    paint.setStyle(Paint.Style.STROKE);
	    paint.setTextAlign(Paint.Align.LEFT);
	    return paint.measureText(Text);
	  }
	  
	  public static void setClipToOutline(ViewGroup v) {
		  if (Build.VERSION.SDK_INT >= 21) {
			v.setClipToOutline(true);
		  }
	  }

	  public static void setClickEffect(View View, boolean Borderless) {
	    int[] attrs;	    
	    if (Borderless) {
	      attrs = new int[] { android.R.attr.selectableItemBackgroundBorderless };
	    } else {
	      attrs = new int[] { android.R.attr.selectableItemBackground };
	    }
	    TypedArray typedArray = View.getContext().obtainStyledAttributes(attrs);
	    int backgroundResource = typedArray.getResourceId(0, 0);
	    View.setBackgroundResource(backgroundResource);
	  }
	  	
	  public static void RemoveView(View view) {
	    if (view.getParent() instanceof ViewGroup) {
	      ViewGroup vg = (ViewGroup)view.getParent();
	      vg.removeView(view);
	    }
	  }
	  
	  public static void setElevation (View v,float e) {
		  if (Build.VERSION.SDK_INT >= 21) {
			  v.setElevation(e);
		  }
	  }
}
