package com.example.android.jobprovider.model;

public class JobApplyedDetails {
    String id, job_id, provider_id, seeker_id, applied_date;

    public JobApplyedDetails(String id, String job_id, String provider_id, String seeker_id, String applied_date) {
        this.id = id;
        this.job_id = job_id;
        this.provider_id = provider_id;
        this.seeker_id = seeker_id;
        this.applied_date = applied_date;
    }

    public JobApplyedDetails() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApplied_date() {
        return applied_date;
    }

    public void setApplied_date(String applied_date) {
        this.applied_date = applied_date;
    }

    public String getJob_id() {
        return job_id;
    }

    public void setJob_id(String job_id) {
        this.job_id = job_id;
    }

    public String getProvider_id() {
        return provider_id;
    }

    public void setProvider_id(String provider_id) {
        this.provider_id = provider_id;
    }

    public String getSeeker_id() {
        return seeker_id;
    }

    public void setSeeker_id(String seeker_id) {
        this.seeker_id = seeker_id;
    }
}

