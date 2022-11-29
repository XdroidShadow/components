package com.xd.spring.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.transition.Fade;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import com.xd.spring.R;
import com.xd.spring.databinding.ActivityTest2Binding;
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

public class IndexActivity2 extends AppCompatActivity {


    private static final String TAG = "IndexActivity2";
    ActivityTest2Binding binding;



    @Override
    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //使用淡入淡出效果
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setExitTransition(new Fade());
        getWindow().setExitTransition(new Fade());
        binding = ActivityTest2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setTransitionName(binding.page2Img, "shareIMG");
        ViewCompat.setTransitionName(binding.page2Img2, "shareIMG2");
    }


}