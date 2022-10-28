package com.xd.spring.test;

import android.app.Notification;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.xd.spring.ui.XDNotification;

public class XDService extends Service {
    private static final String TAG = "XDService";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG, "onBind");
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "onCreate");
        setForeground();
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private void setForeground() {
        Notification notification = XDNotification.productNotification(this);
        startForeground(1, notification);
    }

    @Override
    public ComponentName startForegroundService(Intent service) {
        Log.e(TAG, "startForegroundService");
        return super.startForegroundService(service);
    }

}
