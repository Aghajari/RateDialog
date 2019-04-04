package com.aghajari.ratedialog;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;


/**
 * 
 * @author Amir Hossein Aghajari
 * @version 1.04
 *
 */
public class AmirRateDialog {
	public static int TEXT_WIDTH_SCALE = -1;
	public static final int TYPE_TITLE = 0;
	public static final int TYPE_SUBMIT_BTN = 1;
	public static final int TYPE_CANCEL_BTN = 2;
	public static final int TYPE_NUMBERS = 3;
	public static final int NUMBER_10 = 10;
	public static final int NUMBER_5 = 5;
	
	private int TargetTop = 0;
	private ViewGroup Panel;
	private int Height,Width;
	private int EmojisColor,ShapeColor,CircleColor,Circle2Color,DotColor,DotBackgroundColor;
	private int Numbers=10;
	private int DefaultSelected=5;
	
	private Typeface TitleTypeface = Typeface.DEFAULT;
	private Typeface ButtonTypeface = Typeface.DEFAULT;
	private Typeface Button2Typeface = Typeface.DEFAULT;
	private Typeface NumbersTypeface= Typeface.DEFAULT;
	
	private int TitleSize = 18;
	private int ButtonBottomMargin = 0;
	private int ButtonSize = 14;
	private int Button2Size = 14;
	private int NumbersSize = 12;
	private int TitleColor = Color.BLACK;
	private Drawable ButtonBackground;
	private Drawable Button2Background;
	private int ButtonTextColor = Color.WHITE;
	private int Button2TextColor = Color.WHITE;
	private int NumbersColor = Color.BLACK;
	private CharSequence TitleText="",ButtonText="",Button2Text="";
	private int DialogColor =Color.WHITE;
	private int DialogRadius = 0;
	private int PaddingBottom= 0;
	private boolean HideOnSubmit = true;
	private boolean HideOnCancel= true;
	
	private boolean Showing =false;
	private boolean RunningAnim = false;
	private long AnimDuration = 250;
	
	private ViewGroup RateDialog;
	private ViewGroup EmojiParent;
	private ViewGroup SeekBarParent;
	private ViewGroup Selector;
	private List<Integer> Points;

	private View noaSelectorView = null;
	
	private boolean Pressing =false;
	private TouchTimer TouchTimer;
	private boolean EmojiShowing = false;
	
	private Bitmap ShapeImage;
	private int SelectedIndex = 1;
	private List<Bitmap> EmojiList;
	private long EmojiAnimation = 150;
	private List<String> NumbersText;
	private boolean Rtl = false;
	private int mposition;
	
	private boolean emojiAnimRunning = false;
	private boolean emojiAnimNext= true;
	
	private boolean ButtonsEnabled =true;
	private boolean Ripple = false;
	private int ButtonSpace = 8;
	private float ButtonShadow = 0;
	private int DotSpace = -1;
	private Drawable DotBackground = null;
	private boolean Clickable = true;
	private float lastClickX=-1;

	public  int CLICK_SENSITIVITY =-1 ;
	public  int CLICK_ANIMATION_SENSITIVITY = 18;

	private RateListener Listener = new RateListener() {
		@Override
		public void onShow() {}
		@Override
		public void onHide() {}
		@Override
		public void onChange(int NewPosition) {}
		@Override
		public void onSubmit(int Position) {}
		@Override
		public void onCancel(int Position) {}
		@Override
		public void onBind(ViewGroup Dialog) {}
	};
		
	Context context;
    private float density = 1;
    
    private android.animation.TimeInterpolator animInterpolator;
    private ViewGroup.LayoutParams lp=null;
    
	public AmirRateDialog (Context context,int TargetTop,ViewGroup Parent,RateListener Listener) {
	this.context = context;
	this.Listener = Listener;
	density = context.getResources().getDisplayMetrics().density;
	this.TargetTop = TargetTop;
	this.Panel = Parent;
	
	Height = dp(240);
	Width = getScreenWidth();
	this.Points = new ArrayList<Integer>();
	TouchTimer = new TouchTimer(100);
	EmojiList = new ArrayList<Bitmap>();
	NumbersText = new ArrayList<String>();
	ButtonSpace = dp(8);
	DialogRadius = dp(4);
	
	if (TEXT_WIDTH_SCALE==-1) {TEXT_WIDTH_SCALE=dp(18);}
	animInterpolator = new android.view.animation.OvershootInterpolator();
	if (CLICK_SENSITIVITY==-1) CLICK_SENSITIVITY=dp(6);
	}
	
