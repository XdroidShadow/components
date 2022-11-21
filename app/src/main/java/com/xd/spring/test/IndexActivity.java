package com.xd.spring.test;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.PopupWindow;


import com.xd.spring.R;
import com.xd.spring.test.rxjava.XDTestRxJava;
import com.xd.spring.test.rxjava.events.XDEvent1;
import com.xdroid.spring.frames.zxing.app.CaptureActivity;
import com.xdroid.spring.frames.zxing.util.ZxingCode;
import com.xdroid.spring.util.androids.tool.XDLog;
import com.xdroid.spring.util.androids.ui.dialog.XDMask;
import com.xdroid.spring.util.androids.ui.popwindow.XDPopupWindows;
import com.xdroid.spring.util.javas.tool.XDFiles;

import org.greenrobot.eventbus.Subscribe;

import static com.xdroid.spring.frames.zxing.util.ZxingCode.REQUEST_CODE_QR;

public class IndexActivity extends AppCompatActivity {
    private static final String TAG = "IndexActivity";

    private LinearLayout container;
    private static boolean isStarted = false;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        container = findViewById(R.id.container);

//        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 0);

//        XDTests.test();

        XDTestRxJava.test();

        //异常监听
//        new XDCrashHandler(this).init();
//        new Handler().postDelayed(this::takeQRCode, 1000);
//        new Handler().postDelayed(this::testPopWindow, 1000);


//        if (!isStarted) {
//            isStarted = true;
//            if (!EventBus.getDefault().isRegistered(this)) {
//                EventBus.getDefault().register(this);
//                startActivity(new Intent(this, IndexActivity2.class));
//            }
//        }


    }


    @Subscribe()
    public void testEventBus(XDEvent1 e) {
        XDLog.e(TAG, "  收到消息", e.getInfo());

    }




    private void testPopWindow() {
        PopupWindow popupWindow = XDPopupWindows.createPopupWindow(this);
        XDPopupWindows.popAction(container, XDPopupWindows.BOTTOM, popupWindow);
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