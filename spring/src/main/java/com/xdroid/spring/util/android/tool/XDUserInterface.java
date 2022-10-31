//package com.xdroid.spring.example.android.tool;
//
//import android.app.Activity;
//import android.content.Context;
//import android.util.DisplayMetrics;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.WindowManager;
//import android.widget.GridView;
//import android.widget.ListAdapter;
//import android.widget.ListView;
//
//import com.tsh.xdroid.tools.tool.android.bean.TSHDeviceBounds;
//import com.tsh.xdroid.tools.tool.android.interfaces.IXDUserInterface;
//
//public enum XDUserInterface {
//
//    /**
//     * 重新计算listview的高度
//     */
//    @Override
//    public void modifyListView(ListView listView) {
//        // 获取listview的adapter
//        ListAdapter listAdapter = listView.getAdapter();
//        if (listAdapter == null) return;
//        // 固定列宽，有多少列
//        int col = 1;
//        int totalHeight = 0;
//        // i每次加4，相当于listAdapter.getCount()小于等于4时 循环一次，计算一次item的高度，
//        // listAdapter.getCount()小于等于8时计算两次高度相加
//        for (int i = 0; i < listAdapter.getCount(); i += col) {
//            // 获取listview的每一个item
//            View listItem = listAdapter.getView(i, null, listView);
//            listItem.measure(0, 0);
//            // 获取item的高度和
//            totalHeight += listItem.getMeasuredHeight();
//        }
//
//        // 获取listview的布局参数
//        ViewGroup.LayoutParams params = listView.getLayoutParams();
//        // 设置高度
//        params.height = totalHeight;
//        // 设置margin
//        ((ViewGroup.MarginLayoutParams) params).setMargins(0, 0, 0, 0);
//        // 设置参数
//        listView.setLayoutParams(params);
//    }
//
//
//    /**
//     * 重新计算grid的高度
//     */
//    @Override
//    public void modifyGrid(GridView gridView, int numCol) {
//        // 获取listview的adapter
//        ListAdapter listAdapter = gridView.getAdapter();
//        if (listAdapter == null) {
//            return;
//        }
//        // 固定列宽，有多少列
//        int col = numCol;
//        int totalHeight = 0;
//        // i每次加4，相当于listAdapter.getCount()小于等于4时 循环一次，计算一次item的高度，
//        // listAdapter.getCount()小于等于8时计算两次高度相加
//        for (int i = 0; i < listAdapter.getCount(); i += col) {
//            // 获取listview的每一个item
//            View listItem = listAdapter.getView(i, null, gridView);
//            listItem.measure(0, 0);
//            // 获取item的高度和
//            totalHeight += listItem.getMeasuredHeight();
//        }
//
//        // 获取listview的布局参数
//        ViewGroup.LayoutParams params = gridView.getLayoutParams();
//        // 设置高度
//        params.height = totalHeight;
//        // 设置margin
//        ((ViewGroup.MarginLayoutParams) params).setMargins(0, 0, 0, 0);
//        // 设置参数
//        gridView.setLayoutParams(params);
//    }
//
//
//    /**
//     * dp转化为px
//     */
//    @Override
//    public int dp2Px(Context context, float dpValue) {
//        final float scale = context.getResources().getDisplayMetrics().density;
//        return (int) (dpValue * scale + 0.5f);
//    }
//
//    //px转化为dp
//    @Override
//    public int px2dp(Context context, float pxValue) {
//        final float scale = context.getResources().getDisplayMetrics().density;
//        return (int) (pxValue / scale + 0.5f);
//    }
//
//
//    /**
//     * 屏幕宽度
//     */
//    @Override
//    public TSHDeviceBounds device(Context context) {
//        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//        DisplayMetrics dm = new DisplayMetrics();
//        wm.getDefaultDisplay().getMetrics(dm);
//        // 屏幕宽度（像素）
//        int width = dm.widthPixels;
//        // 屏幕高度（像素）
//        int height = dm.heightPixels;
//        // 屏幕密度（0.75 / 1.0 / 1.5）
//        float density = dm.density;
//        // 屏幕密度dpi（120 / 160 / 240）
//        int densityDpi = dm.densityDpi;
//        // 屏幕宽度算法:屏幕宽度（像素）/屏幕密度
//        int screenWidth = (int) (width / density);  // 屏幕宽度(dp)
//        int screenHeight = (int) (height / density);// 屏幕高度(dp)
//        return new TSHDeviceBounds(screenWidth, screenHeight);
//    }
//
//
//    /**
//     * 设置屏幕的背景透明度
//     */
//    @Override
//    public void backgroundAlpha(Activity activity, float bgAlpha) {
//        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
//        lp.alpha = bgAlpha; //0.0-1.0
//        activity.getWindow().setAttributes(lp);
//    }
//
//
//}
