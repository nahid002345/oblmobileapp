package com.app.obl.oblmobileapp.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;

import com.app.obl.oblmobileapp.app.PrefManager;
import com.app.obl.oblmobileapp.helper.CustomWarningAlertDialog;
import com.app.obl.oblmobileapp.helper.MessageType;
import com.app.obl.oblmobileapp.helper.SessionTimeOutAlertDialog;
import com.app.obl.oblmobileapp.listener.AppInActivityTimeOutListener;
import com.app.obl.oblmobileapp.thread.AppInActivityTimer;

import java.util.Date;

/**
 * Created by ONE BANK 1 on 1/10/2016.
 */
public class BaseActivity extends AppCompatActivity implements AppInActivityTimeOutListener {
    public PrefManager mPrefManager;
    private AppInActivityTimer mInActiveTimerThread;
    public static long lastActivity=new Date().getTime();
    public static long inActiveTime=(5*60*1000);
    public boolean isTimeOutActivity;

    public void setLoginLastActivityTime()
    {
        lastActivity= new Date().getTime();
    }
    public void checkSessionLogin(Context ctx)
    {
        mPrefManager= new PrefManager(ctx,BaseActivity.this);
        mPrefManager.checkLogin();
    }

    public void clearSession(Context ctx)
    {
        mPrefManager= new PrefManager(ctx,BaseActivity.this);
        mPrefManager.clearSession();

    }

    public void logOutSession(Context ctx) {
        mPrefManager = new PrefManager(ctx, BaseActivity.this);
        mPrefManager.clearSession();
        Intent intWelCome = new Intent(BaseActivity.this, MainDrawerTestActivity.class);
        intWelCome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intWelCome);
    }
    public String getTokenId()
    {
        String tokenId="";
        if(mPrefManager!=null)
            tokenId=mPrefManager.getTokenId();
        return tokenId;
    }

    private void checkTimeOut()
    {
        mPrefManager= new PrefManager(getApplicationContext(),BaseActivity.this);
        long now = new Date().getTime();
        if (now - lastActivity > inActiveTime && mPrefManager.isLoggedIn()) {
            SessionTimeOutAlertDialog alert = new SessionTimeOutAlertDialog();
            alert.showDialog(BaseActivity.this, MessageType.MT_SESSION_TIMEOUT);
            lastActivity=now;
        }
        else{
            lastActivity=now;
        }
    }

    private void checkTimeoutFromThread()
    {
        if(isTimeOutActivity)
        {
            CustomWarningAlertDialog alert = new CustomWarningAlertDialog();
            alert.showDialog(BaseActivity.this, "Time Out, Please Log in...");
        }
    }


    @Override
    public void onStart()
    {
        super.onStart();
        mInActiveTimerThread=new AppInActivityTimer(inActiveTime,this);
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        checkTimeOut();
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onUserInteraction()
    {
        super.onUserInteraction();
        checkTimeOut();
        //checkTimeoutFromThread();
    }

    @Override
    public void onResume() {
        super.onResume();
        checkTimeOut();
    }

    @Override
    public void onTaskComplete(boolean isTimeOut) {
        isTimeOutActivity=isTimeOut;
    }
}
