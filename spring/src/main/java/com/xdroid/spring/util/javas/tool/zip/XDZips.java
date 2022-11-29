package com.xdroid.spring.util.javas.tool.zip;

import com.xdroid.spring.util.androids.tool.XDLog;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;


/**
 * 压缩、解压缩
 */
public class XDZips {
    private static final String TAG = "XDZips";
    private static boolean isOriginalFormat = true;

    /**
     * @param sourceName
     * @param desDirName
     * @param structure  是否按照原格式
     */
    public static void zip(String sourceName, String desDirName, boolean structure) throws FileNotFoundException {
        isOriginalFormat = structure;
        zip(sourceName, desDirName);
    }

    /**
     * @param sourceName 需要压缩的文件
     * @param desDirName 目标文件夹
     *                   java压缩不可预料的压缩文件末端 -- 流未关闭会出现这个问题
     */
    public static void zip(String sourceName, String desDirName) throws FileNotFoundException {
        File source = new File(sourceName);
        File destinationDir = new File(desDirName);
        if (!destinationDir.exists()) destinationDir.mkdirs();

        FileOutputStream fos = new FileOutputStream(new File(desDirName + File.separator + source.getName() + ".zip"));
        ZipOutputStream zipOutputStream = new ZipOutputStream(fos);

        try {
            if (source.isFile()) {
                compressFile(source, "", zipOutputStream);
            } else {
                compressDir(source, getFileName(source), zipOutputStream);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                zipOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 压缩文件夹
     */
    private static void compressDir(File source, String dir, ZipOutputStream zipOutputStream) throws IOException {
//        XDLog.e("目标：", dir, " - ", source.getName());
        File[] files = source.listFiles();
        if (files.length == 0) {
            zipOutputStream.putNextEntry(new ZipEntry(dir + File.separator));
            return;
        }
        for (File f : files) {
            if (f.isFile()) {
                compressFile(f, dir, zipOutputStream);
            } else {
                if (isOriginalFormat) {
                    //按照原始格式压缩
                    compressDir(f, dir + File.separator + getFileName(f), zipOutputStream);
                } else {
                    //输出在一个文件夹
                    compressDir(f, "", zipOutputStream);
                }
            }
        }
    }

    /**
     * 压缩文件
     */
    private static void compressFile(File source, String dir, ZipOutputStream zipOutputStream) throws IOException {
//        XDLog.e("目标路径：" + dir + File.separator + getFileName(source));
        XDLog.e("目标路径：" + dir + File.separator + source.getName());
        zipOutputStream.putNextEntry(new ZipEntry(dir + File.separator + source.getName()));
        InputStream inputStream = new FileInputStream(source);
        byte[] data = new byte[1024 * 6];
        while (inputStream.read(data) > 0) {
            zipOutputStream.write(data);
            zipOutputStream.flush();
        }
        inputStream.close();
    }

    /**
     * 解压
     * 注意：不能解压中文命名的文件，这个怎么解决？
     * 还需要捕获一下  IllegalArgumentException
     */
    public static void unZip(String name, String destinationDir) throws IOException,IllegalArgumentException {
        ZipInputStream zipInputStream = new ZipInputStream(new BufferedInputStream(new FileInputStream(new File(name))));
        byte[] data = new byte[1024 * 5];
        int len = 0;
        ZipEntry zipEntry;
        while ((zipEntry = zipInputStream.getNextEntry()) != null) {
            XDLog.e(TAG, "解压缩文件：", zipEntry.getName());

            if (zipEntry.isDirectory()) {
                unZipDir(zipInputStream, zipEntry, destinationDir);
            } else {
                unZipFile(zipInputStream, zipEntry, destinationDir);
            }
        }
    }

    /**
     * 解压文件夹
     */
    private static void unZipDir(ZipInputStream zipInputStream, ZipEntry zipEntry, String path) throws IOException {
        File destinationDirFile = new File(path + File.separator + zipEntry.getName());
        XDLog.e(TAG, "解压文件夹:", destinationDirFile.getName());
        if (!destinationDirFile.exists()) destinationDirFile.mkdirs();
    }

    /**
     * 解压文件
     */
    private static void unZipFile(ZipInputStream zipInputStream, ZipEntry zipEntry, String path) throws IOException {
        File destinationDirFile = new File(path);
        if (!destinationDirFile.exists()) destinationDirFile.mkdirs();
        File file = new File(path + File.separator + zipEntry.getName());
        XDLog.e(TAG, "解压文件:", file.getName());
        FileOutputStream fos = new FileOutputStream(file);
        BufferedOutputStream bos = new BufferedOutputStream(fos);

        int len = 0;
        byte[] data = new byte[1024 * 5];
        while ((len = zipInputStream.read(data)) > 0) {
            bos.write(data, 0, len);
            bos.flush();
        }
        bos.close();
    }

    private static String getFileName(File file) {
        String name = file.getName();
        if (name.contains(".")) {
            return name.substring(0, name.indexOf("."));
        } else return name;
    }


}
