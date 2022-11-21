package com.xdroid.spring.util.javas.tool.download;

/**
 * 下载回调
 */
public interface XDDownloadCallBack {

    default void onStart(String info) {

    }

    void onFail(String info);

    void onSuccess(String info, long pos, long contentLength);


    void onDownloading(long pos, long contentLength);

}
