//package com.xd.spring.httpRequest.response;
//
//import android.os.Handler;
//import android.os.Looper;
//import android.util.Log;
//
//import com.google.gson.Gson;
//import com.xd.spring.httpRequest.exception.XDHttpErrType;
//import com.xd.spring.httpRequest.listener.MKCookieListener;
//import com.xd.spring.httpRequest.listener.XDJsonHandle;
//import com.xd.spring.httpRequest.listener.MKDataListener;
//
//import org.jetbrains.annotations.NotNull;
//
//import java.io.IOException;
//import java.util.ArrayList;
//
//import okhttp3.Call;
//import okhttp3.Callback;
//import okhttp3.Headers;
//import okhttp3.Response;
//
///**
// * 处理JSON回调
// */
//public class MKJsonCallback<T> implements Callback {
//    // decide the server it
//    protected final String COOKIE_STORE = "Set-Cookie";
//
//    // UI线程
//    private final Handler mainHandler;
//    private final MKDataListener<T> mListener;
//    private final Class<T> mClass;
//
//    public MKJsonCallback(XDJsonHandle<T> handle) {
//        this.mListener = handle.mListener;
//        this.mClass = handle.mClass;
//        this.mainHandler = new Handler(Looper.getMainLooper());
//    }
//
//    @Override
//    public void onFailure(@NotNull final Call call, @NotNull final IOException ioexception) {
//        mainHandler.post(() -> mListener.onFailure(XDHttpErrType.errNetWork));
//    }
//
//    @Override
//    public void onResponse(@NotNull final Call call, @NotNull final Response response) throws IOException {
//        if ( response.body() == null){
//            mainHandler.post(() -> mListener.onFailure(XDHttpErrType.errNetWork));
//            return;
//        }
//        final String result = response.body().string();
//        final ArrayList<String> cookieLists = handleCookie(response.headers());
//        mainHandler.post(() -> {
//            handleResponse(result);
//            //handle the cookie
//            if (mListener instanceof MKCookieListener) {
//                ((MKCookieListener) mListener).onCookie(cookieLists);
//            }
//        });
//    }
//
//    private ArrayList<String> handleCookie(Headers headers) {
//        ArrayList<String> tempList = new ArrayList<String>();
//        for (int i = 0; i < headers.size(); i++) {
//            if (headers.name(i).equalsIgnoreCase(COOKIE_STORE)) {
//                tempList.add(headers.value(i));
//            }
//        }
//        return tempList;
//    }
//
//    private void handleResponse(Object responseObj) {
//        if (responseObj == null || responseObj.toString().trim().equals("")) {
//            //已经有响应，但是数据为空
//            mListener.onFailure(XDHttpErrType.errBlank);
//            return;
//        }
//        try {
//            Log.e("网络返回的数据：", responseObj.toString());
//            if (mClass == null) {
//                mListener.onSuccess(null);
//            }else if (mClass == String.class){
//                mListener.onSuccess((T) responseObj.toString());
//            }else {
//                T jsonRes = new Gson().fromJson(responseObj.toString(), mClass);
//                if (jsonRes != null) {
//                    mListener.onSuccess(jsonRes);
//                } else {
//                    mListener.onFailure(XDHttpErrType.errBean);
//                }
//            }
//        } catch (Exception e) {
//            //不是json格式
//            mListener.onFailure(XDHttpErrType.errJson);
//            e.printStackTrace();
//        }
//    }
//}