package com.xdroid.spring.util.androids.tool;

import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 *  安卓测量工具
 */
public class XDMeasures {



    /**
     * 重新计算listview的高度
     */
    public static void measureListView(ListView listView) {
        // 获取listview的adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) return;
        int col = 1; // 固定列宽，有多少列
        int totalHeight = 0;
        int subViewCount = listAdapter.getCount();
        for (int i = 0; i < subViewCount; i += col) {
            // 获取listview的每一个item
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            // 获取item的高度和
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight;
        ((ViewGroup.MarginLayoutParams) params).setMargins(0, 0, 0, 0);
        listView.setLayoutParams(params);
    }


    /**
     * 重新计算grid的高度
     */
    public static void measureGrid(GridView gridView, final int numCol) {
        ListAdapter listAdapter = gridView.getAdapter();
        if (listAdapter == null) return;
        int totalHeight = 0;
        int subviewCount = listAdapter.getCount();
        //grid一行只加一次高度，所以 i+=col
        for (int i = 0; i < subviewCount; i += numCol) {
            View listItem = listAdapter.getView(i, null, gridView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        params.height = totalHeight;
        ((ViewGroup.MarginLayoutParams) params).setMargins(0, 0, 0, 0);
        gridView.setLayoutParams(params);
    }


}
