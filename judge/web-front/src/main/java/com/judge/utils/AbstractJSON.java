package com.judge.utils;

/*
 * 建立AbstractJSON(JSON数据的响应基类 )
 */
public class AbstractJSON {
    private String code;                            //响应状态码
    private String desc;                             //响应状态描述
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setContent(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public void setStatusObject(StatusObject statusObject) {
        this.code = statusObject.getCode();
        this.desc = statusObject.getDesc();
    }
}