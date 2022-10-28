package com.xd.spring.test.java;

import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class XDOkhttpTest {
    OkHttpClient mOkHttpClient = null;


    public static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown; charset=utf-8");
    public static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");

    public void test() {
        //异步上传文件
        Request request = new Request.Builder()
                .url("https://api.github.com/markdown/raw")
                .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, new File("")))
                .build();
    }


    /**
     *   设置超时时间和缓存
     */
    private void initOkHttpClient() {
        File sdcache = new File("缓存目录");
        int cacheSize = 10 * 1024 * 1024;
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .cache(new Cache(sdcache.getAbsoluteFile(), cacheSize));
        mOkHttpClient = builder.build();
    }

    /**
     * get异步请求
     */
    private void getAsynHttp() {
        Request request = new Request.Builder()
                .url("http://www.baidu.com")
                .method("GET", null)
                .build();
        Call mcall = mOkHttpClient.newCall(request);
        mcall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
            }
        });
    }


    /**
     * post异步请求
     */
    private void postAsynHttp() {
        RequestBody formBody = new FormBody.Builder()
                .add("ip", "59.108.54.37")
                .build();
        Request request = new Request.Builder()
                .url("http://ip.taobao.com/service/getIpInfo.php")
                .post(formBody)
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();

            }

        });
    }

    /**
     * 异步上传文件
     */
    private void postAsynFile() {
        File file = new File("文件目录", "log.txt");
        Request request = new Request.Builder()
                .url("https://api.github.com/markdown/raw")
                .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, file))
                .build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
            }
        });
    }

    /**
     * 异步下载文件
     */
    private void downAsynFile() {
        Request request = new Request.Builder()
                .url("http://img.my.csdn.net/uploads/201603/26/1458988468_5804.jpg")
                .build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) {
                InputStream inputStream = response.body().byteStream();
            }
        });
    }

    /**
     *  异步上传Multipart文件
     */
    private void sendMultipart() {
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("title", "wangshu")
                .addFormDataPart("image", "wangshu.jpg",
                        RequestBody.create(MEDIA_TYPE_PNG, new File("/sdcard/wangshu.jpg")))
                .build();
        Request request = new Request.Builder()
                .header("Authorization", "Client-ID " + "...")
                .url("https://api.imgur.com/3/image")
                .post(requestBody)
                .build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }

    /**
     * 设置tag，取消 call
     */
    private void cancel() {
        final Request request = new Request.Builder()
                .url("http://www.baidu.com")
                .cacheControl(CacheControl.FORCE_NETWORK)
                .build();
        Call call = mOkHttpClient.newCall(request);
        final Call finalCall = call;
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (null != response.cacheResponse()) {
                    String str = response.cacheResponse().toString();
                } else {
                    String str = response.networkResponse().toString();
                }
            }
        });
    }


}
