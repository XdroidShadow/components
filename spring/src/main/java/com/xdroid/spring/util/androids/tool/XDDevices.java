package com.xdroid.spring.util.androids.tool;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.xdroid.spring.util.androids.bean.XDDeviceBounds;
import com.xdroid.spring.util.javas.tool.XDUnits;

/**
 * 安卓设备工具
 */
public class XDDevices {
    private static final String TAG = "XDDevices";

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


    /**XDNetTool
     *   硬件 - 磁盘大小
     */
    public static String devRom() {
        StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
        //块大小
        long blockSize = statFs.getBlockSize();
        //可用块数量
        long availableCount = statFs.getAvailableBlocks();
        return  XDUnits.formatFileSize(blockSize * availableCount);
    }


}