	public void setAnimInterpolator (android.animation.TimeInterpolator i) {
		this.animInterpolator=i;
	}
	
	private int dp(float value) {
        if (value == 0) {
            return 0;
        }
		//return (int) Math.ceil(density * value);
		return  (int) (density * value);
    }

	static int getScreenWidth() {
	    return Resources.getSystem().getDisplayMetrics().widthPixels;
	}
	
	static int getScreenHeight() {
	    return Resources.getSystem().getDisplayMetrics().heightPixels;
	}
	


/**
 * setSize or setLayout of Dialog
 */
public void setSize (int ViewWidth,int ViewHeight) {
	Height = ViewHeight;
	Width = ViewWidth;
}


/**
 * setSize or setLayout of Dialog
 */
public void setLayout (ViewGroup.LayoutParams lp) {
	this.lp=lp;
}

public void setEmojisColor (int Color) {
	EmojisColor = Color;
}

public void setClickable (boolean value) {
		Clickable = value;
	}

public void setShapeColor (int Color) {
	ShapeColor = Color;
}

public void setBottomPadding (int Size) {
PaddingBottom = Size;
}

public void setAnimationDuration (long Duration) {
AnimDuration = Duration;
}

public void setEmojiAnimation (long Duration) {
EmojiAnimation = Duration;
}

public void setCircleColor (int Background,int Color) {
CircleColor = Background;
Circle2Color = Color;

}

public void setDotColor (int Background,int Color) {
DotColor = Color;
DotBackgroundColor = Background;
}

public void setDotBackground (Drawable Background) {
DotBackground = Background;
}

public void setDotSpace (int SpaceSize) {
DotSpace = SpaceSize;
}


public void setButtonBottomMargin (int Margin) {
		ButtonBottomMargin = Margin;
	}

public void setNumbers (int Count,int Selected,boolean UseDefaultText) {
Numbers = Count;
DefaultSelected = Selected;
if (UseDefaultText) {
	List<String> l = new ArrayList<String>();
    for (int i = 1; i <= Count; i++) {
       l.add(String.valueOf(i));
    }
	this.setNumbersText(l);
}
}

public void setShape (Bitmap Shape) {
ShapeImage = Shape;
}

public void setEmojisList (List<Bitmap> EmojisList) {
EmojiList=EmojisList;
}

public void setHideWithSubmit (boolean value) {
HideOnSubmit=value;
}

public void setHideWithCancel (boolean value) {
HideOnCancel=value;
}

public void setRTLMode (boolean value) {
Rtl=value;
}

public void setNumbersText (List<String> Texts) {
NumbersText = Texts;
}

public void setTypeface (int ViewType,Typeface ViewTypeface) {
	switch (ViewType) {
	case TYPE_TITLE:
		TitleTypeface = ViewTypeface;
	case TYPE_SUBMIT_BTN:
		ButtonTypeface = ViewTypeface;
	case TYPE_CANCEL_BTN:
		Button2Typeface = ViewTypeface;
	case TYPE_NUMBERS:
		NumbersTypeface = ViewTypeface;
	}
}

public void setTextColor (int ViewType,int Color) {
switch (ViewType) {
	case TYPE_TITLE:
		TitleColor = Color;
	case TYPE_SUBMIT_BTN:
		ButtonTextColor = Color;
	case TYPE_CANCEL_BTN:
		Button2TextColor = Color;
	case TYPE_NUMBERS:
		NumbersColor = Color;
}
}

public void setTextSize (int ViewType,int Size) {
switch(ViewType) {
	case TYPE_TITLE :
		TitleSize = Size;
	case TYPE_SUBMIT_BTN :
		ButtonSize = Size;
	case TYPE_CANCEL_BTN :
		Button2Size = Size;
	case TYPE_NUMBERS :
		NumbersSize = Size;
}
}

public void setText (int ViewType,CharSequence Text) {
if(ViewType==TYPE_TITLE) {
		TitleText = Text;
}else if (ViewType==TYPE_SUBMIT_BTN) {
		ButtonText = Text;
}else if (ViewType==TYPE_CANCEL_BTN) {
		Button2Text = Text;
}
}

public void setButtonBackground (int ViewType,Drawable Background) {
switch(ViewType) {
	case TYPE_SUBMIT_BTN:
		ButtonBackground = Background;
	case TYPE_CANCEL_BTN:
		Button2Background = Background;
}
}

public void setDialogTheme (int Color,int Radius) {
DialogColor = Color;
DialogRadius = Radius;
}

public void setButtonsEnabled (boolean value) {
ButtonsEnabled = value;
}

public void setButtonsRipple (boolean value) {
Ripple = value;
}

public void setButtonsSpace (int value) {
ButtonSpace = value;
}

public void setButtonsShadow (float value) {
ButtonShadow = value;
}


class DialogLayout extends ViewGroup.MarginLayoutParams{

