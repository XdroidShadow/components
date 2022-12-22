package com.xdroid.spring.util.androids.tool;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.Objects;

public class XDActivityUtils {
    private static final String TAG = "XDActivityUtils";

    public static void addFragmentToActivity(@NonNull FragmentManager fragmentManager,
                                             @NonNull Fragment fragment, int frameId) {
        if (fragment.isAdded()) {
            XDLog.e(TAG, "已经添加过了，跳过");
            return;
        }
        FragmentTransaction transaction = Objects.requireNonNull(fragmentManager).beginTransaction();
        transaction.add(frameId, Objects.requireNonNull(fragment));
        transaction.commit();
    }

    public static void removeFragmentFromActivity(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment) {
        FragmentTransaction transaction = Objects.requireNonNull(fragmentManager).beginTransaction();
        if (fragment.isAdded()) {
            transaction.remove(fragment);
        }
        transaction.commit();
    }

    /**
     * 监听APP处于前台还是后台
     * APP内部打开页面会走：page1：onActivityPaused，page2：onActivityResumed
     * 所以任然能准备标识
     */
    public static void observeAppState(Application app, ActivitySateCallBack callBack) {
        app.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(@NonNull Activity activity) {

            }

            @Override
            public void onActivityResumed(@NonNull Activity activity) {
                XDLog.i(TAG, "onActivityResumed", activity.getClass().getName());
                callBack.onForeGround();
            }

            @Override
            public void onActivityPaused(@NonNull Activity activity) {
                XDLog.i(TAG, "onActivityPaused", activity.getClass().getName());
                callBack.onBackGround();
            }

            @Override
            public void onActivityStopped(@NonNull Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {

            }
        });
    }

    public interface ActivitySateCallBack {
        void onForeGround();

        void onBackGround();
    }

}
