package com.xdroid.spring.util.javas.tool.download;

import com.xdroid.spring.util.androids.tool.XDLog;
import com.xdroid.spring.util.javas.tool.XDFiles;

import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 使用 HttpURLConnection 下载
 */
public class XDDownloadByURL extends XDDownloadTask implements Runnable {
    private static final String TAG = "XDDownloadByURL";

    public XDDownloadByURL(XDDownloadBean target, XDSingleCallBack callBack) {
        super(target, callBack);
    }

    @Override
    public void run() {
        XDFiles.isSupportBreakpointDownload(target.getUrlPath(),
                new XDFiles.CheckBreakpointCallBack() {
                    @Override
                    public void isSupport(Boolean isSupport, long cLength) {
                        try {
                            XDLog.e(TAG, "是否支持断点续传：", isSupport);
                            //断点位置
                            long position = 0;
                            if (isSupport) position = Integer.parseInt(target.getBreakpoint());
                            XDLog.e(TAG, "断点：position = ", position);
                            callBack.onStart("run");
                            URL url = new URL(target.getUrlPath());
                            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                            long contentLength = urlConnection.getContentLength();
                            XDLog.e(TAG, "web响应码：", urlConnection.getResponseCode());
                            //断点续传配置
                            if (isSupport) {
                                urlConnection.setRequestProperty("Range", "bytes=" + position + "-" + contentLength);
                            }
                            InputStream appData = urlConnection.getInputStream();
                            RandomAccessFile rw = new RandomAccessFile(target.getDestinationPath(), "rwd");
                            rw.seek(position);
                            byte[] temp = new byte[1024 * 5];
                            int dataLen = 0;
                            while ((dataLen = appData.read(temp)) > 0) {
                                position += dataLen;
                                rw.write(temp, 0, dataLen);
                                callBack.onDownloading(position, contentLength);
                                int current = (int) (position * 100 / contentLength);
                                if (percent != current) {
                                    percent = current;
                                    XDLog.e(TAG, "onDownloading", position, " - ", contentLength, current, "%");
                                }
                            }
                            appData.close();
                            callBack.onSuccess("", position, contentLength);
                        } catch (Exception e) {
                            e.printStackTrace();
                            callBack.onFail("Exception");
                        }
                    }
                });
    }
}
