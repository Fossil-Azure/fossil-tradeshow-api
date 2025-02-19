package com.fossil.tradeshow.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class Users {

    @Id
    private String emailId;

    private String password;

    private String userAccess;

    private String nameOfUser;

    private String categoryAccess;

    private String brandAccess;

    private String countryAccess;

    private String currency;

    private String company;

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserAccess() {
        return userAccess;
    }

    public void setUserAccess(String userAccess) {
        this.userAccess = userAccess;
    }

    public String getCategoryAccess() {
        return categoryAccess;
    }

    public void setCategoryAccess(String categoryAccess) {
        this.categoryAccess = categoryAccess;
    }

    public String getBrandAccess() {
        return brandAccess;
    }

    public void setBrandAccess(String brandAccess) {
        this.brandAccess = brandAccess;
    }

    public String getCountryAccess() {
        return countryAccess;
    }

    public void setCountryAccess(String countryAccess) {
        this.countryAccess = countryAccess;
    }

    public String getNameOfUser() {
        return nameOfUser;
    }

    public void setNameOfUser(String nameOfUser) {
        this.nameOfUser = nameOfUser;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
