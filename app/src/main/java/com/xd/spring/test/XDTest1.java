package com.xd.spring.test;

import java.util.concurrent.TimeUnit;

public class XDTest1 {


private static volatile XDTest1 mXDTest1 = null;

private XDTest1(){}

public static XDTest1 getInstance(){
    if (mXDTest1 == null){
        synchronized (XDTest1.class){
            if (mXDTest1 == null){
                mXDTest1 = new XDTest1();
            }
        }
    }
    return mXDTest1;
} 
}
