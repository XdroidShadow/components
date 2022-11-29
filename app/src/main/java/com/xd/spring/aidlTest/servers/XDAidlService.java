package com.xd.spring.aidlTest.servers;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import androidx.annotation.Nullable;

import com.xd.spring.IXdroidAidlInterface;
import com.xdroid.annotation.XDTip;
import com.xdroid.annotation.XDTodo;
import com.xdroid.spring.util.androids.tool.XDLog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *   服务端，通过service与AIDL进行绑定
 */
public class XDAidlService extends Service {
    private static final String TAG = "XDAidlService服务";
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        XDLog.e(TAG,"服务已连接");
        return binder;
    }

    /**
     *   将AIDL与service绑定
     */
    private IBinder binder = new IXdroidAidlInterface.Stub(){

        @Override
        public int add(int a, int b) throws RemoteException {
            XDLog.e(TAG,a," | ",b);
            return a + b;
        }

        @Override
        public int count(List<String> data) throws RemoteException {
            return data.size();
        }

        @Override
        public List<String> getListData() throws RemoteException {
            return new ArrayList<>(Arrays.asList("A","B","C"));
        }
    };


}
