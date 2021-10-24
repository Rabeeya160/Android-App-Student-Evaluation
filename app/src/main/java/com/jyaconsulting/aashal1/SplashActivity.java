package com.jyaconsulting.aashal1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.jyaconsulting.aashal1.database.AppDataBase;
import com.jyaconsulting.aashal1.model.UserInfoModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SplashActivity extends AppCompatActivity implements Animation.AnimationListener {
    private Context context;
    private Animation animation;
    ImageView iv_logo, iv_trend;
    TextView tv_textCopy;
    String encode;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        context = SplashActivity.this;

        AppDataBase db = new AppDataBase(context, "");
        db.open();
        ArrayList<UserInfoModel> arr=db.getUserInfo();
        UserInfoModel current=arr.get(0);
        encode=current.getAccount();
        db.close();

        SimpleDateFormat sdf=new SimpleDateFormat("EEEE");
        Date day=new Date();
        String TodayIs=sdf.format(day);
        Utils.savePreferences("TodayIs",TodayIs,context);


        linkViews();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                if (encode.isEmpty()) {
                    intent = new Intent(context, LoginActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(context, HomeActivity.class);
                    startActivity(intent);
                }
//                // Apply Condition Local Db Available or not then Build Db from Class DBRecord and SetPreferences null Else Goto Login Screen
//                SharedPreferences preferences = getSharedPreferences("myprefs", MODE_PRIVATE);
//                if (preferences.getBoolean("firstLogin", true)) {
//                    SharedPreferences.Editor editor = preferences.edit();
//                    editor.putBoolean("firstLogin", false);
//                    editor.commit();
//                    Intent intent = new Intent(context, LoginActivity.class);
//                    startActivity(intent);
//                } else {
//                    Intent intent = new Intent(context, LoginActivity.class);
//                    startActivity(intent);
//                }
                finish();
            }
        }, 5000);
    }

    private void linkViews() {
//        tv_textCopy=findViewById(R.id.tv_textCopy);
        iv_logo = findViewById(R.id.iv_logo);
        iv_trend = findViewById(R.id.iv_trend);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                final TypeWrite tw = (TypeWrite) findViewById(R.id.tv_textCopy);
                tw.setText("");
                tw.setCharacterDelay(70);
                tw.animateText("Powered by JYA Consulting");
            }
        }, 1000);
        animation = AnimationUtils.loadAnimation(context, R.anim.animation_up);
        iv_trend.startAnimation(animation);
//        animation = AnimationUtils.loadAnimation(context, R.anim.animation_fade_in);
//        iv_logo.startAnimation(animation);
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
