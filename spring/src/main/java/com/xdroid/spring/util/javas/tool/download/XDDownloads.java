package com.xdroid.spring.util.javas.tool.download;

import com.xdroid.spring.util.javas.tool.XDFiles;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
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

    /**
     * 单线程下载
     * one thread download
     */
    @Override
    public void downloadSingleThread(XDDownloadBean target, XDSingleCallBack callBack) {
        switch (target.getChannel()) {
            case OKHTTP:
                executorService.execute(new XDDownloadByOkhttp(target, callBack));
                break;
            case HttpURLConnection:
                executorService.execute(new XDDownloadByURL(target, callBack));
                break;
        }
    }


    /**
     * 多线程下载
     * Multi-threaded download
     * 使用多个单线程下载来完成
     * 等待开发
     */
    private void downloadMultiThread(XDDownloadBean target, XDSingleCallBack callBack) {
        //重点
        try {
            RandomAccessFile rf = new RandomAccessFile("", "rwd");
            rf.setLength(100);//1、设置文件长度
            rf.seek(1);//2、指定位置的写入


            XDFiles.isSupportBreakpointDownload(target.getUrlPath(), (isSupport, cLength) -> {
                switch (target.getChannel()) {
                    case OKHTTP:
                        for (int i = 0; i < target.getThreadCount(); i++) {
                            executorService.execute(new XDDownloadByOkhttp(target, new XDSingleCallBack() {
                                @Override
                                public void onFail(String info) {

                                }

                                @Override
                                public void onSuccess(String info, long pos, long contentLength) {

                                }

                                @Override
                                public void onDownloading(long pos, long contentLength) {

                                }
                            }));
                        }
                        break;
                    case HttpURLConnection:
                        executorService.execute(new XDDownloadByURL(target, callBack));
                        break;
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
