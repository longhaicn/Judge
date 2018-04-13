package com.judge.po;

import java.util.Date;

public class Project {
    private Integer pId;

    private String pName;

    private String pDescription;

    private Integer pUserId;

    private String pUserName;

    private Double pUserPenalty;

    private Integer pClass;

    private Date pStart;

    private Date pEnd;

    private Long pAward;

    private Date datetime;

    private Integer major;

    private String majorName;

    public Integer getMajor() {
        return major;
    }

    public void setMajor(Integer major) {
        this.major = major;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

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

    public Double getpUserPenalty() {
        return pUserPenalty;
    }

    public void setpUserPenalty(Double pUserPenalty) {
        this.pUserPenalty = pUserPenalty;
    }
}