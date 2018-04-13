package com.judge.po;

public class AffairMiss {
    String project;
    String username;
    int uId;
    String endtime;

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public int getuId() {
        return uId;
    }

    public void setuId(int uId) {
        this.uId = uId;
    }

    String affair;

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAffair() {
        return affair;
    }

    public void setAffair(String affair) {
        this.affair = affair;
    }

    @Override
    public String toString() {
        return "AffairMiss{" +
                "project='" + project + '\'' +
                ", username='" + username + '\'' +
                ", uId=" + uId +
                ", affair='" + affair + '\'' +
                '}';
    }
}
