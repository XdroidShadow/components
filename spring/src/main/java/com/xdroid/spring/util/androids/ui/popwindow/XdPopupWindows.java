package com.xdroid.spring.util.androids.ui.popwindow;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import androidx.annotation.IntDef;

import com.xdroid.spring.R;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * popupWindow 工具
 */
public class XdPopupWindows {

    /**
     * 新建 popupWindow
     */
    public static PopupWindow createPopupWindow(final Context context) {
        final View view = LayoutInflater.from(context).inflate(R.layout.pop_bottom_sele_layout, null);
        //内容，高度，宽度
        final PopupWindow pw = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        //动画效果
        pw.setAnimationStyle(R.style.mkpark_navi_pop);
        //菜单背景色
        ColorDrawable dw = new ColorDrawable(0xffffffff);
        pw.setBackgroundDrawable(dw);
        return pw;
    }


    /**
     * 控制pop
     */
    public static void popAction(View parentView, @XdGravity int gravity, PopupWindow popupWindow) {
        if (popupWindow != null && !popupWindow.isShowing()) {
            //设置背景半透明
//            MKUIManager.backgroundAlpha(context, 0.5f);
            popupWindow.showAtLocation(parentView, gravity, 0, 0);
            return;
        }
        //退场
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
    }
    public static final int TOP = Gravity.TOP;
    public static final int CENTER = Gravity.CENTER;
    public static final int BOTTOM = Gravity.BOTTOM;

    @IntDef({TOP, CENTER, BOTTOM})
    @Target({ElementType.PARAMETER})
    @interface XdGravity {

    }


}
