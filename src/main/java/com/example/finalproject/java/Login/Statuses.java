package com.example.finalproject.java.Login;

public class Statuses {
    private String mail;
    private String statusId;

    public Statuses(String mail, String statusId) {
        this.mail = mail;
        this.statusId = statusId;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }
}
