package com.xd.spring.test;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.os.TraceCompat;
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
import android.os.StrictMode;
import android.transition.Fade;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.PopupWindow;


import com.xd.spring.databinding.ActivityMain2Binding;
import com.xd.spring.test.rxjava.XDTestRxJava;
import com.xd.spring.test.rxjava.events.XDEvent1;
import com.xdroid.annotation.XDImportant;
import com.xdroid.annotation.XDModify;
import com.xdroid.annotation.XDTip;
import com.xdroid.spring.codedesign.launchstarter.XDTaskLauncher;
import com.xdroid.spring.codedesign.launchstarter.XDTaskLauncherDelayed;
import com.xdroid.spring.codedesign.launchstarter.task.XDTask;
import com.xdroid.spring.codedesign.launchstarter.task.XDMainTask;
import com.xdroid.spring.frames.zxing.app.CaptureActivity;
import com.xdroid.spring.frames.zxing.util.ZxingCode;
import com.xdroid.spring.util.androids.tool.XDLayoutInflaters;
import com.xdroid.spring.util.androids.tool.XDLog;
import com.xdroid.spring.util.androids.ui.popwindow.XDPopupWindows;
import com.xdroid.spring.util.javas.tool.XDUnits;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.xdroid.spring.frames.zxing.util.ZxingCode.REQUEST_CODE_QR;

@SuppressLint("NonConstantResourceId")
public class IndexActivity extends AppCompatActivity {
    private static final String TAG = "IndexActivity";

    //    ActivityMainBinding binding;
    ActivityMain2Binding binding;
    Handler handler = new Handler(Looper.getMainLooper()) {

    };

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        XDLayoutInflaters.countInflateTime(getLayoutInflater(), getDelegate());
        super.onCreate(savedInstanceState);
        test();
        testStrictMode();
        testLock();
        testANR();

//        AsyncLayoutInflater

        XDTaskLauncher.newInstance(this).addTasks(
                new XDMainTask() {
                    @Override
                    public void run() {
                        XDLog.e(TAG, "启动器，线程 = ", Thread.currentThread().getName());
                    }
                },
                new XDTask() {
                    @Override
                    public void run() {
                        XDLog.e(TAG, "启动器，线程 = ", Thread.currentThread().getName());
                    }
                }
        ).start();

        XDLog.e(TAG, "delay启动器，线程 = ", Thread.currentThread().getName(), "开始");
        new XDTaskLauncherDelayed().addTask(new XDTask() {
            @Override
            public void run() {
                XDLog.e(TAG, "delay启动器，线程 = ", Thread.currentThread().getName());
            }
        })
                .addTask(new XDTask() {
                    @Override
                    public void run() {
                        XDLog.e(TAG, "delay启动器，线程 = ", Thread.currentThread().getName());
                    }
                }).start();
        XDLog.e(TAG, "delay启动器，线程 = ", Thread.currentThread().getName(), "结束");

//        File traceFile = new File( Environment.getExternalStorageDirectory(),"app.trace");
        File traceFile = new File("data/data/com.xd.spring/files", "app.trace");

        XDLog.e(TAG, "trace路径：", traceFile.getAbsolutePath());

        TraceCompat.beginSection("xdroid");
//        Debug.startMethodTracing("app.trace");

        //使用淡入淡出效果
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setExitTransition(new Fade());
        getWindow().setExitTransition(new Fade());
        //view binding
        binding = ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        Debug.stopMethodTracing();
        TraceCompat.endSection();

        binding.btn1.setOnClickListener(this::testTransition);
//        binding.btn1.setOnClickListener(this::testService);
//        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 0);

//        XDTests.test();


        XDTestRxJava.test();

        testMask();
//        testPopWindow();


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
//        XDZipsTest.testUnZip_dir();
//
//        ArrayList<String> data = new ArrayList<>();
//        Supplier<Boolean> booleanSupplier = data::isEmpty;
//
//        XDDialog xdDialog = new XDDialog(this, "", "", new XDDialog.CallBack() {
//            @Override
//            public void onPositiveClick() {
//
//            }
//        });

//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                XDLog.e(TAG, "循环OOM，XDData");
//                for (int index = 0; index <= 100; index++) {
//                    XDData[] data = new XDData[10000];
//                }
//                handler.postDelayed(this, 100);
//            }
//        }, 100);


    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }


    private static class XDData {
        private String name = "name";
        private String info = "info";
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
//        new XDMask(this).show();
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

    private void test() {
        try {
            throw new RuntimeException("手动抛出异常");
        } catch (Exception e) {
            XDLog.e(TAG, Log.getStackTraceString(e));
        }

        XDLog.e(TAG, "CPU核心数量：", Runtime.getRuntime().availableProcessors());
        XDLog.e(TAG, "maxMemory：", XDUnits.formatFileSize(Runtime.getRuntime().maxMemory()));
        XDLog.e(TAG, "freeMemory：", XDUnits.formatFileSize(Runtime.getRuntime().freeMemory()));
        XDLog.e(TAG, "totalMemory：", XDUnits.formatFileSize(Runtime.getRuntime().totalMemory()));

    }

    public void testStrictMode() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()// or .detectAll()for all detectable problems
                .penaltyLog()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects()
                .penaltyLog()
                .build());


    }


    ExecutorService executorService = Executors.newCachedThreadPool();
    private Object flg1 = "flg1";
    private Object flg2 = "flg2";

    public void testLock() {
//        executorService.execute(() -> {
//            synchronized (flg1) {
//                XDLog.e(TAG, "A-获得flg1");
//                Thread.sleep(500);
//                XDLog.e(TAG, "A-等待flg2");
//                synchronized (flg2) {
//                    XDLog.e(TAG, "A-获得flg2");
//                }
//            }
//        });
//
//        executorService.execute(() -> {
//            synchronized (flg2) {
//                XDLog.e(TAG, "B-获得flg2");
//                Thread.sleep(500);
//                XDLog.e(TAG, "B-等待flg1");
//                synchronized (flg1) {
//                    XDLog.e(TAG, "B-获得flg1");
//                }
//            }
//        });
    }

    public void testANR() {

        executorService.execute(() -> {
            //持有主线程的锁 20秒
            synchronized (IndexActivity.this) {
                Thread.sleep(20 * 1000);
            }
        });

        handler.postDelayed(() -> {
            //等子线程持有主线程锁后，尝试获取主线程锁
            synchronized (IndexActivity.this) {
                XDLog.e(TAG, "进入主线程");
            }
        }, 1000);



    }


}