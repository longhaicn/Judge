package com.judge.po;

import java.util.Date;

public class Project {
    private Integer pId;

    private String pName;

    private String pDescription;

    private Integer pUserId;

    private String pUserName;

    private Integer pClass;

    private Date pStart;

    private Date pEnd;

    private Long pAward;

    private Date datetime;

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName == null ? null : pName.trim();
    }

    public String getpDescription() {
        return pDescription;
    }

    public void setpDescription(String pDescription) {
        this.pDescription = pDescription == null ? null : pDescription.trim();
    }

    public Integer getpUserId() {
        return pUserId;
    }

    public void setpUserId(Integer pUserId) {
        this.pUserId = pUserId;
    }

    public String getpUserName() {
        return pUserName;
    }

    public void setpUserName(String pUserName) {
        this.pUserName = pUserName == null ? null : pUserName.trim();
    }

    public Integer getpClass() {
        return pClass;
    }

    public void setpClass(Integer pClass) {
        this.pClass = pClass;
    }

    public Date getpStart() {
        return pStart;
    }

    public void setpStart(Date pStart) {
        this.pStart = pStart;
    }

    public Date getpEnd() {
        return pEnd;
    }

    public void setpEnd(Date pEnd) {
        this.pEnd = pEnd;
    }

    public Long getpAward() {
        return pAward;
    }

    public void setpAward(Long pAward) {
        this.pAward = pAward;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }
}