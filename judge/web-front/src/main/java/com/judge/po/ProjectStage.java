package com.judge.po;

import java.util.Date;

public class ProjectStage {
    private Integer psId;

    private Integer psProjectId;

    private Integer psStage;

    private String psStageDescription;

    private Date psStart;

    private Date psEnd;

    private Long psAward;

    private Date datetime;

    public Integer getPsId() {
        return psId;
    }

    public void setPsId(Integer psId) {
        this.psId = psId;
    }

    public Integer getPsProjectId() {
        return psProjectId;
    }

    public void setPsProjectId(Integer psProjectId) {
        this.psProjectId = psProjectId;
    }

    public Integer getPsStage() {
        return psStage;
    }

    public void setPsStage(Integer psStage) {
        this.psStage = psStage;
    }

    public String getPsStageDescription() {
        return psStageDescription;
    }

    public void setPsStageDescription(String psStageDescription) {
        this.psStageDescription = psStageDescription == null ? null : psStageDescription.trim();
    }

    public Date getPsStart() {
        return psStart;
    }

    public void setPsStart(Date psStart) {
        this.psStart = psStart;
    }

    public Date getPsEnd() {
        return psEnd;
    }

    public void setPsEnd(Date psEnd) {
        this.psEnd = psEnd;
    }

    public Long getPsAward() {
        return psAward;
    }

    public void setPsAward(Long psAward) {
        this.psAward = psAward;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }
}