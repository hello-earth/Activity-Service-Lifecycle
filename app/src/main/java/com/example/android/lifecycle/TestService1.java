package com.example.android.lifecycle;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

public class TestService1 extends Service {

    @Override
    public void onCreate() {
        Log.i(ActivityA.mActivityName,"TestService -> onCreate, Thread ID: " + Thread.currentThread().getId());
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(ActivityA.mActivityName, "TestService -> onStartCommand, startId: " + startId + ", Thread ID: " + Thread.currentThread().getId());
        worker();
        return START_STICKY;
    }

    private void worker(){
        new Thread(new Runnable(){
            @Override
            public void run() {
                while(true) {
                    Log.e(ActivityA.mActivityName, "time:" + SystemClock.elapsedRealtime());
                    SystemClock.sleep(1000);
                }
            }
        }).start();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(ActivityA.mActivityName, "TestService -> onBind, Thread ID: " + Thread.currentThread().getId());
        return null;
    }

    @Override
    public void onDestroy() {
        Log.i(ActivityA.mActivityName, "TestService -> onDestroy, Thread ID: " + Thread.currentThread().getId());
        super.onDestroy();
    }
}

