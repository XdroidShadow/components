package com.xdroid.spring.util.androids.tool;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * 项目中使用到的线程，需要共用处理
 */
public class XDThreads {


    public void test() {
        Executors.newFixedThreadPool(1, new ThreadFactory() {
            @Override
            public Thread newThread(Runnable runnable) {
                Thread t = new Thread(runnable, "xdroid_thread");
                return t;
            }
        });
    }


}
