package com.xdroid.spring.util.java.tool;

import android.util.Base64;

public class XDCode {

    /**
     * base64加码
     */
    public static String Base64Encode(String args) {
        return Base64.encodeToString(args.getBytes(), Base64.DEFAULT);
    }

    /**
     * base64解码
     */
    public static String Base64Decode(String args) {
        return new String(Base64.decode(args.getBytes(), Base64.DEFAULT));
    }


}
