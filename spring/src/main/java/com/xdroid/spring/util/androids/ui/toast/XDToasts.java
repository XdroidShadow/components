package com.xdroid.spring.util.androids.ui.toast;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.xdroid.spring.R;


public class XDToasts {

    public static void toast(Context c, String info) {
        toast(c, info, false);
    }


    public static void toast(Context c, String info, boolean isLong) {
        View toastView = LayoutInflater.from(c).inflate(R.layout.toast_common, null);
        TextView toastInfoTV = (TextView) toastView.findViewById(R.id.toastInfoTV);
        toastInfoTV.setText(info);
        Toast toast = new Toast(c);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(isLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
        toast.setView(toastView);
        toast.show();
    }

    public static void warning(Context c, String info) {
        toast(c, info, false);
    }

    public static void warning(Context c, String info, boolean isLong) {
        View toastView = LayoutInflater.from(c).inflate(R.layout.toast_warning, null);
        TextView toastInfoTV = (TextView) toastView.findViewById(R.id.toastInfoTV);
        toastInfoTV.setText(info);
        Toast toast = new Toast(c);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(isLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
        toast.setView(toastView);
        toast.show();
    }


}
