package com.xdroid.spring.util.androids.tool;

import android.content.Context;

/**
 *   尺寸工具
 */
public class XDDimensions {
    /**
     * dp转化为px
     */
    public static <T extends Number> int dpToPx(Context context, T dpValue) {
        final float density = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue.doubleValue() * density + 0.5f);
    }

    //px转化为dp
    public static int pxToDp(Context context, float pxValue) {
        final float density = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / density + 0.5f);
    }


}