	public DialogLayout(int left,int top,int width, int height) {
		super(width, height);
		this.leftMargin = left;
		this.topMargin=top;
	}
	
}

public void Show() {
	if (Showing || RunningAnim) return;
	Showing = true;
	RunningAnim = true;
	SelectedIndex = DefaultSelected;
	mposition = SelectedIndex-1;
	
	RateDialogLayout Dialog = new RateDialogLayout(context);
	Dialog.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View arg0) {
			// DoNothing!
		}
	});
	if (lp!=null) {
	Panel.addView(Dialog,lp);
	}else {
		if (Panel instanceof  RateDialogLayout){
			Panel.addView(Dialog, new RateDialogLayout.LayoutParams
					((getScreenWidth() / 2) - (Width / 2), (TargetTop) - PaddingBottom, Width, Height));
		}else {
			Panel.addView(Dialog, new DialogLayout
					((getScreenWidth() / 2) - (Width / 2), (TargetTop) - PaddingBottom, Width, Height));
		}
	}

	
	if (Listener!=null) this.Listener.onShow();
	
	GradientDrawable  DCD = new GradientDrawable ();
	DCD.setColor(DialogColor);
	DCD.setShape(GradientDrawable.RECTANGLE);
	DCD.setCornerRadii(new float[] {DialogRadius,DialogRadius,DialogRadius,DialogRadius,0,0,0,0});
	Dialog.setBackground(DCD);
	
	TextView Title = new TextView(context);
	int TitleHeight =dp(46);
	if (TitleText.length()==0) TitleHeight=0;
	Dialog.addView(Title,new RateDialogLayout.LayoutParams(0,dp(16),ViewUtils.getWidth(Dialog),TitleHeight));
	Title.setText(TitleText);
	Title.setTextColor(TitleColor);
	Title.setTypeface(TitleTypeface);
	Title.setTextSize((float) TitleSize);
	Title.setGravity(Gravity.CENTER);
	
	RateDialogLayout MainParent = new RateDialogLayout(context);
	Dialog.addView(MainParent,new RateDialogLayout.LayoutParams
			(0,ViewUtils.getTop(Title)+ViewUtils.getHeight(Title),ViewUtils.getWidth(Dialog),dp(106)));
	
	RateDialogLayout EmojisParent = new RateDialogLayout(context);
	EmojisParent.setVisibility(RateDialogLayout.GONE);
	MainParent.addView(EmojisParent,new RateDialogLayout.LayoutParams(0,dp(2),dp(42),dp(48)));
	ImageView Shape = new ImageView(context);
	EmojisParent.addView(Shape,new RateDialogLayout.LayoutParams(0,0,ViewUtils.getWidth(EmojisParent),
			ViewUtils.getHeight(EmojisParent)));
	BitmapDrawable ShapeBD = new BitmapDrawable(context.getApplicationContext().getResources(),this.ShapeImage);
	DrawableCompat.setTint(DrawableCompat.wrap(ShapeBD), this.ShapeColor);
	Shape.setBackground(ShapeBD);
	
	ImageView EmojiImage = new ImageView(context);
	EmojiImage.setBackground(GetEmoji(mposition));
	EmojisParent.addView(EmojiImage,new RateDialogLayout.LayoutParams
			(dp(10),dp(8),ViewUtils.getWidth(EmojisParent)-dp(20),ViewUtils.getWidth(EmojisParent)-dp(20)));
	EmojiParent = EmojisParent;
	
	Points.clear();
	RateDialogLayout mSeekbarParent = new RateDialogLayout(context);
	MainParent.addView(mSeekbarParent,new RateDialogLayout.LayoutParams
			(0,ViewUtils.getTop(EmojisParent)+ViewUtils.getHeight(EmojisParent)+dp(2),ViewUtils.getWidth(MainParent),dp(30)));
	setSeekbar(mSeekbarParent);
	
	
	int DotSize = dp(4);
	int DotSize2 = DotSize*2;
	int Left = dp(2);
	ImageView BarImage = new ImageView(context);
	mSeekbarParent.addView(BarImage,new RateDialogLayout.LayoutParams
			(dp(36),(ViewUtils.getHeight(mSeekbarParent)/2)-dp(9),ViewUtils.getWidth(mSeekbarParent)-dp(72),dp(18)));
	
	if (DotBackground==null) {
		GradientDrawable  CD = new GradientDrawable ();
		CD.setColor(this.DotBackgroundColor);
		CD.setShape(GradientDrawable.RECTANGLE);
		CD.setCornerRadius(dp(16));
		BarImage.setBackground(CD);
	}else {
		BarImage.setBackground(DotBackground);
	}
	
	
	int Space;
	if (DotSpace>0) {
		Space = DotSpace;
	}else {
		if (Numbers >5) {
			Space=(ViewUtils.getWidth(BarImage)-((Numbers*(DotSize))-(Left*2)))/Numbers;
	}else {
			Space=(ViewUtils.getWidth(BarImage)-(DotSize2*(Numbers-1))-(DotSize2))/(Numbers-1);
	}
}
	
	RateDialogLayout mSelector = new RateDialogLayout(context);
	CircleCanvas c = new CircleCanvas(BarImage,ViewUtils.getWidth(BarImage),ViewUtils.getHeight(BarImage));
	for (int i=0;i<Numbers;i++) {
		c.drawCircle((Left+(DotSize*2)+dp(1)),(ViewUtils.getHeight(BarImage)/2)-(DotSize/4)+dp(1),DotSize,DotColor,true,dp(2));
		Points.add(Left-DotSize);

		TextView Number = new TextView(context);
		MainParent.addView(Number,new RateDialogLayout.LayoutParams
				(ViewUtils.getLeft(BarImage)+Left-dp(1),ViewUtils.getTop(mSeekbarParent)+ViewUtils.getHeight(mSeekbarParent)+dp(6),dp(18),dp(16)));
		Number.setText(GetNumber(i));
		Number.setTextColor(NumbersColor);
		Number.setGravity(Gravity.CENTER);
		Number.setTypeface(NumbersTypeface);
		Number.setTextSize(NumbersSize);
	
		if ((i+1) == DefaultSelected) {
			SelectedIndex = Left-(DotSize/2);
			mSeekbarParent.addView(mSelector,new RateDialogLayout.LayoutParams
					(dp(36)+Left-DotSize,dp(3),dp(24),dp(24)));
	}
	
		Left=Left+Space+DotSize;
	}
	
	GradientDrawable  SelectorBG = new GradientDrawable ();
	SelectorBG.setColor(this.CircleColor);
	SelectorBG.setShape(GradientDrawable.RECTANGLE);
	SelectorBG.setCornerRadius(ViewUtils.getWidth(mSelector));
	ViewUtils.setElevation(mSelector,dp(2));
	mSelector.bringToFront();
	mSelector.setBackground(SelectorBG);

	View Circle2View = new View(context);
	mSelector.addView(Circle2View,new RateDialogLayout.LayoutParams
			(dp(4),dp(4),ViewUtils.getWidth(mSelector)-dp(8),ViewUtils.getHeight(mSelector)-dp(8)));
	GradientDrawable  SelectorBG2 = new GradientDrawable ();
	SelectorBG2.setColor(this.Circle2Color);
	SelectorBG2.setShape(GradientDrawable.RECTANGLE);
	SelectorBG2.setCornerRadius(ViewUtils.getWidth(Circle2View));
	Circle2View.setBackground(SelectorBG2);
	ViewUtils.setLeft(EmojiParent,ViewUtils.getLeft(mSelector)+ViewUtils.getWidth(mSelector));
	
	Selector = mSelector;
	SeekBarParent = mSeekbarParent;
	
	if (ButtonsEnabled) {
	RateDialogLayout ButtonPanel = new RateDialogLayout(context);
	Dialog.addView(ButtonPanel,new RateDialogLayout.LayoutParams
			(ViewUtils.getWidth(Dialog)-dp(20)-dp(50),ViewUtils.getHeight(Dialog)-dp(18)-dp(32)-ButtonBottomMargin,dp(50),dp(32)));
	ButtonPanel.setBackground(ButtonBackground);
	
	TextView ButtonTextLbl = new TextView(context);
	ButtonPanel.addView(ButtonTextLbl,new RateDialogLayout.LayoutParams
			(0,0,ViewUtils.getWidth(ButtonPanel),ViewUtils.getHeight(ButtonPanel)));
	ButtonTextLbl.setText(ButtonText);
	ButtonTextLbl.setTextSize(ButtonSize);
	ButtonTextLbl.setTextColor(ButtonTextColor);
	ButtonTextLbl.setTypeface(ButtonTypeface);
	ButtonTextLbl.setGravity(Gravity.CENTER);
	ViewUtils.setWidth(ButtonTextLbl, ViewUtils.getTextWidth(ButtonTextLbl)+TEXT_WIDTH_SCALE);
	ViewUtils.setWidth(ButtonPanel,ViewUtils.getWidth(ButtonTextLbl));
	if (this.Ripple) {
		this.SubmitClick(ButtonTextLbl);
	}else {
		this.SubmitClick(ButtonPanel);
	}
	
	RateDialogLayout ButtonPanel2 = new RateDialogLayout(context);
	if (Button2Text.length()>0) {
		Dialog.addView(ButtonPanel2,new RateDialogLayout.LayoutParams
				(ViewUtils.getWidth(Dialog)-dp(20)-dp(50)-this.ButtonSpace-ViewUtils.getWidth(ButtonPanel)
						,ViewUtils.getHeight(Dialog)-dp(18)-dp(32)-ButtonBottomMargin,dp(50),dp(32)));
		ButtonPanel2.setBackground(Button2Background);
		
		TextView ButtonTextLbl2 = new TextView(context);
		ButtonPanel2.addView(ButtonTextLbl2,new RateDialogLayout.LayoutParams
				(0,0,ViewUtils.getWidth(ButtonPanel2),ViewUtils.getHeight(ButtonPanel2)));
		ButtonTextLbl2.setText(Button2Text);
		ButtonTextLbl2.setTextSize(Button2Size);
		ButtonTextLbl2.setTextColor(Button2TextColor);
		ButtonTextLbl2.setTypeface(Button2Typeface);
		ButtonTextLbl2.setGravity(Gravity.CENTER);
		ViewUtils.setWidth(ButtonTextLbl2, ViewUtils.getTextWidth(ButtonTextLbl2)+TEXT_WIDTH_SCALE);
		ViewUtils.setWidth(ButtonPanel2,ViewUtils.getWidth(ButtonTextLbl2));
		if (this.Ripple) {
			this.CancelClick(ButtonTextLbl2);
		}else {
			this.CancelClick(ButtonPanel2);
		}
		
		ViewUtils.setClipToOutline(ButtonPanel2);
		if (Ripple) {
			ViewUtils.setClickEffect(ButtonTextLbl2,false);
		}
		if (ButtonShadow>0) {
			ViewUtils.setElevation(ButtonPanel2,ButtonShadow);
		}
	}

	if (Rtl==false) {
		ViewUtils.setLeft(ButtonPanel,ViewUtils.getWidth(Dialog)-dp(20)-ViewUtils.getWidth(ButtonPanel));
		if (Button2Text.length()>0)
		ViewUtils.setLeft(ButtonPanel2,ViewUtils.getLeft(ButtonPanel)-ViewUtils.getWidth(ButtonPanel2)-ButtonSpace);
	}else {
		ViewUtils.setLeft(ButtonPanel,dp(20));
		if (Button2Text.length()>0)
		ViewUtils.setLeft(ButtonPanel2,ViewUtils.getLeft(ButtonPanel)+ViewUtils.getWidth(ButtonPanel)+ButtonSpace);
	}
	

	ViewUtils.setClipToOutline(ButtonPanel);
	if (Ripple) {
		ViewUtils.setClickEffect(ButtonTextLbl,false);
	}
	
	if (ButtonShadow>0) {
		ViewUtils.setElevation(ButtonPanel,ButtonShadow);
	}
	}
	
	if (Listener!=null) this.Listener.onBind(Dialog);
	RateDialog = Dialog;

	StartAnimation(true);
}

