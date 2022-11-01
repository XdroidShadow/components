package com.xdroid.spring.util.android.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.xdroid.spring.R;
import com.xdroid.spring.util.android.tool.XDAndroids;
import com.xdroid.spring.util.android.tool.XDWindows;


public class XDMask extends Dialog {
    private Context context;
    private ImageView imageView;
    private TextView mashBg;
    private Handler handler = new Handler();

    public XDMask(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_tshmask_layout);
        XDWindows.setDialogFullWindow(getWindow());
        imageView = this.findViewById(R.id.mkLoading);
        mashBg = this.findViewById(R.id.mashBg);
    }

    @Override
    public void show() {
        super.show();
        Animation animation1 = AnimationUtils.loadAnimation(context, R.anim.dialog_translate_topin_loading);
        imageView.startAnimation(animation1);
    }


}
