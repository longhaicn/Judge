package com.judge.po;

import java.util.Date;

public class Infect {
    private Integer iId;

    private Integer iAffairId;

    private Integer iUserId;

    private Integer iScored;

    private Date datetime;

    public Integer getiId() {
        return iId;
    }

    public void setiId(Integer iId) {
        this.iId = iId;
    }

    public Integer getiAffairId() {
        return iAffairId;
    }

    public void setiAffairId(Integer iAffairId) {
        this.iAffairId = iAffairId;
    }

    public Integer getiUserId() {
        return iUserId;
    }

    public void setiUserId(Integer iUserId) {
        this.iUserId = iUserId;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public Integer getiScored() {
        return iScored;
    }

    public void setiScored(Integer iScored) {
        this.iScored = iScored;
    }
}