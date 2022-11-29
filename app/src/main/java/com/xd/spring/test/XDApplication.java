package com.xd.spring.test;

import android.app.Application;

public class XDApplication extends Application {
    private static XDApplication app;

    @Override
    public void onCreate() {
        super.onCreate();

        app = this;
    }

    public static Application INSTANCE(){
        return app;
    }
}
