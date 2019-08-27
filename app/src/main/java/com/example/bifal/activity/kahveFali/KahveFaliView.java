package com.example.bifal.activity.kahveFali;

import com.example.bifal.model.User;

import java.util.List;

public interface KahveFaliView {
    void onSucees(String message);
    void onRequestError(String message);
    void getUser(List<User> onGetResult);
    void showLoadin();
    void hideLoading();
}
