package com.xdroid.spring.util.javas.tool.download;

/**
 *   下载实现
 */
public class XDDownloadTask {
    public XDDownloadBean target;
    public XDSingleCallBack callBack;
    public int percent = 0;


    public XDDownloadTask(XDDownloadBean target, XDSingleCallBack callBack) {
        this.target = target;
        this.callBack = callBack;
    }


}
