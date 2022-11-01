package com.xd.spring.test;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.LinearLayout;
import android.widget.PopupWindow;


import com.xd.spring.R;
import com.xdroid.spring.util.android.ui.dialog.XDMask;
//import com.xdroid.spring.util.android.ui.popwindow.XdPopupWindows;

public class IndexActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private LinearLayout container;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        container = findViewById(R.id.container);

        XDTests.test();


        new Handler().postDelayed(this::testMask,100);

    }

    private void testPopWindow() {
//        PopupWindow popupWindow = XdPopupWindows.createPopupWindow(this);
//        XdPopupWindows.popAction(container, XdPopupWindows.BOTTOM, popupWindow);
    }

    private void testMask() {
        new XDMask(this).show();
    }

}