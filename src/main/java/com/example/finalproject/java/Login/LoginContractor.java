package com.example.finalproject.java.Login;

public interface LoginContractor {
    interface View{
        void OnSuccess(String message);
        void OnError(String message);
    }
    interface Presenter{
        void OnLogin(String Saveduser, String Savedpass, String user, String pass);
    }
}
