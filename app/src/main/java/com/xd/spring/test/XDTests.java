//package com.xd.spring.test;
//
//import com.xdroid.spring.httpRequest.XDHttpClient;
//import com.xdroid.spring.httpRequest.exception.XDHttpErrType;
//import com.xdroid.spring.httpRequest.listener.XDDataListener;
//import com.xdroid.spring.httpRequest.listener.XDJsonHandle;
//import com.xdroid.spring.httpRequest.request.XDRequest;
//
//import java.util.HashMap;
//
//public class XDTests {
//    public void test() {
//        String url = "http://39.105.38.116:8080/miyun/appManager/appLogin.do";//?userCode=dmf&passWord=dmf
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
//
//    }
//}
