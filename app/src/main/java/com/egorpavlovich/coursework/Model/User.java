package com.egorpavlovich.coursework.Model;

public class User {
    private String id;
    private String fullName;
    private String gender;
    private String dateOfBirth;
    private String eMail;
    private String password;
    private String imageURL;


    public User(String id, String fullName, String gender, String dateOfBirth, String eMail, String password, String imageURL) {
        this.id = id;
        this.fullName = fullName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.eMail = eMail;
        this.password = password;
        this.imageURL = imageURL;
    }

    public User() {

    }

    public String getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String geteMail() {
        return eMail;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public String getPassword() {
        return password;
    }
}
