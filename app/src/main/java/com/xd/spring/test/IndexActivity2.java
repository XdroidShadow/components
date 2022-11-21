package com.xd.spring.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.xd.spring.R;
import com.xd.spring.test.rxjava.XDTestRxJava;
import com.xd.spring.test.rxjava.events.XDEvent1;
import com.xd.spring.test.rxjava.events.XDEvent2;
import com.xdroid.spring.frames.zxing.app.CaptureActivity;
import com.xdroid.spring.frames.zxing.util.ZxingCode;
import com.xdroid.spring.util.androids.tool.XDLog;
import com.xdroid.spring.util.androids.ui.dialog.XDMask;
//import com.xdroid.spring.util.androids.ui.popwindow.XDPopupWindows;
import com.xdroid.spring.util.androids.ui.popwindow.XDPopupWindows;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.functions.Consumer;

import static com.xdroid.spring.frames.zxing.util.ZxingCode.REQUEST_CODE_QR;

public class IndexActivity2 extends IndexActivity {


    private static final String TAG = "IndexActivity2";

    private LinearLayout container;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        container = findViewById(R.id.container);


//        EventBus.getDefault().register(IndexActivity2.this);
        //RxJava 测试

        Observable.range(1,10)
                .delay(3000, TimeUnit.MILLISECONDS)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Throwable {

                        XDTestRxJava.testEventBus(" - "+integer);
                    }
                });

    }


    @Subscribe()
    public void testEventBus2(XDEvent2 e) {
        XDLog.e(TAG, "  收到消息", e.getInfo());

    }


    private void testPopWindow() {
        PopupWindow popupWindow = XDPopupWindows.createPopupWindow(this);
        XDPopupWindows.popAction(container, XDPopupWindows.BOTTOM, popupWindow);
    }

    private void testMask() {
        new XDMask(this).show();
    }

    public void takeQRCode() {
        Intent intent = new Intent(IndexActivity2.this, CaptureActivity.class);
        startActivityForResult(intent, REQUEST_CODE_QR);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_CODE_QR:
                if (resultCode == Activity.RESULT_OK)
                    XDLog.e(TAG, data.getStringExtra(ZxingCode.SCAN_RESULT));
                break;
        }

    }
}