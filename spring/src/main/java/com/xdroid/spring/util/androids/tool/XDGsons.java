package com.xdroid.spring.util.androids.tool;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

public class XDGsons {

    private static Gson instance;

    private XDGsons() {
    }

    public static Gson getInstance() {
        if (instance == null) {
            synchronized (XDGsons.class) {
                if (instance == null) instance = new Gson();
            }
        }
        return instance;
    }

    public void getType() {
        new TypeToken<String>() {
        }.getType();
    }


}
