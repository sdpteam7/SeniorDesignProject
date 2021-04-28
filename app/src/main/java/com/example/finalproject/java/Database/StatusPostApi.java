package com.example.finalproject.java.Database;



public class StatusPostApi {

    private String mail;
    private String statusId;

    public StatusPostApi(String mail, String statusId) {
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
