package com.xdroid.spring.util.androids.tool.net;

import android.content.Context;
import android.net.TrafficStats;

import com.xdroid.spring.util.androids.tool.XDLog;


/**
 * TrafficStats.getTotalRxBytes()
 * 这是用来统计总流量的
 * 循环靠外部来控制
 */
public class NetSpeed {
    private static final String TAG = "NetSpeed";

    //下载
    private static long lastReceiveTime = 0;
    private static long lastTotalReceivedBytes = 0;
    //上传
    private static long lastUpTimeStamp = 0;
    private static long lastTotalUploadBytes = 0;

    /**
     * 下载
     */
    public static long downloadNetSpeed(Context c) {
        long now = System.currentTimeMillis();
        long nowTotalRxBytes = getTotalRxBytes();
        if (lastReceiveTime == 0 || lastTotalReceivedBytes == 0) {
            lastReceiveTime = now;
            lastTotalReceivedBytes = nowTotalRxBytes;
            return 0L;
        }
        long speed = ((nowTotalRxBytes - lastTotalReceivedBytes) * 1000 / (now - lastReceiveTime));//毫秒转换
        lastReceiveTime = now;
        lastTotalReceivedBytes = nowTotalRxBytes;
        XDLog.e(TAG, "下载网速：", speed);
        return speed;
    }

    /**
     * 上传网速
     */
    public static long uploadNetSpeed(Context c) {
        long nowTotalTxBytes = getTotalTxBytes();
        long now = System.currentTimeMillis();
        if (lastUpTimeStamp == 0 || lastTotalUploadBytes == 0) {
            lastUpTimeStamp = now;
            lastTotalUploadBytes = nowTotalTxBytes;
            return 0L;
        }
        long speed = ((nowTotalTxBytes - lastTotalUploadBytes) * 1000 / (now - lastUpTimeStamp));//毫秒转换
        lastUpTimeStamp = now;
        lastTotalUploadBytes = nowTotalTxBytes;
        XDLog.e(TAG, "上传网速：", speed);
        return speed;
    }

    /**
     * 下载数据
     */
    private static long getTotalRxBytes() {
        return TrafficStats.getTotalRxBytes();//B
    }

    /**
     * 上传数据
     */
    private static long getTotalTxBytes() {
        return TrafficStats.getTotalTxBytes();//B
    }


}
