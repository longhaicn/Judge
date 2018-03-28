package com.judge.po;

import java.util.Date;

public class Judge {
    private Integer jId;

    private Integer jAffairId;

    private Integer jProjectId;

    private Integer jEvaluatorRoleId;

    private Integer jEvaluatorId;

    private Integer jEvaluatedId;

    private Integer jAtitude;

    private Integer jQualityEfficient;

    private Integer jComplishment;

    private String jReason;

    private Date datetime;

    public Integer getjId() {
        return jId;
    }

    public void setjId(Integer jId) {
        this.jId = jId;
    }

    public Integer getjAffairId() {
        return jAffairId;
    }

    public void setjAffairId(Integer jAffairId) {
        this.jAffairId = jAffairId;
    }

    public Integer getjEvaluatorRoleId() {
        return jEvaluatorRoleId;
    }

    public void setjEvaluatorRoleId(Integer jEvaluatorRoleId) {
        this.jEvaluatorRoleId = jEvaluatorRoleId;
    }
    public Integer getjProjectId() {
        return jProjectId;
    }

    public String getjReason() {
        return jReason;
    }

    public void setjReason(String jReason) {
        this.jReason = jReason;
    }

    public void setjProjectId(Integer jProjectId) {
        this.jProjectId = jProjectId;
    }
    public Integer getjEvaluatorId() {
        return jEvaluatorId;
    }

    public void setjEvaluatorId(Integer jEvaluatorId) {
        this.jEvaluatorId = jEvaluatorId;
    }

    public Integer getjEvaluatedId() {
        return jEvaluatedId;
    }

    public void setjEvaluatedId(Integer jEvaluatedId) {
        this.jEvaluatedId = jEvaluatedId;
    }

    public Integer getjAtitude() {
        return jAtitude;
    }

    public void setjAtitude(Integer jAtitude) {
        this.jAtitude = jAtitude;
    }

    public Integer getjQualityEfficient() {
        return jQualityEfficient;
    }

    public void setjQualityEfficient(Integer jQualityEfficient) {
        this.jQualityEfficient = jQualityEfficient;
    }

    public Integer getjComplishment() {
        return jComplishment;
    }

    public void setjComplishment(Integer jComplishment) {
        this.jComplishment = jComplishment;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }
}