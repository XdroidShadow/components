package com.xd.spring.test;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.leakcanary.LeakCanary;
import com.xdroid.spring.util.androids.tool.XDLog;
import com.xdroid.spring.util.androids.tool.anr.ANRError;
import com.xdroid.spring.util.androids.tool.anr.ANRWatchDog;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class XDApplication extends Application {
    private static final String TAG = "XDApplication";
    private static XDApplication app;
    private static int appState = 0;

    private CountDownLatch countDownLatch = new CountDownLatch(2);
    private ExecutorService executorService = Executors.newCachedThreadPool();


    @Override
    public void onCreate() {
        super.onCreate();

        XDLog.e(TAG, "开始");
        app = this;
        runInSub(this::init);
        runInSub(this::test1);
        runInSub(this::test1);
        runInSub(this::test2);

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        XDLog.e(TAG, "结束");


//        TaskDispatcher.newInstance(this).addTasks(
//
//        ).start();

        ANRWatchDog anrWatchDog = new ANRWatchDog();
        anrWatchDog.setANRListener(new ANRWatchDog.ANRListener() {
            @Override
            public void onAppNotResponding(@NonNull @NotNull ANRError error) {
                XDLog.e(TAG, "触发了ANR，需要进行业务上报");
            }
        });
        anrWatchDog.start();

        testActivityLifecycle();

    }

    public static Application INSTANCE() {
        return app;
    }

    void init() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        countDownLatch.countDown();
    }

    private void test1() {
        int count = 0;
        for (int i = 0; i < 100000; i++) {
            count++;
        }
        XDLog.e(TAG, "test1：", count);
    }

    private void test2() {
        int count = 0;
        for (int i = 0; i < 100000; i++) {
            count++;
        }
        XDLog.e(TAG, "test2：", count);
        countDownLatch.countDown();
    }


    private void runInSub(Runnable r) {
        executorService.execute(r);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();

    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);


    }

    public void testActivityLifecycle() {

    }

}
