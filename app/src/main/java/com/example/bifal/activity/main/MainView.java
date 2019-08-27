package com.example.bifal.activity.main;

public interface MainView {
    void onSucces(String message);
    void onRequestError(String message);
    void showProgress();
    void hideProgress();
}
