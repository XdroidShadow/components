package com.xdroid.spring.util.android.tool;

import android.annotation.SuppressLint;
import android.content.Context;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.xdroid.spring.util.android.bean.XDDeviceBounds;
import com.xdroid.spring.util.android.log.XDLog;

/**
 * 获取安卓相关数据
 */
public class XDAndroid {

    private static final String TAG = "XDAndroid";

    /**
     * 获取安卓系统相关的 androidID号
     */
    public static String androidID(Context context) {
        @SuppressLint("HardwareIds") String id = Settings.Secure.getString(
                context.getContentResolver(),
                Settings.Secure.ANDROID_ID
        );
        if ("9774d56d682e549c".equals(id)) return "";
        String res = id == null ? "" : id;
        XDLog.e(TAG, "androidID ： " + res);
        return res;
    }


    /**
     * 重新计算listview的高度
     */
    public static void measureListView(ListView listView) {
        // 获取listview的adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) return;
        int col = 1; // 固定列宽，有多少列
        int totalHeight = 0;
        int subViewCount = listAdapter.getCount();
        for (int i = 0; i < subViewCount; i += col) {
            // 获取listview的每一个item
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            // 获取item的高度和
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight;
        ((ViewGroup.MarginLayoutParams) params).setMargins(0, 0, 0, 0);
        listView.setLayoutParams(params);
    }


    /**
     * 重新计算grid的高度
     */
    public static void measureGrid(GridView gridView, final int numCol) {
        ListAdapter listAdapter = gridView.getAdapter();
        if (listAdapter == null) return;
        int totalHeight = 0;
        int subviewCount = listAdapter.getCount();
        //grid一行只加一次高度，所以 i+=col
        for (int i = 0; i < subviewCount; i += numCol) {
            View listItem = listAdapter.getView(i, null, gridView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        params.height = totalHeight;
        ((ViewGroup.MarginLayoutParams) params).setMargins(0, 0, 0, 0);
        gridView.setLayoutParams(params);
    }

    /**
     * dp转化为px
     */
    public static<T extends  Number> int dpToPx(Context context,T dpValue) {
        final float density = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue.doubleValue() * density + 0.5f);
    }

    //px转化为dp
    public static int pxToDp(Context context, float pxValue) {
        final float density = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / density + 0.5f);
    }


    /**
     * 屏幕宽度
     */
    public static XDDeviceBounds deviceBound(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        // 屏幕宽度（像素）
        int width = dm.widthPixels;
        // 屏幕高度（像素）
        int height = dm.heightPixels;
        // 屏幕密度（0.75 / 1.0 / 1.5）
        float density = dm.density;
        // 屏幕密度dpi（120 / 160 / 240）
        int densityDpi = dm.densityDpi;
        // 屏幕宽度算法:屏幕宽度（像素）/屏幕密度
        int screenWidth = (int) (width / density);  // 屏幕宽度(dp)
        int screenHeight = (int) (height / density);// 屏幕高度(dp)
        return new XDDeviceBounds(screenWidth, screenHeight);
    }

    /**
     * 设置屏幕的背景透明度
     */
    public static void windowAlpha(Window window, float bgAlpha) {
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        window.setAttributes(lp);
    }


}
