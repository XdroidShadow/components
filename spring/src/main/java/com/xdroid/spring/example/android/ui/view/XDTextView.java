package com.xdroid.spring.example.android.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

public class XDTextView extends androidx.appcompat.widget.AppCompatTextView {
    public XDTextView(Context context) {
        super(context);
    }

    public XDTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public XDTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
