package com.judge.po;

public class ScoreReport {

    int uId;
    String uUsername;
    int pAward;
    String pName;

    int role1_score;
    int role2_score;
    int role3_score;
    int role4_score;

    int uAfairSum;
    Double scoreSum;
    String evSum;
    String uRole;
    String pStart;
    String pEnd;
    String uProportion;
    String uPerformance;

    String fileName;

    String atitudeDetail;
    String qualityEfficientDetail;
    String complishmentDetail;

    public String getAtitudeDetail() {
        return atitudeDetail;
    }

    public void setAtitudeDetail(String atitudeDetail) {
        this.atitudeDetail = atitudeDetail;
    }

    public String getQualityEfficientDetail() {
        return qualityEfficientDetail;
    }

    public void setQualityEfficientDetail(String qualityEfficientDetail) {
        this.qualityEfficientDetail = qualityEfficientDetail;
    }

    public String getComplishmentDetail() {
        return complishmentDetail;
    }

    public void setComplishmentDetail(String complishmentDetail) {
        this.complishmentDetail = complishmentDetail;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getuPerformance() {
        return uPerformance;
    }

    public void setuPerformance(String uPerformance) {
        this.uPerformance = uPerformance;
    }

    public String getuProportion() {
        return uProportion;
    }

    public void setuProportion(String uProportion) {
        this.uProportion = uProportion;
    }

    public Double getScoreSum() {
        return scoreSum;
    }

    public void setScoreSum(Double scoreSum) {
        this.scoreSum = scoreSum;
    }

    public int getuAfairSum() {
        return uAfairSum;
    }

    public void setuAfairSum(int uAfairSum) {
        this.uAfairSum = uAfairSum;
    }

    public int getpAward() {
        return pAward;
    }

    public void setpAward(int pAward) {
        this.pAward = pAward;
    }

    public String getpStart() {
        return pStart;
    }

    public void setpStart(String pStart) {
        this.pStart = pStart;
    }

    public String getpEnd() {
        return pEnd;
    }

    public void setpEnd(String pEnd) {
        this.pEnd = pEnd;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getuRole() {
        return uRole;
    }

    public void setuRole(String uRole) {
        this.uRole = uRole;
    }

    public int getuId() {
        return uId;
    }

    public void setuId(int uId) {
        this.uId = uId;
    }

    public String getuUsername() {
        return uUsername;
    }

    public void setuUsername(String uUsername) {
        this.uUsername = uUsername;
    }

    public int getRole1_score() {
        return role1_score;
    }

    public void setRole1_score(int role1_score) {
        this.role1_score = role1_score;
    }

    public int getRole2_score() {
        return role2_score;
    }

    public void setRole2_score(int role2_score) {
        this.role2_score = role2_score;
    }

    public int getRole3_score() {
        return role3_score;
    }

    public void setRole3_score(int role3_score) {
        this.role3_score = role3_score;
    }

    public int getRole4_score() {
        return role4_score;
    }

    public void setRole4_score(int role4_score) {
        this.role4_score = role4_score;
    }

    public String getEvSum() {
        return evSum;
    }

    public void setEvSum(String evSum) {
        this.evSum = evSum;
    }

    @Override
    public String toString() {
        return "ScoreReport{" +
                "uId=" + uId +
                ", uUsername='" + uUsername + '\'' +
                ", pAward=" + pAward +
                ", pName='" + pName + '\'' +
                ", role1_score=" + role1_score +
                ", role2_score=" + role2_score +
                ", role3_score=" + role3_score +
                ", role4_score=" + role4_score +
                ", uAfairSum=" + uAfairSum +
                ", scoreSum=" + scoreSum +
                ", evSum='" + evSum + '\'' +
                ", uRole='" + uRole + '\'' +
                ", pStart='" + pStart + '\'' +
                ", pEnd='" + pEnd + '\'' +
                ", uProportion='" + uProportion + '\'' +
                ", uPerformance='" + uPerformance + '\'' +
                ", fileName='" + fileName + '\'' +
                ", atitudeDetail='" + atitudeDetail + '\'' +
                ", qualityEfficientDetail='" + qualityEfficientDetail + '\'' +
                ", complishmentDetail='" + complishmentDetail + '\'' +
                '}';
    }
}
