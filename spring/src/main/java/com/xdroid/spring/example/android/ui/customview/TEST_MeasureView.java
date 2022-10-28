package com.xdroid.spring.example.android.ui.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

public class TEST_MeasureView extends View {
    private static final String TAG = "XDMeasureView";

    public TEST_MeasureView(Context context) {
        super(context);
    }

    public TEST_MeasureView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TEST_MeasureView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TEST_MeasureView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);

        Log.e(TAG, "onMeasure: " + mode );
        switch (mode){
            case MeasureSpec.AT_MOST:
                Log.e(TAG, "onMeasure: AT_MOST"  );
                break;
            case MeasureSpec.EXACTLY:
                Log.e(TAG, "onMeasure: EXACTLY"  );
                break;
            case MeasureSpec.UNSPECIFIED:
                Log.e(TAG, "onMeasure: UNSPECIFIED"  );
                break;
        }
        Log.e(TAG, "onMeasure widthMeasureSpec = " + Integer.toBinaryString(widthMeasureSpec) );
        Log.e(TAG, "onMeasure size = " + size );


    }


}
