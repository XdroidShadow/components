package com.xdroid.spring.util.androids.tool;


import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.LayoutInflaterCompat;

public class XDLayoutInflaters {
    private static final String TAG = "XDLayoutInflaters";


    /**
     * 统计所有view加载的耗时
     * 必须在 super.onCreate(savedInstanceState) 之前调用
     */
    public static void countInflateTime(@NonNull LayoutInflater inflater, AppCompatDelegate delegate) {
        LayoutInflaterCompat.setFactory2(inflater, new LayoutInflater.Factory2() {
            @Override
            public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
                if (TextUtils.equals(name, "TextView")) {
                    // 生成自定义TextView
                }
                long time = System.currentTimeMillis();
                View view = delegate.createView(parent, name, context, attrs);
                XDLog.e(TAG, "【", name, "】 加载耗时： ", (System.currentTimeMillis() - time));
                return view;
            }

            @Override
            public View onCreateView(String name, Context context, AttributeSet attrs) {
                return null;
            }
        });
    }

}
