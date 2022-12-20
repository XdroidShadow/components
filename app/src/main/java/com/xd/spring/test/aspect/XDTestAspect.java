package com.xd.spring.test.aspect;


import android.os.AsyncTask;

import com.xdroid.spring.util.androids.tool.XDLog;
import com.xdroid.spring.util.javas.tool.XDUnits;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class XDTestAspect {
    private static final String TAG = "XDTestAspect";

    /**
     * call 代表前后都添加
     * .** 所有方法
     * (..) 参数可有可无
     *
     * @param point 可以拿到被处理方法的信息
     */
    @Around("call(* com.xd.spring.test.IndexActivity.**(..))")
    public void countTime(ProceedingJoinPoint point) {
        long start = System.currentTimeMillis();
        Signature signature = point.getSignature();
        try {
            point.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        long end = System.currentTimeMillis();
        XDLog.e(TAG, signature.toString(), " ", (end - start));
    }

    public void test() {
    }
}
