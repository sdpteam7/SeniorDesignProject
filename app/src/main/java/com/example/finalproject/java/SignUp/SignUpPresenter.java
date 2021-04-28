package com.example.finalproject.java.SignUp;

public class SignUpPresenter implements SignUpContractor.Presenter{
    SignUpContractor.View view;

    public SignUpPresenter(SignUpContractor.View view) {
        this.view = view;
    }

    @Override
    public void OnSignUp() {

    }
}
