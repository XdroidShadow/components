package com.xdroid.spring.util.androids.tool.handler;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;


import com.xdroid.spring.util.androids.tool.XDLog;
import com.xdroid.spring.util.javas.tool.XDStrings;

import org.json.JSONObject;

public class XDHandler extends Handler {

    private long mStartTime = System.currentTimeMillis();

    public XDHandler() {
        super(Looper.myLooper(), null);
    }

    public XDHandler(Callback callback) {
        super(Looper.myLooper(), callback);
    }

    public XDHandler(Looper looper, Callback callback) {
        super(looper, callback);
    }

    public XDHandler(Looper looper) {
        super(looper);
    }

    @Override
    public boolean sendMessageAtTime(Message msg, long uptimeMillis) {
        boolean send = super.sendMessageAtTime(msg, uptimeMillis);
        if (send) {
            GetDetailHandlerHelper.getMsgDetail().put(msg, Log.getStackTraceString(new Throwable()).replace("java.lang.Throwable", ""));
        }
        return send;
    }

    @Override
    public void dispatchMessage(Message msg) {
        mStartTime = System.currentTimeMillis();
        super.dispatchMessage(msg);

        if (GetDetailHandlerHelper.getMsgDetail().containsKey(msg)
                && Looper.myLooper() == Looper.getMainLooper()) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("Msg_Cost", System.currentTimeMillis() - mStartTime);
                jsonObject.put("MsgTrace", XDStrings.unitMultiArgs(msg.getTarget(), "", GetDetailHandlerHelper.getMsgDetail().get(msg)));
                XDLog.i("MsgDetail ", jsonObject.toString());
                GetDetailHandlerHelper.getMsgDetail().remove(msg);
            } catch (Exception e) {
            }
        }
    }

}
