package com.xdroid.spring.util.android.ui.customview;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.widget.Scroller;

import androidx.annotation.Nullable;

import com.xdroid.spring.R;


/**
 * 自定义view
 * 改变view位置的几种方法
 */
public class TEST_ViewTouch extends View {
    private static final String TAG = "XDViewTouch";
    private float lastX;
    private float lastY;
    private Scroller scroller;


    public TEST_ViewTouch(Context context) {
        super(context);
    }

    public TEST_ViewTouch(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        scroller = new Scroller(context, new OvershootInterpolator());
    }

    public TEST_ViewTouch(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TEST_ViewTouch(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e(TAG, "onTouchEvent: " + event.getAction());
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;
                break;

            case MotionEvent.ACTION_MOVE:
                int offsetX = (int) (x - lastX);
                int offsetY = (int) (y - lastY);

                //让view移动
                //1使用layout()方法
//                byLayoutFun(offsetX,offsetY);

//                byLayoutFun2(offsetX,offsetY);

                //3使用 getLayoutParams
//                byLayoutParams(offsetX,offsetY);

                //4 scrollBy
//                byScroll(offsetX, offsetY);

                //使用scroller
                break;

        }
        return true;
    }

    /**
     * 1 使用layout()方法
     */
    private void byLayoutFun(int offsetX, int offsetY) {
        layout(getLeft() + offsetX, getTop() + offsetY,
                getRight() + offsetX, getBottom() + offsetY);
    }

    /**
     * 2使用方法设置
     */
    private void byLayoutFun2(int offsetX, int offsetY) {
        offsetLeftAndRight(offsetX);
        offsetTopAndBottom(offsetY);
    }

    /**
     * 3使用 getLayoutParams
     */
    private void byLayoutParams(int offsetX, int offsetY) {
        //使用ViewGroup.MarginLayoutParams
        ViewGroup.MarginLayoutParams layoutParams1 = (ViewGroup.MarginLayoutParams) getLayoutParams();
        //使用view所在布局的类型，当前是LinearLayout
//        LinearLayout.LayoutParams layoutParams1 = (LinearLayout.LayoutParams) getLayoutParams();
        layoutParams1.leftMargin = getLeft() + offsetX;
        layoutParams1.topMargin = getTop() + offsetY;
        setLayoutParams(layoutParams1);
    }


    /**
     * 4动画
     */
    private void anim(Context context) {
        Log.e(TAG, "anim alpha");
        TEST_ViewTouch viewTouch = null;

        //view动画
        viewTouch.startAnimation(AnimationUtils.loadAnimation(context, R.anim.translate));

        //属性动画
        ObjectAnimator.ofFloat(viewTouch, "alpha", 0.0F, 1.0F).setDuration(200).start();


        //值动画
        ValueAnimator va = ValueAnimator.ofFloat(0.0F, 1.0F);
        va.setTarget(viewTouch);
        va.setDuration(5000);
        va.start();
        va.addUpdateListener(animation -> {
            viewTouch.setAlpha((Float) animation.getAnimatedValue());
            Log.e(TAG, "value ->" + animation.getAnimatedValue());
        });

        //组合动画
        ObjectAnimator y = ObjectAnimator.ofFloat(viewTouch, "y", 0F, 200).setDuration(1000);
        y.setInterpolator(new OvershootInterpolator());
        ObjectAnimator rotation = ObjectAnimator.ofFloat(viewTouch, "rotation", 0F, 360 * 4F).setDuration(5000);

        AnimatorSet animators = new AnimatorSet();
        animators.play(y).before(rotation);
        animators.start();

    }


    private void byScroll(int offsetX, int offsetY) {
        Log.e(TAG, "byScroll: " + offsetX + " / " + offsetY);
        ((View) getParent()).scrollBy(-offsetX, -offsetY);
    }


    /*
     *   6、使用Scroller实现移动
     *   scrollTo 完成后会回调 computeScroll
     */
    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset()) {
            Log.e(TAG, "computeScroll: " + scroller.getCurrX() + " / " + scroller.getCurrY());
            ((View) getParent()).scrollTo(scroller.getCurrX(), scroller.getCurrY());
            invalidate();
        }
    }

    //使用Scroller实现移动
    public void smoothScrollToX(int destinationX, int destinationY) {
        int startX = getScrollX();
        int startY = getScrollY();
        int delta = destinationX - startX;
        Log.e(TAG, "scrollToX: " + startX + " / " + delta);
        scroller.startScroll(startX, startY, destinationX - startX, destinationY - startY);
        invalidate();
    }


}
