package com.xdroid.spring.frames.okhttp.request;

public class XDLoginBean {
    private String userCode;
    private String  passWord;


    public XDLoginBean(String userCode, String passWord) {
        this.userCode = userCode;
        this.passWord = passWord;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
