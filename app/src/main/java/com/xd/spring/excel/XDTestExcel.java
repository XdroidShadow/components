package com.xd.spring.excel;

import android.util.Log;

import com.alibaba.excel.EasyExcel;
import com.xd.spring.beans.XDStudent;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class XDTestExcel {

    private static final String TAG = "XDTestExcel";
    public void writeExcel() {

        Log.e(TAG, "add:target");
        try {
            String dirPath = "/sdcard/spring/";
            File dir = new File(dirPath);
            dir.mkdirs();
            String filePath = dirPath + "/" + new SimpleDateFormat("yyyy-MM-dd_HHmmss").format(new Date()) + ".xlsx";
            File target = new File(filePath);
            target.createNewFile();
            Log.e(TAG, "add:target =  " + target);
            EasyExcel.write(filePath, XDStudent.class)
                    .sheet("测试")
                    .doWrite(Arrays.asList(new XDStudent("1", "2", "3", "4", "5", "6"), new XDStudent("01", "02", "03", "04", "05", "06")));

//            EasyExcel.write(fileName, DemoData.class)
//                    .sheet("模板")
//                    .doWrite(() -> {
//                        // 分页查询数据
//                        return data();
//                    });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
