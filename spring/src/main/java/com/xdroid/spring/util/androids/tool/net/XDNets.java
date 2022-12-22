package com.xdroid.spring.util.androids.tool.net;

import android.app.usage.NetworkStats;
import android.app.usage.NetworkStatsManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.RemoteException;

import androidx.annotation.NonNull;

import com.xdroid.spring.util.androids.tool.XDLog;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * 网络工具
 */
public class XDNets {
    private static final String TAG = "TSHNetTool";


    /**
     * 判断网络是否连接
     */
    @SuppressWarnings({"ConstantConditions", "ForLoopReplaceableByForEach", "IfCanBeSwitch"})
    public static void isConnected(Context context, CallBack callBack) {
        boolean isConnected = false;
        String message = "";

        StringBuilder sb = null;

        //检测API是不是小于21，因为到了API21之后getNetworkInfo(int networkType)方法被弃用
        /**
         *  在系统版本小于21之前，使用以下的方式获取当前网络状态：
         * 先利用Context对象获取ConnectivityManager对象，
         * 然后利用ConnectivityManager对象获取NetworkInfo对象，
         * 然后根据NetworkInfo对象的类型来返回不同的网络状态。
         * 有三种，移动，Wi-Fi，无网络连接。
         */
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {
            //获得ConnectivityManager对象
            ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            //获取ConnectivityManager对象对应的NetworkInfo对象
            //获取WIFI连接的信息
            NetworkInfo wifiNetworkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            //获取移动数据连接的信息
            NetworkInfo dataNetworkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (wifiNetworkInfo.isConnected() && dataNetworkInfo.isConnected()) {
                message = "WIFI已连接,移动数据已连接";
                isConnected = true;
            } else if (wifiNetworkInfo.isConnected() && !dataNetworkInfo.isConnected()) {
                message = "WIFI已连接,移动数据已断开";
                isConnected = true;
            } else if (!wifiNetworkInfo.isConnected() && dataNetworkInfo.isConnected()) {
                message = "WIFI已断开,移动数据已连接";
                isConnected = true;
            } else {
                message = "WIFI已断开,移动数据已断开";
                isConnected = false;
            }
        } else {
            /**
             *  在系统21及之后，获取网络连接状态的方式：利用ConnectivityManager对象获取
             * 所有的网络连接信息，然后遍历每个网络连接，获取相应的NetworkInfo，
             * 然后根据NetworkInfo对象的类型来返回不同的网络状态。
             */
            if (sb != null) {
                sb.setLength(0);
            }
            //获得ConnectivityManager对象
            ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            //获取所有网络连接的信息
            Network[] networks = connMgr.getAllNetworks();
            //用于存放网络连接信息
            sb = new StringBuilder();
            //通过循环将网络信息逐个取出来
            for (int i = 0; i < networks.length; i++) {
                //获取ConnectivityManager对象对应的NetworkInfo对象
                NetworkInfo networkInfo = connMgr.getNetworkInfo(networks[i]);
                sb.append(networkInfo.getTypeName() + " connect is " + networkInfo.isConnected());
            }
            XDLog.e(TAG, "网络连接信息 : " + sb.toString());
            if (sb.toString().equals("WIFI connect is true")) {
                message = "WIFI已连接";
                isConnected = true;
            } else if (sb.toString().equals("MOBILE connect is true")) {
                message = "移动数据已连接";
                isConnected = true;
            } else if (sb.toString().equals("MOBILE connect is trueWIFI connect is true")
                    || sb.toString().equals("WIFI connect is trueMOBILE connect is true")) {
                message = "WIFI已连接,移动数据已连接";
                isConnected = true;
            } else if (sb.toString().equals("MOBILE connect is falseWIFI connect is true")
                    || sb.toString().equals("WIFI connect is trueMOBILE connect is false")) {
                message = "WIFI已连接,移动数据已断开";
                isConnected = true;
            } else if (sb.toString().equals("MOBILE connect is trueWIFI connect is false")
                    || sb.toString().equals("WIFI connect is falseMOBILE connect is true")) {
                message = "WIFI已断开,移动数据已连接";
                isConnected = true;
            } else {
                message = "WIFI已断开,移动数据已断开";
                isConnected = false;
            }
        }
        XDLog.e(TAG, "网络连接状态：" + isConnected + "   信息：" + message);

        if (isConnected) {
            callBack.onConnected(message);
        } else {
            callBack.onUnConnected(message);
        }

    }

    /**
     * 统计 wifi+手机流量
     */
    public static long countMobileTraffic(Context context, long startTime, long endTime) {
        return countMobileTrafficByWifi(context, startTime, endTime)
                + countMobileTrafficByCellular(context, startTime, endTime);
    }

    /**
     * wifi流量
     */
    public static long countMobileTrafficByWifi(Context context, long startTime, long endTime) {
        return countTraffic(context, startTime, endTime, NetworkCapabilities.TRANSPORT_WIFI);
    }

    /**
     * 手机流量
     */
    public static long countMobileTrafficByCellular(Context context, long startTime, long endTime) {
        return countTraffic(context, startTime, endTime, NetworkCapabilities.TRANSPORT_CELLULAR);
    }

    private volatile static int appUid = -1;

    private static long countTraffic(@NotNull Context context, long startTime, long endTime, int type) {
        Objects.requireNonNull(context);
        long netDataReceived = 0;
        long netDataSend = 0;
        NetworkStatsManager manager = (NetworkStatsManager) context.getSystemService(Context.NETWORK_STATS_SERVICE);
        NetworkStats networkStats = null;
        try {
            networkStats = manager.querySummary(type, null, startTime, endTime);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        if (networkStats == null) return 0L;

        NetworkStats.Bucket bucket = new NetworkStats.Bucket();
        int appUid = getAppUid(context);
        while (networkStats.hasNextBucket()) {
            networkStats.getNextBucket(bucket);
            if (appUid == bucket.getUid()) {
                netDataReceived += bucket.getRxBytes();
                netDataSend += bucket.getTxBytes();
            }
        }
        String tag = type == NetworkCapabilities.TRANSPORT_WIFI ? "WIFI" : "移动网络";
        XDLog.e(TAG, tag, "上传流量：", netDataSend);
        XDLog.e(TAG, tag, "下载流量：", netDataReceived);
        return netDataReceived + netDataSend;
    }

    private static int getAppUid(Context context) {
        if (appUid == -1) {
            PackageManager packageManager = context.getApplicationContext().getPackageManager();
            try {
                PackageInfo packageInfo = packageManager.getPackageInfo(context.getApplicationContext().getPackageName(), PackageManager.GET_META_DATA);
                if (packageInfo != null) {
                    appUid = packageInfo.applicationInfo.uid;
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return appUid;
    }

    public interface CallBack {
        void onConnected(String message);

        void onUnConnected(String message);
    }

}
