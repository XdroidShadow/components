package com.xdroid.spring.util.androids.tool.handler;

import android.os.Message;

import java.util.concurrent.ConcurrentHashMap;

 class GetDetailHandlerHelper {

    private static ConcurrentHashMap<Message, String> sMsgDetail = new ConcurrentHashMap<>();

    public static ConcurrentHashMap<Message, String> getMsgDetail() {
        return sMsgDetail;
    }

}
