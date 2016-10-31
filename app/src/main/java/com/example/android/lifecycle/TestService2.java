package com.example.android.lifecycle;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class TestService2 extends Service {

    public class MyBinder extends Binder {
        public TestService2 getService(){
            return TestService2.this;
        }
    }

    //通过binder实现调用者client与Service之间的通信
    private MyBinder binder = new MyBinder();

    @Override
    public void onCreate() {
        Log.i(ActivityA.mActivityName,"TestService2 -> onCreate, Thread: " + Thread.currentThread().getName());
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(ActivityA.mActivityName, "TestService2 -> onStartCommand, startId: " + startId + ", Thread: " + Thread.currentThread().getName());
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(ActivityA.mActivityName, "TestService2 -> onBind, Thread: " + Thread.currentThread().getName());
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(ActivityA.mActivityName, "TestService2 -> onUnbind, from:" + intent.getStringExtra("from"));
        return false;
    }

    @Override
    public void onDestroy() {
        Log.i(ActivityA.mActivityName, "TestService2 -> onDestroy, Thread: " + Thread.currentThread().getName());
        super.onDestroy();
    }

    public int getNumber(){
        return 1;
    }
}
