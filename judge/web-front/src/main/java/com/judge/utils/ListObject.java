package com.judge.utils;
import java.util.List;

/*
 * 建立JSON数组类ListObject
 */
public class ListObject extends AbstractJSON {

    private List<?> data;                       // 列表对象

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }

}