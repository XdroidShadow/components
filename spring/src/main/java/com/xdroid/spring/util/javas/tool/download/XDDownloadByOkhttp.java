package com.xdroid.spring.util.javas.tool.download;

import com.xdroid.spring.frames.okhttp.XDHttpClient;
import com.xdroid.spring.util.androids.tool.XDLog;
import com.xdroid.spring.util.javas.tool.XDFiles;

import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 使用 OKhttp 下载
 */
public class XDDownloadByOkhttp extends XDDownloadTask implements Runnable {
    private static final String TAG = "XDDownloadByOkhttp";


    public XDDownloadByOkhttp(XDDownloadBean target, XDSingleCallBack callBack) {
        super(target, callBack);
    }

    @Override
    public void run() {
        XDFiles.isSupportBreakpointDownload(target.getUrlPath(), (isSupport, cLength) -> {
            try {
                XDLog.i(TAG, "是否支持断点续传：", isSupport);
                XDLog.i(TAG, "下载链接：", target.getUrlPath());
                XDLog.i(TAG, "下载目录：", target.getDestinationPath());
                //断点位置
                long position = 0;
                if (isSupport) position = Integer.parseInt(target.getBreakpoint());
                XDLog.i(TAG, "断点：position = ", position);
                callBack.onStart("run");

                Request request = new Request.Builder()
                        .url(target.getUrlPath())
                        .addHeader("Range", "bytes=" + target.getBreakpoint() + "-" + target.getEndPoint())
                        .build();
                Call call = XDHttpClient.getInstance().newCall(request);
                Response response = call.execute();
                //文件总长度
                long contentLength = response.body().contentLength();
                XDLog.i(TAG, "web响应码：", response.code());

                InputStream appData = response.body().byteStream();
                RandomAccessFile rw = new RandomAccessFile(target.getDestinationPath(), "rwd");
                rw.seek(position);
                byte[] temp = new byte[1024 * 5];
                int dataLen = 0;
                while ((dataLen = appData.read(temp)) > 0) {
                    position += dataLen;
                    rw.write(temp, 0, dataLen);
                    int current = (int) (position * 100 / contentLength);
                    if (percent != current) {
                        percent = current;
                        XDLog.i(TAG, "onDownloading", position, "/", contentLength, current, "%");
                        callBack.onDownloading(position, contentLength);
                    }
                }
                appData.close();
                callBack.onSuccess("", position, contentLength);
            } catch (IOException e) {
                e.printStackTrace();
                callBack.onFail("IOException");
            }
        });


    }

}
