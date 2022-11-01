package com.xdroid.spring.util.androids.tool;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class XDKeyboards {

    /**
     * 开启软键盘
     */
    public static void openSoftKeyboard(Context context, EditText et) {
        InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputManager != null) {
            inputManager.showSoftInput(et, 0);
        }
    }

    /**
     * 关闭软键盘
     */
    public static void closeSoftKeyboard(Activity context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null && context.getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(context.getCurrentFocus()
                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

}
