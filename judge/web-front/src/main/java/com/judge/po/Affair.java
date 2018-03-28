package com.judge.po;

import java.util.Date;

public class Affair {

    private Integer aId;

    private String aAffairs;

    private Integer aSponserId;

    private Integer aProjectId;

    private Date aStart;

    private Date aEnd;

    private Date datetime;

    public Integer getaId() {
        return aId;
    }

    public void setaId(Integer aId) {
        this.aId = aId;
    }

    public String getaAffairs() {
        return aAffairs;
    }

    public void setaAffairs(String aAffairs) {
        this.aAffairs = aAffairs == null ? null : aAffairs.trim();
    }

    public Integer getaSponserId() {
        return aSponserId;
    }

    public void setaSponserId(Integer aSponserId) {
        this.aSponserId = aSponserId;
    }

    public Integer getaProjectId() {
        return aProjectId;
    }

    public void setaProjectId(Integer aProjectId) {
        this.aProjectId = aProjectId;
    }

    public Date getaStart() {
        return aStart;
    }

    public void setaStart(Date aStart) {
        this.aStart = aStart;
    }

    public Date getaEnd() {
        return aEnd;
    }

    public void setaEnd(Date aEnd) {
        this.aEnd = aEnd;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }
}