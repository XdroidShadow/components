package com.xdroid.spring.util.javas.tool;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *   File 工具类
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

}
