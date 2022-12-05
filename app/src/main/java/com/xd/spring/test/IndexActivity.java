package com.xd.spring.test;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.transition.Fade;
import android.view.View;
import android.view.Window;
import android.widget.PopupWindow;


import com.google.common.base.Supplier;
import com.xd.spring.R;
import com.xd.spring.databinding.ActivityMainBinding;
import com.xd.spring.test.rxjava.events.XDEvent1;
import com.xdroid.annotation.XDImportant;
import com.xdroid.annotation.XDModify;
import com.xdroid.annotation.XDTip;
import com.xdroid.spring.frames.zxing.app.CaptureActivity;
import com.xdroid.spring.frames.zxing.util.ZxingCode;
import com.xdroid.spring.util.androids.tool.XDCaches;
import com.xdroid.spring.util.androids.tool.XDLog;
import com.xdroid.spring.util.androids.ui.dialog.XDDialog;
import com.xdroid.spring.util.androids.ui.dialog.XDMask;
import com.xdroid.spring.util.androids.ui.popwindow.XDPopupWindows;
import com.xdroid.spring.util.javas.tool.XDFiles;
import com.xdroid.spring.util.javas.tool.zip.test.XDZipsTest;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;

import static com.xdroid.spring.frames.zxing.util.ZxingCode.REQUEST_CODE_QR;

@SuppressLint("NonConstantResourceId")
public class IndexActivity extends AppCompatActivity {
    private static final String TAG = "IndexActivity";

    ActivityMainBinding binding;
    Handler handler = new Handler(Looper.getMainLooper());

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //使用淡入淡出效果
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setExitTransition(new Fade());
        getWindow().setExitTransition(new Fade());
        //view binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


//        binding.btn1.setOnClickListener(this::testTransition);
        binding.btn1.setOnClickListener(this::testService);
//        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 0);

//        XDTests.test();

//        XDTestRxJava.test();

        //异常监听
//        new XDCrashHandler(this).init();
//        new Handler().postDelayed(this::takeQRCode, 1000);
//        new Handler().postDelayed(this::testPopWindow, 1000);

//        if (!isStarted) {
//            isStarted = true;
//            if (!EventBus.getDefault().isRegistered(this)) {
        EventBus.getDefault().register(this);
//                startActivity(new Intent(this, IndexActivity2.class));
//            }
//        }
//        testAnnotation("","",1L);


//        XDTestGeneric.testZip();
//        XDZipsTest.testZip();
        XDZipsTest.testUnZip_dir();

        ArrayList<String> data = new ArrayList<>();
        Supplier<Boolean> booleanSupplier = data::isEmpty;

        XDDialog xdDialog = new XDDialog(this, "", "", new XDDialog.CallBack() {
            @Override
            public void onPositiveClick() {

            }
        });

    }

    /**
     * 转场动画
     */
    public void testTransition(View v) {
        ActivityOptionsCompat shareIMG = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this, new Pair<>(binding.page1Img, "shareIMG"), new Pair<>(binding.page1Img2, "shareIMG2")
        );

        Bundle bundle = shareIMG.toBundle();
        ArrayList<String> o = (ArrayList<String>) bundle.get("android:activity.sharedElementNames");
        XDLog.e(TAG, "bundle = ", bundle);
        for (String s : o) {
            XDLog.e(TAG, "bundle = ", s);
        }

        startActivity(new Intent(IndexActivity.this, IndexActivity2.class), shareIMG.toBundle());

    }

    public void testService(View v) {
        XDLog.e(TAG, "ServiceConnection = testService");
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.xd.spring",
                "com.xd.spring.aidlTest.servers.XDAidlService"));
        ServiceConnection connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                XDLog.e(TAG, "ServiceConnection = onServiceConnected");
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                XDLog.e(TAG, "ServiceConnection = onServiceDisconnected");
            }
        };

//        Intent intent1  = new Intent(this, XDAidlService.class);

        bindService(intent, connection, Context.BIND_AUTO_CREATE);


    }


    @XDImportant("网络ip")
    public static final String configuration_ip = "39.105.38.116";
    @XDModify("修改了port")
    public static final String configuration_port = "8080";

    @XDTip("这边是测试参数要注意")
    public void testAnnotation(@XDTip("参数要注意") String info1, String info2, long time) {
        @XDTip("局部变量")
        int temp = 100;
    }

    private void testPopWindow() {
        PopupWindow popupWindow = XDPopupWindows.createPopupWindow(this);
        XDPopupWindows.popAction(binding.getRoot(), XDPopupWindows.BOTTOM, popupWindow);

    }

    private void testMask() {
        new XDMask(this).show();
    }

    @Subscribe()
    public void testEventBus(XDEvent1 e) {
        XDLog.e(TAG, "  收到消息", e.getInfo());
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