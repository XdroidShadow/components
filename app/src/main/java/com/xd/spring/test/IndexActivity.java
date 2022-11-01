package com.xd.spring.test;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.LinearLayout;


import com.aispeech.medicalcheck.xdroid.tool.crash.XDCrashHandler;
import com.aispeech.medicalcheck.xdroid.tool.crash.XDRuntimeLog;
import com.xd.spring.R;
import com.xdroid.spring.frames.zxing.app.CaptureActivity;
import com.xdroid.spring.frames.zxing.util.ZxingCode;
import com.xdroid.spring.util.android.log.XDLog;
import com.xdroid.spring.util.android.ui.dialog.XDMask;

import static com.xdroid.spring.frames.zxing.util.ZxingCode.REQUEST_CODE_QR;
//import com.xdroid.spring.util.android.ui.popwindow.XdPopupWindows;

public class IndexActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private LinearLayout container;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        container = findViewById(R.id.container);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 0);

        XDTests.test();

        //异常监听
        XDRuntimeLog.createLogFiles(this);
        new XDCrashHandler(this).init();


        new Handler().postDelayed(this::takeQRCode, 1000);

    }

    private void testPopWindow() {
//        PopupWindow popupWindow = XdPopupWindows.createPopupWindow(this);
//        XdPopupWindows.popAction(container, XdPopupWindows.BOTTOM, popupWindow);
    }

    private void testMask() {
        new XDMask(this).show();
    }

    public void takeQRCode() {
        Intent intent = new Intent(IndexActivity.this, CaptureActivity.class);
        startActivityForResult(intent, REQUEST_CODE_QR);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_CODE_QR:
                if (resultCode == Activity.RESULT_OK)
                    XDLog.e(TAG, data.getStringExtra(ZxingCode.SCAN_RESULT));
                break;
        }

    }
}