private void SubmitClick (View v) {
	v.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View arg0) {
			if (Listener!=null) Listener.onSubmit(mposition);
			if (HideOnSubmit) {Hide();}
		}
	});
}
private void CancelClick (View v) {
	v.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View arg0) {
			if (Listener!=null) Listener.onCancel(mposition);
			if (HideOnCancel) {Hide();}
		}
	});
}

private Drawable GetEmoji (int index) {
if (index<0) return null;
if (EmojiList.size()>(index)){
Bitmap Emoji = EmojiList.get(index);
BitmapDrawable bd = new BitmapDrawable(context.getApplicationContext().getResources(),Emoji);
	DrawableCompat.setTint(DrawableCompat.wrap(bd), this.EmojisColor);
	return bd;
}else {
	return null;
}
}

private String GetNumber (int index) {
if (NumbersText==null || NumbersText.isEmpty()) return "";
if (index<0) return "";
if (NumbersText.size()>(index)){
	return NumbersText.get(index);
}else {
	return "";
}
}

private void StartAnimation (final boolean Shows) {
RunningAnim = true;
ObjectAnimator anim = ObjectAnimator.ofFloat(RateDialog, "translationY", 0,-ViewUtils.getHeight(RateDialog));
anim.setDuration(AnimDuration);
if (this.animInterpolator!=null) {
anim.setInterpolator(this.animInterpolator);
}
anim.addListener(new AnimatorListener() {
	@Override
	public void onAnimationCancel(Animator arg0) {
	}
	@Override
	public void onAnimationEnd(Animator arg0) {
		RunningAnim = false;
				if(Shows==false) {
					RateDialog.removeAllViews();
					ViewUtils.RemoveView(RateDialog);
					RateDialog=null;
				}
	}
	@Override
	public void onAnimationRepeat(Animator arg0) {	
	}
	@Override
	public void onAnimationStart(Animator arg0) {	
	}
});
if(Shows) {
	anim.start();
}else {
	anim.reverse();
}
}

