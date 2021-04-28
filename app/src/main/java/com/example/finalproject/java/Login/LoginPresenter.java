package com.example.finalproject.java.Login;

public class LoginPresenter extends LoginActivity implements LoginContractor.Presenter {
    LoginContractor.View view;

    public LoginPresenter(LoginContractor.View view) {
        this.view = view;
    }

    @Override
    public void OnLogin(String Savedlogin, String Savedpass, String user, String pass) {
        if(user.equals(Savedlogin)&&pass.equals(Savedpass))
        {
            view.OnSuccess("Logged In!");
        }
        else {
            view.OnError("Something Went Wrong!");
        }
    }
}
