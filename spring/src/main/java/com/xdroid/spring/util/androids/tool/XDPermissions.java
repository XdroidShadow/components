package com.xdroid.spring.util.androids.tool;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.widget.Toast;

/**
 * 权限工具
 */
public class XDPermissions {

    //所有隐私权限
    public static String[] permissions = new String[]{
            //android.permission-group.CALENDAR
            Manifest.permission.READ_CALENDAR,
            Manifest.permission.WRITE_CALENDAR,
            //android.permission-group.CAMERA
            Manifest.permission.CAMERA,
            //android.permission-group.CONTACTS
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_CONTACTS,
            Manifest.permission.GET_ACCOUNTS,
            //android.permission-group.LOCATION
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            //android.permission-group.MICROPHONE
            Manifest.permission.RECORD_AUDIO,
            //android.permission-group.PHONE
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.READ_CALL_LOG,
            Manifest.permission.WRITE_CALL_LOG,
            Manifest.permission.ADD_VOICEMAIL,
            Manifest.permission.USE_SIP,
            Manifest.permission.PROCESS_OUTGOING_CALLS,
            //android.permission-group.SENSORS
            Manifest.permission.BODY_SENSORS,
            //android.permission-group.SMS
            Manifest.permission.SEND_SMS,
            Manifest.permission.RECEIVE_SMS,
            Manifest.permission.READ_SMS,
            Manifest.permission.RECEIVE_WAP_PUSH,
            Manifest.permission.RECEIVE_MMS,
            //android.permission-group.STORAGE
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    /**
     * 悬浮窗口权限判断
     */
    public static boolean CheckFloatingPermission(Activity context) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (Settings.canDrawOverlays(context)) {
                return true;
            } else {
                //若没有权限，提示获取.
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                Toast.makeText(context, "需要取得权限以使用悬浮窗", Toast.LENGTH_SHORT).show();
                context.startActivityForResult(intent, 1);
                return false;
            }
        }
        return true;
    }


}