private void StartTouchingAnimation (boolean t) {
noaSelectorView = Selector.getChildAt(0);
int Alpha = Color.alpha(Circle2Color);
ValueAnimator anim = ValueAnimator.ofInt(Alpha,Alpha-100);
anim.addUpdateListener( new AnimatorUpdateListener() {

	@Override
	public void onAnimationUpdate(ValueAnimator arg0) {
		int newAlpha = (int) arg0.getAnimatedValue();
		GradientDrawable  SelectorBG = new GradientDrawable ();
		SelectorBG.setColor(Color.argb(newAlpha, Color.red(Circle2Color), Color.green(Circle2Color), Color.blue(Circle2Color)));
		SelectorBG.setShape(GradientDrawable.RECTANGLE);
		SelectorBG.setCornerRadius(ViewUtils.getWidth(noaSelectorView));
		noaSelectorView.setBackground(SelectorBG);
	}

});
anim.setDuration(100);
if (t) {
	anim.start();
	SetNewSize(noaSelectorView,dp(8),dp(8),ViewUtils.getWidth(Selector)-dp(16),ViewUtils.getHeight(Selector)-dp(16));
}else {
	anim.reverse();
	SetNewSize(noaSelectorView,dp(4),dp(4),ViewUtils.getWidth(Selector)-dp(8),ViewUtils.getHeight(Selector)-dp(8));
}
}


