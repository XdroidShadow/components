package com.xdroid.spring.util.javas.tool.zip.test;

import com.xdroid.spring.util.javas.tool.XDFiles;
import com.xdroid.spring.util.javas.tool.XDTimes;
import com.xdroid.spring.util.javas.tool.zip.XDZips;

import java.io.IOException;

public class XDZipsTest {

    //解压测试目录
    private static String sourceFile_unzip = XDFiles.getSdCardPath("test", "xdZip5.zip");
    private static String destination_unzip = XDFiles.buildDirs("test", "xdZip"+ XDTimes.currentTimeStringNoSp());

    //压缩测试目录
    private static String sourceFile_zip = XDFiles.getSdCardPath("test", "xdZip7");
    private static String destination_zip = XDFiles.buildDirs("test");


    //解压测试目录
    private static String sourceFile_unzip_file = XDFiles.getSdCardPath("test", "5.png.zip");
    private static String destination_unzip_file = XDFiles.buildDirs("test", "5png_"+ XDTimes.currentTimeStringNoSp());

    //压缩测试目录
    private static String sourceFile_zip_file = XDFiles.getSdCardPath("test", "5.png");
    private static String destination_zip_file = XDFiles.buildDirs("test");

    /**
     *   zip 测试
     */
    public static void testZip_dir() {
        try {
            //目录
            XDZips.zip(sourceFile_zip, destination_zip);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *   zip 测试
     */
    public static void testZip_file() {
        try {
            //文件
            XDZips.zip(sourceFile_zip, destination_zip);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *   unZip 测试
     */
    public static void testUnZip_dir() {
        try {
            //目录
//            XDZips.unZip(sourceFile_unzip, destination_unzip);
            //文件
            XDZips.unZip(sourceFile_unzip_file, destination_unzip_file);
        } catch (IOException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    /**
     *   unZip 测试
     */
    public static void testUnZip_file() {
        try {
            //文件
            XDZips.unZip(sourceFile_unzip_file, destination_unzip_file);
        } catch (IOException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

}
