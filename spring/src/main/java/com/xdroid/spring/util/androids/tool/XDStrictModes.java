package com.xdroid.spring.util.androids.tool;

import android.os.StrictMode;


/**
 *   严格模式
 */
public class XDStrictModes {


    public static void setStrictMode() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()// or .detectAll()for all detectable problems
                .penaltyLog()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects()
                .penaltyLog()
                .build());
        XDLog.e("XDStrictModes","开启StrictMode模式");

    }
}