private void SetNewSize (final View View,int Left,int Top,int mWidth,int mHeight) {
	ValueAnimator anim_Left = ValueAnimator.ofInt(ViewUtils.getLeft(View),Left);
	anim_Left.addUpdateListener( new AnimatorUpdateListener() {
		@Override
		public void onAnimationUpdate(ValueAnimator arg0) {
			ViewUtils.setLeft(View, (int) arg0.getAnimatedValue());
		}
	});
	anim_Left.setDuration(100);
	anim_Left.start();
	
	ValueAnimator anim_Width = ValueAnimator.ofInt(ViewUtils.getWidth(View),mWidth);
	anim_Width.addUpdateListener( new AnimatorUpdateListener() {
		@Override
		public void onAnimationUpdate(ValueAnimator arg0) {
			ViewUtils.setWidth(View, (int) arg0.getAnimatedValue());
		}
	});
	anim_Width.setDuration(100);
	anim_Width.start();
	
	ValueAnimator anim_Top = ValueAnimator.ofInt(ViewUtils.getTop(View),Top);
	anim_Top.addUpdateListener( new AnimatorUpdateListener() {
		@Override
		public void onAnimationUpdate(ValueAnimator arg0) {
			ViewUtils.setTop(View, (int) arg0.getAnimatedValue());
		}
	});
	anim_Top.setDuration(100);
	anim_Top.start();
	
	ValueAnimator anim_Height = ValueAnimator.ofInt(ViewUtils.getHeight(View),mHeight);
	anim_Height.addUpdateListener( new AnimatorUpdateListener() {
		@Override
		public void onAnimationUpdate(ValueAnimator arg0) {
			ViewUtils.setHeight(View, (int) arg0.getAnimatedValue());
		}
	});
	anim_Height.setDuration(100);
	anim_Height.start();
}


