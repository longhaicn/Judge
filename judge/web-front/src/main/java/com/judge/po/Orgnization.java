package com.judge.po;

import java.util.Date;

public class Orgnization {
    private Integer oId;

    private Integer oProjectId;

    private String oProjectName;

    private Integer oUserId;

    private String oUserName;

    private Double oPenalty;

    private Integer oRoleId;

    private Date datetime;

    private Integer oStatus;

    public Integer getoStatus() {
        return oStatus;
    }

    public void setoStatus(Integer oStatus) {
        this.oStatus = oStatus;
    }

    public Integer getoId() {
        return oId;
    }

    public void setoId(Integer oId) {
        this.oId = oId;
    }

    public Integer getoProjectId() {
        return oProjectId;
    }

    public void setoProjectId(Integer oProjectId) {
        this.oProjectId = oProjectId;
    }

    public String getoProjectName() {
        return oProjectName;
    }

    public void setoProjectName(String oProjectName) {
        this.oProjectName = oProjectName == null ? null : oProjectName.trim();
    }

    public Integer getoUserId() {
        return oUserId;
    }

    public void setoUserId(Integer oUserId) {
        this.oUserId = oUserId;
    }

    public String getoUserName() {
        return oUserName;
    }

    public void setoUserName(String oUserName) {
        this.oUserName = oUserName == null ? null : oUserName.trim();
    }

    public Integer getoRoleId() {
        return oRoleId;
    }

    public void setoRoleId(Integer oRoleId) {
        this.oRoleId = oRoleId;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public Double getoPenalty() {
        return oPenalty;
    }

    public void setoPenalty(Double oPenalty) {
        this.oPenalty = oPenalty;
    }
}