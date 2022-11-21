package com.xd.spring.test.rxjava.events;

public class XDEvent2 {


    private String info;

    public XDEvent2(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "XDEvent1{" +
                "info='" + info + '\'' +
                '}';
    }
}