private void StartEmojiAnimation (boolean Shows) {
EmojiParent.setVisibility(RateDialogLayout.VISIBLE);

ObjectAnimator anim_ALPHA;
ObjectAnimator anim_SCALE_X;
ObjectAnimator anim_SCALE_Y;
anim_ALPHA = ObjectAnimator.ofFloat(EmojiParent, "alpha", 0,1);
anim_ALPHA.setDuration(150);
anim_SCALE_X = ObjectAnimator.ofFloat(EmojiParent, "scaleX", 0.6f,1f);
anim_SCALE_X.setDuration(150);
anim_SCALE_Y = ObjectAnimator.ofFloat(EmojiParent, "scaleY", 0.6f,1f);
anim_SCALE_Y.setDuration(150);
anim_SCALE_X.setInterpolator(new android.view.animation.OvershootInterpolator());
anim_SCALE_Y.setInterpolator(new android.view.animation.OvershootInterpolator());

if (Shows) {
	anim_ALPHA.start();
	anim_SCALE_X.start();
	anim_SCALE_Y.start();
}else {
	anim_ALPHA.reverse();
	anim_SCALE_X.reverse();
	anim_SCALE_Y.reverse();
}
}

private void ChangeEmojiAimation (final Drawable toEmoji) {
emojiAnimNext=true;
emojiAnimRunning=true;
final View View = EmojiParent.getChildAt(1);
ObjectAnimator anim_Y = ObjectAnimator.ofFloat
(View, "translationY", 0,-(ViewUtils.getTop(View)+ViewUtils.getHeight(View)));
anim_Y.setDuration(EmojiAnimation);

anim_Y.addListener(new AnimatorListener() {
	@Override
	public void onAnimationCancel(Animator arg0) {
	}
	@Override
	public void onAnimationEnd(Animator arg0) {
		
	if (emojiAnimNext) {
		View.setBackground(toEmoji);
	}
	ObjectAnimator anim_Y2 = ObjectAnimator.ofFloat
			(View, "translationY", 0,-(ViewUtils.getTop(View)+ViewUtils.getHeight(View)));
		anim_Y2.setDuration(EmojiAnimation);
		anim_Y2.addListener(new AnimatorListener() {
			@Override
			public void onAnimationCancel(Animator arg0) {	
			}
			@Override
			public void onAnimationEnd(Animator arg0) {
				emojiAnimRunning=false;
			}
			@Override
			public void onAnimationRepeat(Animator arg0) {	
			}
			@Override
			public void onAnimationStart(Animator arg0) {	
			}
		});
		anim_Y2.reverse();
	}
	@Override
	public void onAnimationRepeat(Animator arg0) {	
	}
	@Override
	public void onAnimationStart(Animator arg0) {
	}
	
});
anim_Y.start();

}


private void setSeekbar (ViewGroup seekbar) {
seekbar.setOnTouchListener(new View.OnTouchListener() {
	
	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		int Action = arg1.getAction();
		float X = arg1.getX();
		float Y = arg1.getY();

		if (Action==MotionEvent.ACTION_DOWN) {
			if (X>=ViewUtils.getLeft(Selector) && X<=ViewUtils.getLeft(Selector)+ViewUtils.getWidth(Selector)) {
				Pressing=true;
				StartTouchingAnimation(true);
			    TouchTimer.setInterval(100);
				TouchTimer.setEnabled(true);
			}else if (Clickable){
				lastClickX = X - CLICK_SENSITIVITY;
				if (X - dp(36) >= 0 && X - dp(72) <= ViewUtils.getWidth(SeekBarParent.getChildAt(0))) {
					if (lastClickX < 0) lastClickX = 0;
					if (lastClickX >= ViewUtils.getWidth(SeekBarParent) - ViewUtils.getWidth(Selector))
						lastClickX = ViewUtils.getWidth(SeekBarParent) - ViewUtils.getWidth(Selector);
				}
			}
		}else if (Action==MotionEvent.ACTION_UP) {
			if (Pressing) {
				if (EmojiShowing) {
				EmojiShowing=false;
				StartEmojiAnimation(false);
				}
			StartTouchingAnimation(false);
			}else if (Clickable){
				if (Y>0 && Y<ViewUtils.getHeight(SeekBarParent)) {
					if (X - dp(36) >= 0 && X - dp(72) <= ViewUtils.getWidth(SeekBarParent.getChildAt(0))) {
					if ((X-CLICK_SENSITIVITY)<0) {X = 0;}
					else if ((X-CLICK_SENSITIVITY)>= ViewUtils.getWidth(SeekBarParent) - ViewUtils.getWidth(Selector)){
						X=ViewUtils.getWidth(SeekBarParent) - ViewUtils.getWidth(Selector);}
						else {X=X-CLICK_SENSITIVITY;}

					int NX = GetNearPoint(X - dp(36));
					if (NX == GetNearPoint(lastClickX - dp(36))) {
							if (SelectedIndex != NX) {
								SelectedIndex = NX;
								setSelection2(GetNearIndex(NX), NX);
							}
						}
					}
				}
			}
			lastClickX=-1;
			Pressing=false;
		}else if (Action==MotionEvent.ACTION_MOVE) {
			if (Pressing && TouchTimer.getEnabled() == false) {
					if (EmojiShowing==false) {
						EmojiShowing=true;
						StartEmojiAnimation(true);
					}
					if (X-dp(36)>=0 && X-dp(36)<=ViewUtils.getWidth(SeekBarParent)-ViewUtils.getWidth(Selector)) {
						int NX = GetNearPoint(X-dp(36));
						if (SelectedIndex!=NX) {
							SelectedIndex = NX;
							setSelection(GetNearIndex(NX),NX);
						}
					}else {
						if (X-dp(36)<=0 && mposition!=0) {
							setSelection(0,Points.get(0));
						} else if ((X - dp(36))>=ViewUtils.getWidth(SeekBarParent)-ViewUtils.getWidth(Selector) &&
						mposition!=Points.size()-1) {
							setSelection(Points.size()-1,Points.get(Points.size()-1));
						}
					}
			}
		}
		return true;
	}
});	
}

