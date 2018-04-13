package com.judge.po;

public class FeedbackRecord {
    int fId;
    String evaluator;
    int atitude;
    int equality;
    int complishment;
    String reason;
    String affair;
    String evtime;

    public String getAffair() {
        return affair;
    }

    public void setAffair(String affair) {
        this.affair = affair;
    }

    public String getEvtime() {
        return evtime;
    }

    public void setEvtime(String evtime) {
        this.evtime = evtime;
    }

    public int getfId() {
        return fId;
    }

    public void setfId(int fId) {
        this.fId = fId;
    }

    public String getEvaluator() {
        return evaluator;
    }

    public void setEvaluator(String evaluator) {
        this.evaluator = evaluator;
    }

    public int getAtitude() {
        return atitude;
    }

    public void setAtitude(int atitude) {
        this.atitude = atitude;
    }

    public int getEquality() {
        return equality;
    }

    public void setEquality(int equality) {
        this.equality = equality;
    }

    public int getComplishment() {
        return complishment;
    }

    public void setComplishment(int complishment) {
        this.complishment = complishment;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
