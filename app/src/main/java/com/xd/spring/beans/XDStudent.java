package com.xd.spring.beans;

import com.alibaba.excel.annotation.ExcelProperty;

public class XDStudent {
    @ExcelProperty("f1")
    String f1;
    @ExcelProperty("f2")
    String f2;
    @ExcelProperty("f3")
    String f3;
    @ExcelProperty("f4")
    String f4;
    @ExcelProperty("f5")
    String f5;
    @ExcelProperty("f6")
    String f6;


    public XDStudent(String f1, String f2, String f3, String f4, String f5, String f6) {
        this.f1 = f1;
        this.f2 = f2;
        this.f3 = f3;
        this.f4 = f4;
        this.f5 = f5;
        this.f6 = f6;
    }


    public String getF1() {
        return f1;
    }

    public void setF1(String f1) {
        this.f1 = f1;
    }

    public String getF2() {
        return f2;
    }

    public void setF2(String f2) {
        this.f2 = f2;
    }

    public String getF3() {
        return f3;
    }

    public void setF3(String f3) {
        this.f3 = f3;
    }

    public String getF4() {
        return f4;
    }

    public void setF4(String f4) {
        this.f4 = f4;
    }

    public String getF5() {
        return f5;
    }

    public void setF5(String f5) {
        this.f5 = f5;
    }

    public String getF6() {
        return f6;
    }

    public void setF6(String f6) {
        this.f6 = f6;
    }
}
