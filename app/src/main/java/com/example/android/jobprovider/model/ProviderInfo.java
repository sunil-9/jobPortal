package com.example.android.jobprovider.model;

public class ProviderInfo {
    private  String userId,Company_Name,email,mobile,location,imageUrl;

    public ProviderInfo(String userId, String company_Name, String email, String mobile, String location, String imageUrl) {
        this.userId = userId;
        Company_Name = company_Name;
        this.email = email;
        this.mobile = mobile;
        this.location = location;
        this.imageUrl = imageUrl;
    }

    public ProviderInfo() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCompany_Name() {
        return Company_Name;
    }

    public void setCompany_Name(String company_Name) {
        Company_Name = company_Name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
