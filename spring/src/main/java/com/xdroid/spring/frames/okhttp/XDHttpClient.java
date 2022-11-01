package com.xdroid.spring.frames.okhttp;

import android.util.Log;

import com.xdroid.spring.frames.okhttp.cookie.SimpleCookieJar;
import com.xdroid.spring.frames.okhttp.listener.XDJsonHandle;
import com.xdroid.spring.frames.okhttp.response.XDFileCallback;
import com.xdroid.spring.frames.okhttp.util.XDHttpsUtil;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * 用来发送get, post请求、设置一些请求的共用参数
 */
public class XDHttpClient {
    private static final int TIME_OUT = 15;
    private static OkHttpClient mOkHttpClient;

    /**
     *   初始化通用实例
     */
    static {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient
                .Builder()
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                });
        // 拦截器 为所有请求添加请求头
        okHttpClientBuilder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Log.e("okhttp", "拦截器");
                long start = System.currentTimeMillis();
                Request request = chain.request()
                        .newBuilder()
                        // 标明发送本次请求的客户端
                        .addHeader("User-Agent", "XDroid")
                        .build();
                Response response = chain.proceed(request);
                long end = System.currentTimeMillis();
                //总共耗时
                System.out.println(request.url().toString() + "  -->    耗时：" + (end - start) / 1000.0 + "秒");
                Response responseNet = response.networkResponse();
                Response responseCache = response.cacheResponse();
                if (responseNet != null) {
                    System.out.println("结果来自net");
                }
                if (responseCache != null) {
                    System.out.println("结果来自cache");
                }
                return response;
            }
        });
        //缓存  --  页面需要缓存的时候可以开启
        Cache cache = new Cache(new File("cache.cache"), 1024 * 1024);
        okHttpClientBuilder.cache(cache);

        okHttpClientBuilder.cookieJar(new SimpleCookieJar());
        okHttpClientBuilder.connectTimeout(TIME_OUT, TimeUnit.SECONDS);
        okHttpClientBuilder.readTimeout(TIME_OUT, TimeUnit.SECONDS);
        okHttpClientBuilder.writeTimeout(TIME_OUT, TimeUnit.SECONDS);
        //允许重定向
        okHttpClientBuilder.followRedirects(true);
        //设置https的支持
        okHttpClientBuilder.sslSocketFactory(XDHttpsUtil.initSSLSocketFactory(), XDHttpsUtil.initTrustManager());
        mOkHttpClient = okHttpClientBuilder.build();
    }

    public static OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

//    /**
//     * 指定cilent信任指定证书
//     *
//     * @param certificates
//     */
//    public static void setCertificates(InputStream... certificates) {
//        mOkHttpClient.newBuilder().sslSocketFactory(HttpsUtils.getSslSocketFactory(certificates, null, null)).build();
//    }

    /**
     * 通过构造好的Request,Callback去发送请求
     *
     * @param request
     * @param handle
     * @return
     */
    public static<T> Call get(Request request, XDJsonHandle<T> handle) {
        Call call = mOkHttpClient.newCall(request);
        //异步请求  传入带json解析的回调，参数是带解析类class的监听
        call.enqueue(handle);
        //execute  同步
        return call;
    }

    public static <T> Call post(Request request, XDJsonHandle<T> handle) {
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(handle);
        return call;
    }

    public static Call downloadFile(Request request, XDJsonHandle<File> handle) {
        Log.e("下载进度","downloadFile");
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new XDFileCallback(handle));
        return call;
    }


}

/**
 * example:
 * String url = "http://39.105.38.116:8080/miyun/appManager/appLogin.do";//?userCode=dmf&passWord=dmf
 * MKParams params = new MKParams();
 * params.put("userCode","df");
 * params.put("passWord","dmf");
 * <p>
 * Call c = MKHttpClient.get(MKRequest.createGetRequest(url,params),new MKDataHandle(LoginResout.class, new MKDataListener() {
 *
 * @Override public void onSuccess(Object responseObj) {
 * LoginResout resout = (LoginResout) responseObj;
 * }
 * @Override public void onFailure(Object reasonObj) {
 * MKHttpException exception = (MKHttpException) reasonObj;
 * MKLog.e("EXCEPTION",exception.getEcode()+"  "+ exception.getEmsg());
 * switch (exception.getEcode()){
 * case MKHttpConstant.NETWORK_ERROR: //网络异常（网络有问题）
 * break;
 * case MKHttpConstant.RESPONSE_NULL://响应为空
 * break;
 * case MKHttpConstant.JSON_ERROR://网络正常，不是JSON格式（接口有问题）
 * break;
 * case MKHttpConstant.MODEL_ERROR://网络正常，模型异常（解析成对应模型出错）
 * break;
 * default:
 * break;
 * }
 * }
 * }));
 */