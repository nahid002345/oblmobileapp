package com.app.obl.oblmobileapp.activity;


import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.app.obl.oblmobileapp.R;
import com.app.obl.oblmobileapp.helper.ExchangeRate;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class SplashScreenActivity extends Activity {

    @Override
    public void onBackPressed() {


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //test();

        StartAnimations();

        long splashDelay = 5000;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                        startActivity(new Intent(SplashScreenActivity.this,
                                MainDrawerTestActivity.class));
                SplashScreenActivity.this.finish();
                //android.os.Process.killProcess(android.os.Process.myPid());

            }
        }, splashDelay);
    }

    private void RotateLoader(){
        Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.clockwise_rotate);
        //anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.clockwise_rotate);
        anim.reset();
        ImageView ivLoader = (ImageView) findViewById(R.id.loader);
        ivLoader.setVisibility(View.VISIBLE);
        ivLoader.clearAnimation();
        ivLoader.startAnimation(anim);
    }
    private void StartAnimations() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();
        RelativeLayout rela_layout=(RelativeLayout) findViewById(R.id.rel_lay);
        rela_layout.clearAnimation();
        rela_layout.startAnimation(anim);

        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();
        ImageView iv = (ImageView) findViewById(R.id.logo);
        iv.clearAnimation();
        iv.startAnimation(anim);

        if(anim.hasEnded())
        {
            RotateLoader();
        }
    }

    public void test(){
        ArrayList<ExchangeRate> mERList= new ArrayList<>();
        ExchangeRate mer= new ExchangeRate();
        mer.er_currency="USD";
        mer.er_date="01/01/2015";
        mer.er_sell_rate="60.99";
        mer.er_buy_rate="60.99";
        mer.er_type="CASH";
        mERList.add(mer);
        InsertData(mERList,ExchangeRate.class);

    }


    public <T> void InsertData ( ArrayList<T> para_data_list,Class<T> type) {

        ContentValues values = new ContentValues();

        for(Object data : para_data_list ){
            if(type.isAssignableFrom(data.getClass())){
                Field[] field_list=data.getClass().getFields();
                for(Field f : field_list){

                }
            }
        }
    }


}