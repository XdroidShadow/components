package com.xdroid.spring.example.android.ui.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;

public class TEST_ViewGroup extends ViewGroup {
    public TEST_ViewGroup(Context context) {
        super(context);
    }

    public TEST_ViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TEST_ViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TEST_ViewGroup(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);


    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }
}
