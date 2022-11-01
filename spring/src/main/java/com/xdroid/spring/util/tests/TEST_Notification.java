package com.xdroid.spring.util.tests;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;

import com.xdroid.spring.R;


import static android.app.PendingIntent.FLAG_CANCEL_CURRENT;
import static android.content.Context.NOTIFICATION_SERVICE;


/**
 * 问题：
 *   qq音乐的锁屏播放是怎么实现的?
 *   怎么操作RemoteViews中的view?
 *
 */
public class TEST_Notification {

    /**
     * 普通式通知
     */
    public static void testNotification1(Context context) {
//        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("www.baidu.com"));
        Intent intent = new Intent(context, TEST_RecycleView.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        String channelId = "xdroid_1";
        Notification notification = new NotificationCompat.Builder(context, channelId)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)//跳转对象
                .setSmallIcon(R.drawable.logo)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.logo))
                .setContentTitle("这是一个普通通知-标题")
                .setContentText("这是一个普通通知-内容")
                .setWhen(System.currentTimeMillis())
                .build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            //设置通道号  channelid要一致
            NotificationChannel nc = new NotificationChannel(channelId, "xdroid", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(nc);
        }

        notificationManager.notify(1, notification);
    }

    /**
     * 折叠式通知
     */
    public static void testNotification2(Context context) {
//        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("www.baidu.com"));
        Intent intent = new Intent(context, TEST_RecycleView.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        //折叠自定义view
        RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.remote_notification_view);

        String channelId = "xdroid_1";
        Notification notification = new NotificationCompat.Builder(context, channelId)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)//跳转对象
                .setSmallIcon(R.drawable.logo)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.logo))
                .setContentTitle("折叠通知-标题")
                .setContentText("折叠通知-内容")
//                .setContent(remoteView)//直接将折叠views预览
                .setCustomBigContentView(remoteView)//下拉通知，显示折叠views
                .setWhen(System.currentTimeMillis())
                .setVisibility(NotificationCompat.VISIBILITY_SECRET)
                .build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            //设置通道号  channelid要一致
            NotificationChannel nc = new NotificationChannel(channelId, "xdroid", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(nc);
        }

        notificationManager.notify(1, notification);
    }


    /**
     * 悬挂式通知
     */
    public static void testNotification3(Context context) {
        Intent intent = new Intent(context, TEST_RecycleView.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, FLAG_CANCEL_CURRENT);

        Intent intentHang = new Intent(context, TEST_RecycleView.class);
        intentHang.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent pendingIntentHang = PendingIntent.getActivity(context, 0, intentHang, FLAG_CANCEL_CURRENT);

        String channelId = "xdroid_1";
        Notification notification = new NotificationCompat.Builder(context, channelId)
                .setAutoCancel(true)
//                .setContentIntent(pendingIntent)//跳转对象
                .setFullScreenIntent(pendingIntentHang, true)//悬挂式通知
                .setSmallIcon(R.drawable.logo)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.logo))
                .setContentTitle("悬挂式通知-标题")
                .setContentText("悬挂式通知-内容")
                .setWhen(System.currentTimeMillis())
                .build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            //设置通道号  channelid要一致
            NotificationChannel nc = new NotificationChannel(channelId, "xdroid", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(nc);
        }

        notificationManager.notify(1, notification);
    }


    public static Notification productNotification(Context context){
        Intent intent = new Intent(context, TEST_RecycleView.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, FLAG_CANCEL_CURRENT);

        Intent intentHang = new Intent(context, TEST_RecycleView.class);
        intentHang.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent pendingIntentHang = PendingIntent.getActivity(context, 0, intentHang, FLAG_CANCEL_CURRENT);

        String channelId = "xdroid_1";
        Notification notification = new NotificationCompat.Builder(context, channelId)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)//跳转对象
//                .setFullScreenIntent(pendingIntentHang, true)//悬挂式通知
                .setSmallIcon(R.drawable.logo)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.logo))
                .setContentTitle("前台通知")
                .setContentText("配合startForegroundService")
                .setWhen(System.currentTimeMillis())
                .build();

        return notification;
    }

}
