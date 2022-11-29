package com.xdroid.spring.util.javas.tool.download;

import com.xdroid.spring.frames.okhttp.XDHttpClient;
import com.xdroid.spring.util.androids.tool.XDLog;
import com.xdroid.spring.util.javas.tool.XDFiles;

import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.Call;
import okhttp3.OkHttpClient;
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
                XDLog.e(TAG, "是否支持断点续传：", isSupport);
                //断点位置
                long position = 0;
                if (isSupport) position = Integer.parseInt(target.getBreakpoint());
                XDLog.e(TAG, "断点：position = ", position);
                callBack.onStart("run");

                Request request = new Request.Builder()
                        .url(target.getUrlPath())
                        .addHeader("Range", "bytes=" + target.getBreakpoint() + "-" + target.getEndPoint())
                        .build();
                Call call = XDHttpClient.getOkHttpClient().newCall(request);
                Response response = call.execute();
                //文件总长度
                long contentLength = response.body().contentLength();
                XDLog.e(TAG, "web响应码：", response.code());

                InputStream appData = response.body().byteStream();
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
                        XDLog.e(TAG, "onDownloading", position, "/", contentLength, current, "%");
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
