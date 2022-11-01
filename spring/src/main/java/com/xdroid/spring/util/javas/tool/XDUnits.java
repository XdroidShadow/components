package com.xdroid.spring.util.javas.tool;

import java.math.BigDecimal;

/**
 * 单位转换
 */
public class XDUnits {

    /**
     * 格式化单位
     */
    public static String formatFileSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return "0K";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
                + "TB";
    }

    /**
     * 将毫秒转为多少 分钟+秒
     */
    public static String formatTime(long timeMillis) {
        long totalSeconds = timeMillis / 1000;
        int oneHour = 60 * 60;
        if (totalSeconds < 60) {//1分钟内
            return totalSeconds + "秒";
        } else if (totalSeconds < oneHour) {//一小时内
            long secondsPart = totalSeconds % 60;
            long minutePart = totalSeconds / 60;
            return secondsPart + "分" + minutePart + "秒";
        } else {
            long secondsPart = totalSeconds % 60;
            long minutePart = (totalSeconds % oneHour) / 60;
            long hourPart = totalSeconds / oneHour;
            return hourPart+"小时"+secondsPart + "分" + minutePart + "秒";
        }
    }


}
