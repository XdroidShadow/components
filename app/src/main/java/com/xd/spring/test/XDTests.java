package com.xd.spring.test;
//
//import com.xdroid.spring.frames.httpRequest.XDHttpClient;
//import com.xdroid.spring.frames.httpRequest.exception.XDHttpErrType;
//import com.xdroid.spring.frames.httpRequest.listener.XDDataListener;
//import com.xdroid.spring.frames.httpRequest.listener.XDJsonHandle;
//import com.xdroid.spring.frames.httpRequest.request.XDRequest;

//import com.xdroid.spring.util.android.image.XDImages;

import android.content.Context;
import android.net.Uri;

import java.util.HashMap;
import java.util.concurrent.LinkedBlockingDeque;

public class XDTests {
    public static void test() {
        //?userCode=dmf&passWord=dmf
        String url = "http://39.105.38.116:8080/miyun/appManager/appLogin.do";
//        XDHttpClient.post(XDRequest.createPostJSONRequest(url, new HashMap<String, Object>() {
//            {
//                put("userCode", "dmf");
//                put("passWord", 123);
//            }
//        }), XDJsonHandle.createJsonHandler(String.class, new XDDataListener<String>() {
//            @Override
//            public void onSuccess(String res) {
//
//            }
//
//            @Override
//            public void onFailure(XDHttpErrType err) {
//
//            }
//        }));


//        new XDImages();

        Uri uri = Uri.fromFile(null);



    }
}
