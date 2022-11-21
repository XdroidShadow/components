package com.xdroid.spring.util.javas.tool.download;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 断点续传工具
 */
public class XDDownloads implements IXDDownloads {
    private static List<XDDownloadBean> downloadTargets = new ArrayList<>();
    private static ExecutorService executorService = Executors.newCachedThreadPool();
    private volatile static XDDownloads INSTANCE;

    private XDDownloads() {
        if (INSTANCE != null) throw new RuntimeException("不允许初始化！");
    }

    public static XDDownloads getInstance() {
        if (INSTANCE == null) {
            synchronized (XDDownloads.class) {
                if (INSTANCE == null) {
                    INSTANCE = new XDDownloads();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public void download(XDDownloadBean target, XDDownloadCallBack callBack) {
        switch (target.getChannel()) {
            case OKHTTP:
                executorService.execute(new XDDownloadByOkhttp(target, callBack));
                break;
            case HttpURLConnection:
                executorService.execute(new XDDownloadByURL(target, callBack));
                break;
        }
    }


}
