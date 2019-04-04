package com.aghajari.amirratedialog;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.aghajari.ratedialog.AmirRateDialog;
import com.aghajari.ratedialog.RateDialogLayout;
import com.aghajari.ratedialog.RateListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Amir Hossein Aghajari
 */
public class MainActivity extends AppCompatActivity {
    AmirRateDialog rateDialog;
    float density =  1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView rate = findViewById(R.id.btn);

        // RateDialogLayout is the best Parent for showing Dialog
        RateDialogLayout layout = new RateDialogLayout(this);
        rate.getRootView().setBackgroundColor(Color.rgb(11,156,161));
        ((ViewGroup)rate.getRootView()).addView(layout,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));



        density = this.getResources().getDisplayMetrics().density;
        final int top = Resources.getSystem().getDisplayMetrics().heightPixels + dp(34);
        rateDialog = new AmirRateDialog(this, top, layout, new RateListener() {
            @Override
            public void onShow() {
                Log.d("rate","Show");
            }

            @Override
            public void onHide() {
                Log.d("rate","Hide");
            }

            @Override
            public void onChange(int NewPosition) {
                Log.d("rate","New Position : "+(NewPosition+1));
            }

            @Override
            public void onSubmit(int Position) {
                Log.d("rate",(Position+1)+" Submited!");
                Toast.makeText(rate.getRootView().getContext(),(Position+1)+" Submited!",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel(int Position) {
                Log.d("rate","Canceled");
            }

            @Override
            public void onBind(ViewGroup Dialog) {

            }
        });

        List<Bitmap> Emojis = new ArrayList<Bitmap>();
        Emojis.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.image_1));
        Emojis.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.image_2));
        Emojis.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.image_3));
        Emojis.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.image_4));
        Emojis.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.image_5));
        Emojis.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.image_6));
        Emojis.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.image_7));
        Emojis.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.image_8));
        Emojis.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.image_9));
        Emojis.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.image_10));

        GradientDrawable CD = new GradientDrawable();
        CD.setColor(Color.rgb(68,86,168));
        CD.setShape(GradientDrawable.RECTANGLE);
        CD.setCornerRadius(dp(2));

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
        AmirRateDialog.TEXT_WIDTH_SCALE = dp(20);

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("btn" ,"clicked");
                if (rateDialog.isShowing()) { rateDialog.Hide(); } else { rateDialog.Show();}
            }
        });
    }

    private int dp(float value) {
        if (value == 0) {
            return 0;
        }
        return (int) (density*value);
    }

    @Override
    public void onBackPressed(){
        if (rateDialog.onBackPressed()==false){
            this.finish();
        }
    }

    public int getNavigationBarHeight(){
        Resources resources = this.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId);
        }
        return 0;
    }
}
