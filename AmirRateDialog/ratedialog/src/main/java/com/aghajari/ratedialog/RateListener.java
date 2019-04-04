package com.aghajari.ratedialog;

import android.view.ViewGroup;

public interface RateListener{
	 void onShow();
	 void onHide();
	 void onChange(int NewPosition);
	 void onSubmit(int Position);
	 void onCancel(int Position);
	 void onBind(ViewGroup Dialog);
}