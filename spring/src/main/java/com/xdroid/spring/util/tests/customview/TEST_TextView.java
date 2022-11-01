package com.xdroid.spring.util.tests.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

/**
 *   extends 系统组件进行自定义
 */
public class TEST_TextView extends androidx.appcompat.widget.AppCompatTextView {
    public TEST_TextView(Context context) {
        super(context);
    }

    public TEST_TextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TEST_TextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        getLayoutParams();
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(Color.RED);
        p.setStrokeWidth(5);
        canvas.drawLine(0,getHeight()/2,getWidth(),getHeight()/2,p);
        super.onDraw(canvas);
    }
}
