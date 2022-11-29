package com.xdroid.spring.util.javas.tool;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.xdroid.spring.util.androids.tool.XDLog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * File 工具类
 */
public class XDFiles {


    /**
     * 保存图片
     * bitmap
     */
    public Boolean copyBitmapToFile(Bitmap bitmap, String destinationPath) {
        try {
            File f = new File(destinationPath);
            if (!f.exists()) {
                f.createNewFile();
            }
            FileOutputStream outputStream = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            Uri uri = Uri.fromFile(f);
            //发送广播通知更新图库，这样系统图库可以找到这张图片
            //context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 保存图片到SD卡
     */
    public void storeAssImageToFile(Context context, String assetsImg, String destinationPath) {
        Log.e("保存图片到SD卡 assetsImg:", assetsImg + "/storePath:" + destinationPath);
        try {
            File f = new File(destinationPath);
            if (!f.exists()) {
                f.createNewFile();
            }
            OutputStream fileOutput = new FileOutputStream(f);
            InputStream fileInput = context.getAssets().open(assetsImg);
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = fileInput.read(buffer)) != -1) {
                fileOutput.write(buffer, 0, len);
            }
            fileOutput.close();
            fileInput.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getSdCardPath(String... paths) {
        return getSdCardPath(null, paths);
    }

    public static String getSdCardPath(Context c, String... paths) {
        StringBuilder sb = new StringBuilder();
        if (c == null) {
            //  /storage/emulated/0
            sb.append(Environment.getExternalStorageDirectory().getPath());
        } else {
            //  /data/user/0/com.xd.spring/files
            sb.append(c.getFilesDir());
        }
        if (paths.length > 1) {
            for (int i = 0; i < paths.length - 1; i++) {
                sb.append(File.separator);
                sb.append(paths[i]);
            }
        }
        sb.append(File.separator);
        sb.append(paths[paths.length - 1]);
        return sb.toString();
    }

    public static String buildFile(String... paths) {
        return buildFile(null, paths);
    }

    /**
     * 获取app文件路径
     */
    public static String buildFile(Context c, String... paths) {
        if (paths.length == 0) throw new RuntimeException("目标路径不能为空！");

        StringBuilder sb = new StringBuilder();
        if (c == null) {
            //  /storage/emulated/0
            sb.append(Environment.getExternalStorageDirectory().getPath());
        } else {
            //  /data/user/0/com.xd.spring/files
            sb.append(c.getFilesDir());
        }
        if (paths.length > 1) {
            for (int i = 0; i < paths.length - 1; i++) {
                sb.append(File.separator);
                sb.append(paths[i]);
            }
            File dir = new File(sb.toString());
            dir.mkdirs();
        }

        sb.append(File.separator);
        sb.append(paths[paths.length - 1]);

        String destination = sb.toString();
        File file = new File(destination);
        boolean createRes = true;
        try {
            if (!file.exists()) {
                createRes = file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        XDLog.e("文件路径：", destination, " 创建结果：", createRes);
        return destination;
    }

    public static String buildDirs( String... paths){
        return buildDirs(null,paths);
    }
    public static String buildDirs(Context c, String... paths){
        if (paths.length == 0) throw new RuntimeException("目标路径不能为空！");

        StringBuilder sb = new StringBuilder();
        if (c == null) {
            //  /storage/emulated/0
            sb.append(Environment.getExternalStorageDirectory().getPath());
        } else {
            //  /data/user/0/com.xd.spring/files
            sb.append(c.getFilesDir());
        }
        if (paths.length > 1) {
            for (int i = 0; i < paths.length - 1; i++) {
                sb.append(File.separator);
                sb.append(paths[i]);
            }
        }
        sb.append(File.separator);
        sb.append(paths[paths.length - 1]);

        File dir = new File(sb.toString());
        boolean createRes =   dir.mkdirs();

        XDLog.e("文件夹路径：", sb.toString(), " 创建结果：", createRes);
        return sb.toString();
    }


    /**
     * 获取要下载文件的大小
     */
    public static final int HTTP_TIME_OUT = 30000;

    public static int getWebFileLength(String urlPath) {
        HttpURLConnection con = null;
        try {
            URL url = new URL(urlPath);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setReadTimeout(HTTP_TIME_OUT);
            con.setConnectTimeout(HTTP_TIME_OUT);
            //使用 gzip方式获取 解决部分链接无法获取到文件大小问题
            con.setRequestProperty("Accept-Encoding", "identity");
            int length = 0;
            if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                length = con.getContentLength();
            }
            con.disconnect();
            //返回文件长度
            return length;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    /**
     * 判断服务器是否支持断点续传
     */
    public static void isSupportBreakpointDownload(String path, CheckBreakpointCallBack callBack) {

        Observable.create((ObservableOnSubscribe<XDCheckBean>) emitter -> {
            try {
                URL url = new URL(path);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                int responseCode = urlConnection.getResponseCode();
                int contentLength = urlConnection.getContentLength();
                urlConnection.disconnect();
                if (responseCode == HttpURLConnection.HTTP_PARTIAL) {
                    emitter.onNext(new XDCheckBean(true, contentLength));
                } else {
                    emitter.onNext(new XDCheckBean(false, contentLength));
                }
            } catch (Exception e) {
                emitter.onNext(new XDCheckBean(false, -1));
            }
            emitter.onComplete();
        })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(res -> callBack.isSupport(res.isSupport(), res.getContentLength()));
    }

    public interface CheckBreakpointCallBack {

        void isSupport(Boolean isSupport, long contentLength);
    }

    private static class XDCheckBean {
        boolean isSupport;
        long contentLength;

        public XDCheckBean(boolean isSupport, long contentLength) {
            this.isSupport = isSupport;
            this.contentLength = contentLength;
        }

        public boolean isSupport() {
            return isSupport;
        }

        public long getContentLength() {
            return contentLength;
        }
    }

    /**
     * 保存inputstring到文件
     */
    public static void storeIntoFile(InputStream inputStream, String destinationPath, long breakPoint) {
        RandomAccessFile rw = null;
        try {
            long currentPOS = breakPoint;
            rw = new RandomAccessFile(destinationPath, "rw");
            rw.seek(currentPOS);

            byte[] temp = new byte[1024 * 5];
            int dataLen = 0;
            while ((dataLen = inputStream.read(temp)) > 0) {
                currentPOS += dataLen;
                rw.write(temp, 0, dataLen);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rw != null) rw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
