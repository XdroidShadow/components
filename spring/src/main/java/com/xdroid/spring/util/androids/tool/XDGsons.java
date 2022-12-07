package com.xdroid.spring.util.androids.tool;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

public class XDGsons {

    private static XDGsons instance;

    private XDGsons() {
    }

    public static XDGsons getInstance() {
        if (instance == null) {
            synchronized (XDGsons.class) {
                if (instance == null) instance = new XDGsons();
            }
        }
        return instance;
    }

    public void test() {

    }


}
