package com.xdroid.spring.util.java.tool;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串处理工具
 */
public class XDStrings {
    private static String regex = "[\\u4e00-\\u9fa5]";
    private static Pattern pattern =  Pattern.compile(regex);

    /**
     *   中文提取拼音
     * @param chineseStr
     * @return
     */
    public static String chineseToSpell(String chineseStr) {
        if (chineseStr == null || chineseStr.equals("") || chineseStr.equals(" ")) {
            return " null";
        }
        if (isChinese(chineseStr)) {
            HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
            defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
            defaultFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
            char c = chineseStr.charAt(0);

            String pinying = null;
            try {
                String[] strings = PinyinHelper.toHanyuPinyinStringArray(c, defaultFormat);
                pinying = String.valueOf(strings[0].charAt(0));

            } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
                badHanyuPinyinOutputFormatCombination.printStackTrace();
            }
            return pinying.toUpperCase();
        }
        return chineseStr;
    }

    private static boolean isChinese(String str) {
        return pattern.matcher(str).find();
    }


}
