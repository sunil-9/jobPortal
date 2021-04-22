package com.example.android.jobprovider.model;

public class JobAcceptedModel {
    private String  id,seeker_id,status,job_id ,msg;

    public JobAcceptedModel(String id, String seeker_id, String status, String job_id, String msg) {
        this.id = id;
        this.seeker_id = seeker_id;
        this.status = status;
        this.job_id = job_id;
        this.msg = msg;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSeeker_id() {
        return seeker_id;
    }

    public void setSeeker_id(String seeker_id) {
        this.seeker_id = seeker_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getJob_id() {
        return job_id;
    }

    public void setJob_id(String job_id) {
        this.job_id = job_id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
