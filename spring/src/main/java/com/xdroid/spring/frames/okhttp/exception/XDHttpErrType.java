package com.xdroid.spring.frames.okhttp.exception;

public enum XDHttpErrType {
    errNetWork("网络访问失败", -1),
    errJson("数据格式异常", -2),
    errBean("模型解析异常", -3),
    errBlank("响应数据为空", -4),
    errOther("其他异常", -5);


    String errInfo;
    int errCode;

    XDHttpErrType(String errInfo, int errCode) {
        this.errInfo = errInfo;
        this.errCode = errCode;
    }

}
