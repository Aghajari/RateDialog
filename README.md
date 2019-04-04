# Amir_RateDialog (Android-JAVA)
<img src="https://github.com/Aghajari/AmirRateDialog-Android-Java/blob/master/animation.gif" width=300 title="Screen">

Advanced Rate Dialog Library

- Customize Theme,Colors,Texts,Sizes,...
- Support Rtl
- Customize Shape,Emojies,...
- Customize Number Texts
- Customize Submit Button

...

**Version 1.04**

# Whats New ?
**Version 1.04**
- Clickable Seekbar
- Click Sensitivity
- get/set position runtime
- Animation Interpolator
- ver1.02 Bugs Fixed

**Version 1.02**
- Set Buttons Gravity
- Set Dot Background
- Set Dot Items Space
- Support 10 or 5 Stars
- Cancel Button Added
- You can hide title (don't set title)
- onBind event (create your custom view on dialog)
- Ripple Effect into Buttons
- Buttons Enabled
- ver1.00 Bugs Fixed

## Listener
```
        void onShow()
        void onHide()
        void onChange(int NewPosition)
        void onSubmit(int Position)
        void onCancel(int Position)
        void onBind(ViewGroup Dialog)
```

## Example
```
        AmirRateDialog rateDialog = new AmirRateDialog(this, top, layout, new RateListener(){...});
        rateDialog.setBottomPadding(dp(1));
        rateDialog.setDialogTheme(Color.rgb(240,248,250),dp(6));
        rateDialog.setNumbers(AmirRateDialog.NUMBER_10,1,true); //NUMBER_10 , NUMBER_5
        rateDialog.setDotColor(Color.rgb(233,233,231),Color.rgb(193,195,192));
        rateDialog.setCircleColor(Color.rgb(11,156,161),Color.WHITE);
        rateDialog.setSize(dp(320),dp(270));
        rateDialog.setButtonBackground(AmirRateDialog.TYPE_SUBMIT_BTN,CD);
        rateDialog.setButtonBackground(AmirRateDialog.TYPE_CANCEL_BTN,null);
        rateDialog.setText(AmirRateDialog.TYPE_SUBMIT_BTN,"Submit");
        rateDialog.setText(AmirRateDialog.TYPE_CANCEL_BTN,"Cancel"); // comment this line will disable cancel button
        rateDialog.setText(AmirRateDialog.TYPE_TITLE,"How would you rate our service?"); // comment this line will disable Title
        rateDialog.setTextColor(AmirRateDialog.TYPE_TITLE,Color.BLACK);
        rateDialog.setTextColor(AmirRateDialog.TYPE_SUBMIT_BTN,Color.WHITE);
        rateDialog.setTextColor(AmirRateDialog.TYPE_CANCEL_BTN,Color.GRAY);
        rateDialog.setTextColor(AmirRateDialog.TYPE_NUMBERS,Color.BLACK);
        rateDialog.setShape(BitmapFactory.decodeResource(this.getResources(),R.drawable.rectangle));
        rateDialog.setShapeColor(Color.rgb(11,156,161));
        rateDialog.setEmojisColor(Color.WHITE);
        rateDialog.setEmojisList(Emojis);
        rateDialog.setButtonsRipple(true);
        rateDialog.setButtonBottomMargin(dp(30));
        rateDialog.setClickable(true);

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rateDialog.isShowing()) { rateDialog.Hide(); } else { rateDialog.Show();}
            }
        });
```

## Author
Amir Hossein Aghajari

Telegram @KingAmir272
