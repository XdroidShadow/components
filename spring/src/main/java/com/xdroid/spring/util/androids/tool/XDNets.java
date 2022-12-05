package com.xdroid.spring.util.androids.tool;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.xdroid.spring.util.androids.tool.XDLog;

/**
 *   网络工具
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
        XDLog.e(TAG,"网络连接状态："+isConnected + "   信息："+ message);

        if (isConnected){
            callBack.onConnected(message);
        }else {
            callBack.onUnConnected(message);
        }

    }

    private interface CallBack {
        void onConnected(String message);
        void onUnConnected(String message);
    }

}
