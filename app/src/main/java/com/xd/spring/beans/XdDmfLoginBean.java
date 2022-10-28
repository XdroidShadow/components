/**
  * Copyright 2022 json.cn 
  */
package com.xd.spring.beans;

/**
 * Auto-generated: 2022-10-27 11:55:57
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
public class XdDmfLoginBean {

    private String emsg;
    private String ecode;
    public void setEmsg(String emsg) {
         this.emsg = emsg;
     }
     public String getEmsg() {
         return emsg;
     }

    public void setEcode(String ecode) {
         this.ecode = ecode;
     }
     public String getEcode() {
         return ecode;
     }


    @Override
    public String toString() {
        return "XdDmfLoginBean{" +
                "emsg='" + emsg + '\'' +
                ", ecode='" + ecode + '\'' +
                '}';
    }
}