package com.xdroid.spring.frames.okhttp.request;


import android.util.Log;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Type;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * 请求体
 */
public class XDRequest {
    public static Gson gson = new Gson();
    private static final String TAG = "XDRequest";

    /**
     * 创建带参数的请求（post）
     */
    public static Request createPostRequest(String url, XDParams params) {
        return createPostRequest(url, params, null);
    }

    /**
     * 创建带参数、带请求头的请求（post）
     * post表单
     */
    public static Request createPostRequest(String url, XDParams params, XDParams headers) {
        //拼接参数
        FormBody.Builder mFormBodyBuild = new FormBody.Builder();
        if (params != null) {
            for (Map.Entry<String, String> entry : params.urlParams.entrySet()) {
                mFormBodyBuild.add(entry.getKey(), entry.getValue());
            }
        }
        //添加请求头
        Headers.Builder mHeaderBuild = new Headers.Builder();
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.urlParams.entrySet()) {
                mHeaderBuild.add(entry.getKey(), entry.getValue());
            }
        }
        FormBody mFormBody = mFormBodyBuild.build();
        Headers mHeader = mHeaderBuild.build();
        Request request = new Request.Builder().url(url).
                post(mFormBody).
                headers(mHeader)
                .build();
        return request;
    }

    /**
     * 创建带参数的请求（get）
     */
    public static Request createGetRequest(String url, XDParams params) {
        return createGetRequest(url, params, null);
    }

    /**
     * 创建带参数、带请求头的请求（get）
     */
    public static Request createGetRequest(String url, XDParams params, XDParams headers) {
        StringBuilder urlBuilder = new StringBuilder(url).append("?");
        if (params != null) {
            for (Map.Entry<String, String> entry : params.urlParams.entrySet()) {
                urlBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
        }
        //添加请求头
        Headers.Builder mHeaderBuild = new Headers.Builder();
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.urlParams.entrySet()) {
                mHeaderBuild.add(entry.getKey(), entry.getValue());
            }
        }
        Headers mHeader = mHeaderBuild.build();
        Log.e(TAG, "HTTP_URL  " + urlBuilder.substring(0, urlBuilder.length() - 1));
        return new Request.Builder().
                url(urlBuilder.substring(0, urlBuilder.length() - 1))
                .get()
                //.cacheControl(CacheControl.FORCE_NETWORK)//控制强制使用网络加载数据
                .headers(mHeader)
                .build();
    }

    /**
     *
     */
    public static Request createMonitorRequest(String url, XDParams params) {
        StringBuilder urlBuilder = new StringBuilder(url).append("&");
        if (params != null && params.hasParams()) {
            for (Map.Entry<String, String> entry : params.urlParams.entrySet()) {
                urlBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
        }
        return new Request.Builder().url(urlBuilder.substring(0, urlBuilder.length() - 1)).get().build();
    }

    /**
     * 文件上传请求
     */
    private static final MediaType FILE_TYPE = MediaType.parse("application/octet-stream");

    public static Request createMultiPostRequest(String url, XDParams params) {
        MultipartBody.Builder requestBody = new MultipartBody.Builder();
        requestBody.setType(MultipartBody.FORM);
        if (params != null) {

            for (Map.Entry<String, Object> entry : params.fileParams.entrySet()) {
                if (entry.getValue() instanceof File) {
                    requestBody.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + entry.getKey() + "\""),
                            RequestBody.create(FILE_TYPE, (File) entry.getValue()));
                } else if (entry.getValue() instanceof String) {
                    requestBody.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + entry.getKey() + "\""),
                            RequestBody.create(null, (String) entry.getValue()));
                }
            }
        }
        return new Request.Builder().url(url).post(requestBody.build()).build();
    }

    /**
     * 组装JSON 格式的request
     */
    private final static MediaType MediaType_JSON = MediaType.parse("application/json; charset=utf-8");

    /**
     * post JSON 请求
     */
    public static <T, V> Request createPostJSONRequest(String url, Map<T, V> params) {
        return createPostJSONRequest(url, params, null);
    }

    private static <T, V> Request createPostJSONRequest(String url, Map<T, V> params, XDParams headers) {
        Type type = new TypeToken<Map<T, V>>() {
        }.getType();
        String data = gson.toJson(params, type);
        Log.e(TAG, "请求参数：" + data);
        String s = new Gson().toJson(params);
//        RequestBody requestBody = RequestBody.create(JSON, data);
        RequestBody requestBody = RequestBody.create(data, MediaType_JSON);
        return new Request.Builder()
                .post(requestBody)
                .url(url)
                .build();
    }

    /**
     * 组装XML 格式的request
     */
    private final static MediaType XML = MediaType.parse("application/xml; charset=utf-8");

    public static Request createPostXMLRequest(String url, String params) {
        return createPostXMLRequest(url, params, null);
    }

    private static Request createPostXMLRequest(String url, String params, XDParams headers) {
        RequestBody requestBody = RequestBody.create(XML, params);
        Request request = new Request.Builder()
                .post(requestBody)
                .url(url)
                .build();
        return request;
    }

    /**
     * 将map转换为String字符串
     */
    private static String createJsonParams(Map<String, String> params) {
        String res = "";
        try {
            JSONObject json = new JSONObject();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                json.put(entry.getKey(), entry.getValue());
            }
            res = json.toString();
            Log.e(TAG, "createJsonParams: " + res);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return res;
    }

}