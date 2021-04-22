package com.example.android.jobprovider.model;

public class JobAvailable {
    String job_id,provider_id,location,job_title,cat,designation,salary,website,phone,no_of_vacancy,qualification,skills,expDate,experiences,post_date,isAvailable;

    public JobAvailable() {
    }

    public JobAvailable(String job_id, String provider_id, String location, String job_title, String cat, String designation, String salary, String website, String phone, String no_of_vacancy, String qualification, String skills, String date, String experiences, String post_date, String isAvailable) {
        this.job_id = job_id;
        this.provider_id = provider_id;
        this.location = location;
        this.job_title = job_title;
        this.cat = cat;
        this.designation = designation;
        this.salary = salary;
        this.website = website;
        this.phone = phone;
        this.no_of_vacancy = no_of_vacancy;
        this.qualification = qualification;
        this.skills = skills;
        this.expDate = date;
        this.experiences = experiences;
        this.post_date = post_date;
        this.isAvailable = isAvailable;
    }

    public String getProvider_id() {
        return provider_id;
    }

    public void setProvider_id(String provider_id) {
        this.provider_id = provider_id;
    }

    public String getJob_id() {
        return job_id;
    }

    public void setJob_id(String job_id) {
        this.job_id = job_id;
    }


    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }



    public String getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(String isAvailable) {
        this.isAvailable = isAvailable;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPostDate() {
        return post_date;
    }

    public void setPostDate(String post_date) {
        this.post_date = post_date;
    }

    public String getJobTitle() {
        return job_title;
    }

    public void setJobTitle(String job_title) {
        this.job_title = job_title;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNoOfVacancy() {
        return no_of_vacancy;
    }

    public void setNoOfVacancy(String no_of_vacancy) {
        this.no_of_vacancy = no_of_vacancy;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getExpDate() {
        return expDate;
    }

    public void getExpDate(String date) {
        this.expDate = date;
    }

    public String getExperiences() {
        return experiences;
    }

    public void setExperiences(String experiences) {
        this.experiences = experiences;
    }
}
