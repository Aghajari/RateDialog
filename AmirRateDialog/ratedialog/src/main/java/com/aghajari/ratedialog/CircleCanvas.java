package com.aghajari.ratedialog;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;

 class CircleCanvas
{

  public Canvas canvas;
  public Paint paint;
  public PorterDuffXfermode eraseMode;
  
  @SuppressWarnings("deprecation")
 public CircleCanvas(View Target, int w, int h) {
	    paint = new Paint();
	    Bitmap bitmap = Bitmap.createBitmap(getW(w), getH(h), Bitmap.Config.ARGB_8888);
	    BitmapDrawable bd = new BitmapDrawable(bitmap);
	    canvas = new Canvas(bitmap);
	    if (Target.getBackground() != null) {
	      Target.getBackground().setBounds(0, 0, getW(w), getH(h));
	      Target.getBackground().draw(canvas);
	    }
	    Target.setBackgroundDrawable(bd);
  }
  
  private int getW (int w) {
	  if (w<=0) return 1;
	  return w;
  }
  
  private int getH (int h) {
	  if (h<=0) return 1;
	  return h;
  }
  
  public void setXfermode(int color) {
    if (color != 0) {
      paint.setXfermode(null);
      return;
    }
    if (eraseMode == null)
      eraseMode = new PorterDuffXfermode(android.graphics.PorterDuff.Mode.CLEAR);
    paint.setXfermode(eraseMode);
  }
  

  public void drawCircle(float x, float y, float Radius, int Color, boolean Filled, float StrokeWidth)
  {
	setXfermode(Color);
    paint.setColor(Color);
    paint.setStyle(Filled ? Paint.Style.FILL : Paint.Style.STROKE);
    paint.setStrokeWidth(StrokeWidth);
    canvas.drawCircle(x, y, Radius, paint);
  }
 
}