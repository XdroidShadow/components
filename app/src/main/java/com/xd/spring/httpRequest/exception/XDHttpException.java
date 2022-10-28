package com.xd.spring.httpRequest.exception;

/**
 * 异常
 */
public class XDHttpException {

    private XDHttpErrType type;
    private String info;

    public XDHttpException(XDHttpErrType type, String info) {
        this.type = type;
        this.info = info;
    }
}