package com.xdroid.spring.example.android.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.xdroid.spring.R;


public class XDView extends View {
    private static final String TAG = "XDView";
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public XDView(Context context) {
        super(context);
    }

    public XDView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.XDView);
        int color = typedArray.getColor(R.styleable.XDView_innerColor, Color.RED);//获取属性值
        setPaint(color);//使用值
        typedArray.recycle();//回收资源
    }

    public XDView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public XDView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void setPaint(int color) {
        Log.e(TAG, "setPaint: color = " + color);
        paint.setColor(color);
        paint.setStrokeWidth(5);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        canvas.drawRect(paddingLeft, paddingTop, getWidth() - paddingRight,
                getHeight() - paddingBottom, paint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        //默认的值
        int defaultWidth = 200;//px
        int defaultHeight = 200;//px

        Log.e(TAG, "onMeasure: widthMode");
        switch (widthMode) {
            case MeasureSpec.UNSPECIFIED:
                Log.e(TAG, "onMeasure: UNSPECIFIED");
                break;
            case MeasureSpec.AT_MOST:
                Log.e(TAG, "onMeasure: AT_MOST");
                break;
            case MeasureSpec.EXACTLY:
                Log.e(TAG, "onMeasure: EXACTLY");
                break;
        }

        Log.e(TAG, "onMeasure: heightMode");
        switch (heightMode) {
            case MeasureSpec.UNSPECIFIED:
                Log.e(TAG, "onMeasure: UNSPECIFIED");
                break;
            case MeasureSpec.AT_MOST:
                Log.e(TAG, "onMeasure: AT_MOST");
                break;
            case MeasureSpec.EXACTLY:
                Log.e(TAG, "onMeasure: EXACTLY");
                break;
        }

        //AT_MOST 就是 wrap_content
        widthSize = (widthMode == MeasureSpec.AT_MOST) ? defaultWidth : widthSize;
        heightSize = (heightMode == MeasureSpec.AT_MOST) ? defaultHeight : heightSize;

        Log.e(TAG, "onMeasure: widthSize = " + widthSize);
        Log.e(TAG, "onMeasure: heightSize = " + heightSize);
        setMeasuredDimension(widthSize, heightSize);

    }


}