private void setSelection (int Position,int NX) {
	setSelection3(Position,NX,50);
}


private void setSelection2 (int Position,int NX) {
	int delta = mposition - Position;
	delta = Math.abs(delta);
	long Duration = delta * CLICK_ANIMATION_SENSITIVITY;
			if (Duration<50) Duration = 50;
	setSelection3(Position,NX,Duration);
}

private void setSelection3 (int Position,int NX,long Duration) {
mposition=Position;
if (emojiAnimRunning) {
	emojiAnimNext=false;
	EmojiParent.getChildAt(1).setBackground(GetEmoji(Position));
}else {
	ChangeEmojiAimation(GetEmoji(Position));
}

if  (Listener!=null) Listener.onChange(Position);

ValueAnimator anim_Left = ValueAnimator.ofInt(ViewUtils.getLeft(Selector),ViewUtils.getLeft(SeekBarParent.getChildAt(0))+NX);
anim_Left.addUpdateListener( new AnimatorUpdateListener() {
	@Override
	public void onAnimationUpdate(ValueAnimator arg01) {
		ViewUtils.setLeft(Selector, (int) arg01.getAnimatedValue());
	}
});
anim_Left.setDuration(Duration);
anim_Left.start();

ValueAnimator anim_Left2 = ValueAnimator.ofInt(ViewUtils.getLeft(EmojiParent),NX+ViewUtils.getWidth(Selector)+dp(3));
anim_Left2.addUpdateListener( new AnimatorUpdateListener() {
	@Override
	public void onAnimationUpdate(ValueAnimator arg02) {
		ViewUtils.setLeft(EmojiParent, (int) arg02.getAnimatedValue());
	}
});
anim_Left2.setDuration(Duration);
anim_Left2.start();

TouchTimer.setInterval(Duration);
TouchTimer.setEnabled(true);
}

private int GetNearPoint (float x) {
return nearestValue(Points,(int)x,false);
}

private int GetNearIndex (float x) {
return nearestValue(Points,(int)x,true);
}

static int nearestValue(List<Integer> vals,int target,boolean indexReturn){
	  int min = vals.get(0) - target;
	  min = min < 0 ? -min:min;
	  int min_idx = 0;
	  for(int i=1;i<vals.size();i++){
	   int v = vals.get(i);
	   int delta = v - target;
	   delta = delta < 0 ? -delta : delta;
	   if(delta < min){
	    min = delta;
	    min_idx = i;
	   }
	  }
	  if (indexReturn) {
		  return min_idx;
	  }else {
		  return vals.get(min_idx);
	  }
}


public void Hide() {
if (Showing==false || RunningAnim) return;
Showing = false ; RunningAnim = true;
if  (Listener!=null) Listener.onHide();
StartAnimation(false);
}

public boolean isShowing() {
return Showing;
}
public boolean isRunning() {
return RunningAnim;
}
public int getPosition(){
	return  mposition;
}
public void setPosition(int position){
	setSelection(position,Points.get(position));
}
public void setPosition(int position,long Duration){
	setSelection3(position,Points.get(position),Duration);
}
public boolean onBackPressed() {
if (RunningAnim) return true;
if (Showing) {
	Hide();
	return true;
}
return false;
}

}
