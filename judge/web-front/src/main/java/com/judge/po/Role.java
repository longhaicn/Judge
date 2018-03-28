package com.judge.po;

import java.util.Date;

public class Role {
    private Integer rId;

    private String rName;

    private Integer rWeight;

    private Date datetime;

    public Integer getrId() {
        return rId;
    }

    public void setrId(Integer rId) {
        this.rId = rId;
    }

    public String getrName() {
        return rName;
    }

    public void setrName(String rName) {
        this.rName = rName == null ? null : rName.trim();
    }

    public Integer getrWeight() {
        return rWeight;
    }

    public void setrWeight(Integer rWeight) {
        this.rWeight = rWeight;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }
}