package com.example.android.jobprovider.model;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class SeekersInfo {
    private String userId,username,email,location,gender,mobile,imageUrl,cv_link,achievements,certifications,dob,experience,skills;

    public SeekersInfo(String userId, String username, String email, String location, String gender, String mobile, String imageUrl, String cv_link, String achievements, String certifications, String dob, String experience, String skills) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.location = location;
        this.gender = gender;
        this.mobile = mobile;
        this.imageUrl = imageUrl;
        this.cv_link = cv_link;
        this.achievements = achievements;
        this.certifications = certifications;
        this.dob = dob;
        this.experience = experience;
        this.skills = skills;
    }
    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public SeekersInfo() {
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAchievements() {
        return achievements;
    }

    public void setAchievements(String achievements) {
        this.achievements = achievements;
    }

    public String getCertifications() {
        return certifications;
    }

    public void setCertifications(String certifications) {
        this.certifications = certifications;
    }

    public String getCv_link() {
        return cv_link;
    }

    public void setCv_link(String cv_link) {
        this.cv_link = cv_link;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


}
