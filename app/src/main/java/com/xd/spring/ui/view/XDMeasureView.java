package com.xd.spring.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import org.apache.commons.codec.binary.Hex;

public class XDMeasureView extends View {
    private static final String TAG = "XDMeasureView";

    public XDMeasureView(Context context) {
        super(context);
    }

    public XDMeasureView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public XDMeasureView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public XDMeasureView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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
