/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.lifecycle;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.android.lifecycle.util.StatusTracker;
import com.example.android.lifecycle.util.Utils;


public class ActivityA extends Activity {

    public static final String mActivityName = "ALCD";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);
        if(savedInstanceState != null)
        {
            String name = (String)savedInstanceState.getString("name");
        }
        Log.d(mActivityName, getString(R.string.on_create));

        startService();
//        bindService();
    }

    private void  startService(){
        Log.i(mActivityName, "Thread ID: " + Thread.currentThread().getId());

        Log.i(mActivityName, "before test startService");

        //启动Service
        Intent intent1 = new Intent(this, TestService1.class);
        startService(intent1);

        //停止Service
        Intent intent2 = new Intent(this, TestService1.class);
        stopService(intent2);

        Log.i(mActivityName, "after test startService");
    }

    private  void bindService(){
        Intent intent = new Intent(this, TestService2.class);
        Log.i(mActivityName, "ActivityA 执行 bindService");
        bindService(intent, conn, BIND_AUTO_CREATE);
    }

    public ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            Log.i(mActivityName, "ActivityA onServiceConnected");
            TestService2.MyBinder myBinder = (TestService2.MyBinder)binder;
            TestService2 service = myBinder.getService();
            int num = service.getNumber();
            Log.i(mActivityName, "ActivityA 中调用 TestService2的getNumber方法, 结果: " + num);
            Log.i(mActivityName, "ActivityA 执行 unbindService");
            unbindService(conn);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i(mActivityName, "ActivityA onServiceDisconnected");
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(mActivityName, getString(R.string.on_start));
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(mActivityName, getString(R.string.on_restart));
    }

    @Override
    protected void onResume() {
        super.onResume();
         Log.d(mActivityName, getString(R.string.on_resume));
    }

    @Override
    protected void onPause() {
        super.onPause();
         Log.d(mActivityName, getString(R.string.on_pause));
    }

    @Override
    protected void onStop() {
        super.onStop();
         Log.d(mActivityName, getString(R.string.on_stop));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
         Log.d(mActivityName, getString(R.string.on_destroy));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        Log.i(mActivityName, "onSaveInstanceState");
        outState.putString("name", "xiaoluo");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(mActivityName, "onRestoreInstanceState");
        if(savedInstanceState != null)
        {
            String name = (String)savedInstanceState.getString("name");
        }
    }

}
