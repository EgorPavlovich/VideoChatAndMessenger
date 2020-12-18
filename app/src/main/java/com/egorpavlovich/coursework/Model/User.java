package com.egorpavlovich.coursework.Model;

public class User {
    private String id;
    private String fullName;
    private String gender;
    private String dateOfBirth;
    private String eMail;
    private String password;
    private String imageURL;
    private String status;
    private String search;


    public User(String id, String fullName, String gender, String dateOfBirth, String eMail, String password, String imageURL, String status, String search) {
        this.id = id;
        this.fullName = fullName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.eMail = eMail;
        this.password = password;
        this.imageURL = imageURL;
        this.status = status;
        this.search = search;
    }

    public User() {

    }

    public String getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getGender() {
        return gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String geteMail() {
        return eMail;
    }

    public String getPassword() {
        return password;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getStatus() {
        return status;
    }

    public String getSearch() {
        return search;
    }
}
