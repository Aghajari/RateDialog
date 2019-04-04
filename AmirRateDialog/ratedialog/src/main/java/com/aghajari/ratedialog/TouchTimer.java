package com.aghajari.ratedialog;
import android.os.Handler;

class TouchTimer {
  Handler handler;
  private long interval;
  private boolean enabled = false;
  private static int relevantTimer = 0;
  private  TickTack tt;
  public TouchTimer(long Interval) {
	  interval = Interval; 
	  handler = new Handler();
  }
  
  public boolean getEnabled(){
    return enabled;
  }
  

  public void setInterval(long Interval){
    if (interval == Interval) return;
    interval = Interval;
    if (enabled) {
      stopTicking();
      startTicking();
    }
  }
  
  public long getInterval() { 
	  return interval; 
}
  
  private void startTicking() {
    tt = new TickTack(relevantTimer, this);
    handler.postDelayed(tt, interval);
  }
  
  public void setEnabled(boolean Enabled) {
    if (Enabled == enabled)
      return;
    if (Enabled) {
      startTicking();
    }else {
      stopTicking();
    }
    enabled = Enabled; 
    }
  
  
  static class TickTack implements Runnable { 
	 private TouchTimer timer;
    
    public TickTack(int currentTimer, TouchTimer timer) { 
    this.currentTimer = currentTimer;
      this.timer = timer;
    }
    
    public void run() {
    	TouchTimer parentTimer = timer;
      if ((parentTimer == null) || (currentTimer != relevantTimer)) return;
      timer.setEnabled(false);
    }
    
    private final int currentTimer;
  }
  
  private void stopTicking() {
    relevantTimer += 1;
    if (tt!=null) {
    	tt.timer=null;
    	tt=null;
    }
  }
}