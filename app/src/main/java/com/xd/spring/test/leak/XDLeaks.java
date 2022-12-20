package com.xd.spring.test.leak;

import android.content.Context;

public class XDLeaks {

    private static volatile Context context;

    public XDLeaks(Context context) {
        this.context = context;
    }

    private static XDLeaks instance;

    public static XDLeaks newInstance(Context c) {
        if (instance == null) {
            synchronized (XDLeaks.class) {
                if (instance == null) instance = new XDLeaks(c);
            }
        }
        return instance;
    }

}
