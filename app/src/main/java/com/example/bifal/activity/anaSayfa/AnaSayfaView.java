package com.example.bifal.activity.anaSayfa;

import com.example.bifal.model.Fal;
import com.example.bifal.model.User;

import java.util.List;

public interface AnaSayfaView {
    void onSuccess(String message);
    void onRequestError(String message);
    void onGetResult(List<User> list);
    void onGetFals(List<Fal> fals);
    void showReflesh();
    void hideReflesh();
}
