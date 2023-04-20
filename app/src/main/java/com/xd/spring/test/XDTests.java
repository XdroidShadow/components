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
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;

import com.xdroid.spring.util.androids.tool.XDLog;
import com.xdroid.spring.util.javas.tool.XDUnits;

import java.lang.reflect.Field;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author 小苏州
 */
public class XDTests {
    private static final String TAG = "XDTests";
    public static void test() {
        View V;
        V.startAnimation();

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


        Integer key1 = read("", 1);
        Boolean key2 = read("", false);


        aiSpeechReady(XDTests::test1);
    }

    private static void test1(){

    }

    private static  <T> T read(String key ,T df){
        T t ;
        if (key != null){
           t = df;
       }else {
            t = null;
        }
        return t;
    }

    private static void setFields(){
        String data = "";

        Field[] declaredFields = XDBean.class.getDeclaredFields();

        Context c;
    }

    private static class XDBean{
        String name;
        String info;
        int age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }

    private static ExecutorService executorService = Executors.newCachedThreadPool();
    private static Handler mainHandler = new Handler(Looper.getMainLooper());

    private static void aiSpeechReady(Runnable r){
        executorService.submit(r);
    }

   static   Runnable task =   new Runnable() {
        @Override
        public void run() {
            XDLog.e(TAG,"run");
        }
    };

    private static void testDe(){
        mainHandler.postDelayed(task,100);
        mainHandler.postDelayed(task,100);
        mainHandler.postDelayed(task,100);
        mainHandler.postDelayed(task,100);
        mainHandler.postDelayed(task,100);
    }


}
