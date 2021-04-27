package com.example.finalproject.java.Database;

public class EnrollPostApi {
    String id;
    String mail;
    String courseName;

    public EnrollPostApi(String id, String mail, String courseName) {
        this.id = id;
        this.mail = mail;
        this.courseName = courseName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
