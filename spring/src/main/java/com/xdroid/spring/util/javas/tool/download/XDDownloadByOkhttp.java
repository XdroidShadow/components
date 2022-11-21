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
    private static final String TAG = "XDDownloadTaskOkhttp";


    public XDDownloadByOkhttp(XDDownloadBean target, XDDownloadCallBack callBack) {
        super(target, callBack);
    }

    @Override
    public void run() {
        XDFiles.isSupportBreakpointDownload(target.getUrlPath(), (isSupport, cLength) -> {
            try {
                XDLog.e(TAG, "是否支持断点续传：", isSupport);
                //断点位置
                long pos = 0;
                if (isSupport) pos = target.getBreakpoint();
                XDLog.e(TAG, "断点：pos = ", pos);
                callBack.onStart("run");
                OkHttpClient okHttpClient = XDHttpClient.getOkHttpClient();
                String rangeHeader = cLength == -1? ("bytes=" + pos + "-") :("bytes=" + pos + "-" + cLength);
                Request request = new Request.Builder()
                        .url(target.getUrlPath())
                        .addHeader("Range",rangeHeader)
                        .build();
                Call call = okHttpClient.newCall(request);
                Response response = call.execute();
                //文件总长度
                long contentLength = response.body().contentLength();
                int code = response.code();
                XDLog.e(TAG, "web响应码：", code);
                InputStream appData = response.body().byteStream();
                RandomAccessFile rw = new RandomAccessFile(target.getDestinationPath(), "rw");
                rw.seek(pos);
                byte[] temp = new byte[1024 * 5];
                int dataLen = 0;
                while ((dataLen = appData.read(temp)) > 0) {
                    pos += dataLen;
                    rw.write(temp, 0, dataLen);
                    callBack.onDownloading(pos, contentLength);
                    int current = (int) (pos * 100 / contentLength);
                    if (percent != current) {
                        percent = current;
                        XDLog.e(TAG, "onDownloading", pos, "/", contentLength, current, "%");
                    }
                }
                appData.close();
                callBack.onSuccess("", pos, contentLength);
            } catch (IOException e) {
                e.printStackTrace();
                callBack.onFail("IOException");
            }
        });


    }

}
