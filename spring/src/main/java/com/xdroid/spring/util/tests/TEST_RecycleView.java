package com.xdroid.spring.util.tests;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.IntDef;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.xdroid.spring.R;


import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.util.Arrays;
import java.util.List;

public class TEST_RecycleView extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycleview);
        testRecycleView();

    }

    public void testRecycleView() {
        XDAdapter adapter = new XDAdapter(Arrays.asList("1", "2", "3", "4", "5", "6", "6", "6", "6", "6", "6", "6", "6", "6", "6"), this);

        adapter.setClickListener(new XDClickListener() {
            @Override
            public void onClick(View v, int position) {
                Log.e(TAG, "onClick: " + position);
            }

            @Override
            public void onLongClick(View v, int position) {
                Log.e(TAG, "onLongClick: " + position);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.xdRecyclerView);
        //1、ListView 普通水平、横向布局
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
//        recyclerView.setLayoutManager(linearLayoutManager);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //设置分隔线
//        recyclerView.addItemDecoration(new XDDivider(this, XDDivider.HORIZONTAL));
//        recyclerView.setAdapter(adapter);

        //2、GridView
        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
//        recyclerView.addItemDecoration(new XDDivider(this, XDDivider.HORIZONTAL));
        recyclerView.setAdapter(adapter);

        //3、瀑布流
        //在Grid基础上不需要设置  recyclerView.addItemDecoration


    }

    /**
     * 适配器
     */
    public static class XDAdapter extends RecyclerView.Adapter<XDHolder> implements View.OnClickListener, View.OnLongClickListener {

        private List<String> data;
        private Context context;
        private XDClickListener listener;

        public XDAdapter(List<String> data, Context context) {
            this.data = data;
            this.context = context;
        }

        public void setClickListener(XDClickListener action) {
            listener = action;
        }

        @Override
        public XDHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_horizontal, parent, false);
            //点击监听
            view.setOnClickListener(this::onClick);
            view.setOnLongClickListener(this::onLongClick);
            XDHolder holder = new XDHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(XDHolder holder, int position) {
            //将position保存在view上，用于处理点击事件的回调
            holder.itemView.setTag(position);
            //模拟瀑布流，修改view高度
            ViewGroup.LayoutParams lps = holder.tv.getLayoutParams();
            lps.height = (int) (100 + Math.random() * 300);
            holder.tv.setLayoutParams(lps);

            holder.tv.setText(data.get(position));
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.onClick(v, (int) v.getTag());
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (listener != null) {
                listener.onLongClick(v, (int) v.getTag());
            }
            return true;
        }
    }


    /**
     * view复用
     */
    public static class XDHolder extends RecyclerView.ViewHolder {
        private TextView tv;

        public XDHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.xdItemTV);
        }
    }


    /**
     * 点击事件接口
     */
    public interface XDClickListener {
        void onClick(View v, int position);

        void onLongClick(View v, int position);
    }

    /**
     * 分割线
     */
    public static class XDDivider extends RecyclerView.ItemDecoration {
        private static final int[] attrs = new int[]{android.R.attr.listDivider};
        private Drawable divider;
        public static final int HORIZONTAL = LinearLayoutManager.HORIZONTAL;
        public static final int VERTICAL = LinearLayoutManager.VERTICAL;
        private int orientation;

        public XDDivider(Context context, @XDOrientation int orientation) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs);
            divider = typedArray.getDrawable(0);
            this.orientation = orientation;
        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDraw(c, parent, state);
            if (orientation == HORIZONTAL) {
                drawHorizontal(c, parent);
            } else if (orientation == VERTICAL) {
                drawVertical(c, parent);
            }
        }

        //竖向分隔线
        private void drawVertical(Canvas c, RecyclerView parent) {
            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();
            final int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                final View child = parent.getChildAt(i);
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
                int top = child.getBottom() + layoutParams.bottomMargin;
                int bottom = top + divider.getIntrinsicHeight();
                divider.setBounds(new Rect(left, top, right, bottom));
                divider.draw(c);
            }
        }

        //横向分隔线
        private void drawHorizontal(Canvas c, RecyclerView parent) {
            int top = parent.getPaddingTop();
            int bottom = parent.getHeight() - parent.getPaddingBottom();
            final int count = parent.getChildCount();
            for (int i = 0; i < count; i++) {
                final View child = parent.getChildAt(i);
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
                int left = child.getRight() + layoutParams.rightMargin;
                int right = left + divider.getIntrinsicWidth();
                divider.setBounds(left, top, right, bottom);
                divider.draw(c);
            }
        }

        //于设置item的padding属性  给分割线腾出空间
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            if (orientation == HORIZONTAL) {
                outRect.set(0, 0, divider.getIntrinsicWidth(), 0);
            } else if (orientation == VERTICAL) {
                outRect.set(0, 0, 0, divider.getIntrinsicHeight());
            }
        }
    }

    @IntDef({XDDivider.HORIZONTAL, XDDivider.VERTICAL})
    @Target(ElementType.PARAMETER)
    @interface XDOrientation {
    }

}