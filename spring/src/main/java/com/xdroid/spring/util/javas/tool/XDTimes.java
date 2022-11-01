package com.xdroid.spring.util.javas.tool;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

/**
 * 时间工具
 */
public class XDTimes {
    public static final DateFormat fullDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 计算时间间隔
     */
    public static Long timeInterval(String start, String end) {
        try {
            return Objects.requireNonNull(fullDateFormat.parse(end)).getTime()
                    - Objects.requireNonNull(fullDateFormat.parse(start)).getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1L;
    }

    /**
     * 计算时间间隔
     * 转换为时间单位
     */
    public static String timeIntervalString(String start, String end) {
        return XDUnits.formatTime(timeInterval(start, end));
    }

    /**
     *   当前时间字符串
     */
    public static String currentTimeString() {
        return fullDateFormat.format(Calendar.getInstance().getTime());
    }



}
