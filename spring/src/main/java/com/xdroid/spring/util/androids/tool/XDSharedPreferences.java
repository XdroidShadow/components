
package com.xdroid.spring.util.androids.tool;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


/**
 * SharedPreference  工具类
 */
public class XDSharedPreferences {
    private static SharedPreferences sp = null;
    private volatile static XDSharedPreferences tshManager = null;
    private static Editor editor = null;
    /**
     * Preference文件名
     */
    private static final String FILE_SP_INFO = "xdroid_sp";

    private XDSharedPreferences(Context context) {
        sp = context.getSharedPreferences(FILE_SP_INFO, Context.MODE_PRIVATE);
        editor = sp.edit();
    }

    public static XDSharedPreferences getInstance(Context context) {
        if (tshManager == null) {
            synchronized (tshManager) {
                if (tshManager == null) {
                    tshManager = new XDSharedPreferences(context);
                }
            }
        }
        return tshManager;
    }

    public void putInt(String key, int value) {
        editor.putInt(key, value);
        editor.commit();
    }

    public int getInt(String key, int defaultValue) {
        return sp.getInt(key, defaultValue);
    }

    public void putLong(String key, Long value) {
        editor.putLong(key, value);
        editor.commit();
    }

    public long getLong(String key, int defaultValue) {
        return sp.getLong(key, defaultValue);
    }

    public void putString(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public String getString(String key, String defaultValue) {
        return sp.getString(key, defaultValue);
    }

    public void putFloat(String key, float value) {
        editor.putFloat(key, value);
        editor.commit();
    }

    public boolean isKeyExist(String key) {
        return sp.contains(key);
    }

    public float getFloat(String key, float defaultValue) {
        return sp.getFloat(key, defaultValue);
    }

    public void putBoolean(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return sp.getBoolean(key, defaultValue);
    }

    public void remove(String key) {
        editor.remove(key);
        editor.commit();
    }

    public void clear() {
        editor.clear().commit();
    }

}
