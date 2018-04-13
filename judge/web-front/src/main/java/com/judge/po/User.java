package com.judge.po;

import com.judge.com.judge.ldriver.NonInsertDb;

import java.util.Date;

public class User {
    private Integer uId;

    private String oaId;

    private String dingId;

    private String uNickname;

    private String uShortName;

    private String uUsername;

    private String uPassword;

    private String uEmail;

    private String uStatus;

    private Integer uRole;

    private String uDepartment;

    private String token;

    private Date datetime;

    @NonInsertDb
    private Double uPenalty;

    public Integer getuId() {
        return uId;
    }

    public void setuId(Integer uId) {
        this.uId = uId;
    }

    public String getOaId() {
        return oaId;
    }

    public void setOaId(String oaId) {
        this.oaId = oaId;
    }

    public String getDingId() {
        return dingId;
    }

    public void setDingId(String dingId) {
        this.dingId = dingId;
    }

    public String getuNickname() {
        return uNickname;
    }

    public void setuNickname(String uNickname) {
        this.uNickname = uNickname == null ? null : uNickname.trim();
    }

    public String getuShortName() {
        return uShortName;
    }

    public void setuShortName(String uShortName) {
        this.uShortName = uShortName == null ? null : uShortName.trim();
    }

    public String getuUsername() {
        return uUsername;
    }

    public void setuUsername(String uUsername) {
        this.uUsername = uUsername == null ? null : uUsername.trim();
    }

    public String getuPassword() {
        return uPassword;
    }

    public void setuPassword(String uPassword) {
        this.uPassword = uPassword == null ? null : uPassword.trim();
    }

    public String getuEmail() {
        return uEmail;
    }

    public void setuEmail(String uEmail) {
        this.uEmail = uEmail == null ? null : uEmail.trim();
    }

    public String getuStatus() {
        return uStatus;
    }

    public void setuStatus(String uStatus) {
        this.uStatus = uStatus == null ? null : uStatus.trim();
    }

    public Integer getuRole() {
        return uRole;
    }

    public void setuRole(Integer uRole) {
        this.uRole = uRole;
    }

    public String getuDepartment() {
        return uDepartment;
    }

    public void setuDepartment(String uDepartment) {
        this.uDepartment = uDepartment == null ? null : uDepartment.trim();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public Double getuPenalty() {
        return uPenalty;
    }

    public void setuPenalty(Double uPenalty) {
        this.uPenalty = uPenalty;
    }
}