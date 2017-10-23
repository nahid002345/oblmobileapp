package com.app.obl.oblmobileapp.thread;

import android.util.Log;

import com.app.obl.oblmobileapp.listener.AppInActivityTimeOutListener;

/**
 * Created by ONE BANK 1 on 1/10/2016.
 */
public class AppInActivityTimer extends Thread {
    private static final String TAG=AppInActivityTimer.class.getName();
    AppInActivityTimeOutListener timeoutListener;
    private long lastUsed;
    private long period;
    private boolean stop;

    public AppInActivityTimer(long period)
    {
        this.period=period;
        stop=false;
    }
    public AppInActivityTimer(long period,AppInActivityTimeOutListener listener)
    {
        this.period=period;
        this.timeoutListener = listener;
        stop=false;
    }

    public void run()
    {
        long idle=0;
        this.touch();
        do
        {
            idle=System.currentTimeMillis()-lastUsed;
            Log.d(TAG, "Application is idle for " + idle + " ms");
            try
            {
                Thread.sleep(5000); //check every 5 seconds
            }
            catch (InterruptedException e)
            {
                Log.d(TAG, "AppInActivityTimer interrupted!");
            }
            if(idle > period)
            {

                idle=0;
                timeoutListener.onTaskComplete(true);
            }
        }
        while(!stop);
        Log.d(TAG, "Finishing AppInActivityTimer thread");
    }

    public synchronized void touch()
    {
        lastUsed=System.currentTimeMillis();
    }

    public synchronized void forceInterrupt()
    {
        this.interrupt();
    }

    public synchronized void stopThread()
    {
        stop=true;
    }
    public synchronized void setPeriod(long period)
    {
        this.period=period;
    }
}
