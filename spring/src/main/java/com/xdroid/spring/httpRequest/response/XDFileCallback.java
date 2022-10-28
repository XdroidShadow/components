package com.xdroid.spring.httpRequest.response;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.xdroid.spring.httpRequest.exception.XDHttpErrType;
import com.xdroid.spring.httpRequest.listener.XDDownloadListener;
import com.xdroid.spring.httpRequest.listener.XDJsonHandle;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 文件下载回调
 */
public class XDFileCallback implements Callback {
    private static final String TAG = "XDFileCallback";
    protected final int NETWORK_ERROR = -1; //  network  error
    protected final int IO_ERROR = -2; //  JSON  error
    protected final String EMPTY_MSG = "";
    /**
     * 将其它线程的数据转发到UI线程
     */
    private static final int PROGRESS_MESSAGE = 0x01;
    private final Handler mainHandler;
    private final XDDownloadListener mListener;
    private final String mFilePath;
    private int mProgress;

    public XDFileCallback(XDJsonHandle<File> handle) {
        this.mListener = (XDDownloadListener) handle.mListener;
        this.mFilePath = handle.mSource;
        this.mainHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case PROGRESS_MESSAGE:
                        mListener.onProgress((int) msg.obj);
                        break;
                }
            }
        };
    }

    @Override
    public void onFailure(@NotNull final Call call, final IOException ioexception) {
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                mListener.onFailure(XDHttpErrType.errNetWork);
            }
        });
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        Log.e(TAG, "onResponse");
        final File file = handleResponse(response);
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                if (file != null) {
                    mListener.onSuccess(file);
                } else {
                    mListener.onFailure(XDHttpErrType.errBlank);
                }
            }
        });
    }

    /**
     * 此时还在子线程中
     */
    private File handleResponse(Response response) {
        Log.e(TAG, "handleResponse");
        if (response == null) {
            return null;
        }

        InputStream inputStream = null;
        File file = null;
        FileOutputStream fos = null;
        byte[] buffer = new byte[2048];
        int length;
        int currentLength = 0;
        double sumLength;
        try {
            checkLocalFilePath(mFilePath);
            file = new File(mFilePath);
            fos = new FileOutputStream(file);
            inputStream = response.body().byteStream();
            sumLength = (double) response.body().contentLength();

            while ((length = inputStream.read(buffer)) != -1) {
                fos.write(buffer, 0, length);
                currentLength += length;
                //无法获取总长度的时候直接返回-1
                if (sumLength < 0) {
                    mProgress = -1;
                } else {
                    mProgress = (int) (currentLength / sumLength * 100);
                }
                mainHandler.obtainMessage(PROGRESS_MESSAGE, mProgress).sendToTarget();
            }
            fos.flush();
        } catch (Exception e) {
            file = null;
        } finally {
            try {
                if (fos != null) fos.close();
                if (inputStream != null) inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    private void checkLocalFilePath(String localFilePath) {
        File path = new File(localFilePath.substring(0,
                localFilePath.lastIndexOf("/") + 1));
        File file = new File(localFilePath);
        if (!path.exists()) {
            path.mkdirs();
        }
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}