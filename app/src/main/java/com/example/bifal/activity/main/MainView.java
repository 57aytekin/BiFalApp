package com.example.bifal.activity.main;

import com.example.bifal.model.User;

import java.util.List;

public interface MainView {
    void onSucces(String message);
    void onRequestError(String message);
    void showProgress();
    void hideProgress();
    void onGetResult(List<User> getUser);
}
