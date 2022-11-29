package com.xd.spring.aidlTest;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.xd.spring.IXdroidAidlInterface;
import com.xdroid.annotation.XDTodo;

public class XDTestAidl extends Activity {

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

            IXdroidAidlInterface iXdroidAidlInterface = IXdroidAidlInterface.Stub.asInterface(iBinder);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    public static void main(String[] args) {

    }

    @XDTodo(value = "自定义接口 onBind",time = "2022年11月25日16:03:38")
    public void test() {


        //获取服务
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("",""));
        bindService(intent,connection, Context.BIND_AUTO_CREATE);
    }
}
