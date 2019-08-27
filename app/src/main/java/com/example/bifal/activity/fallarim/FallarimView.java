package com.example.bifal.activity.fallarim;

import com.example.bifal.model.Fal;

import java.util.List;

public interface FallarimView {
    void onSuccess(String message);
    void onRequestError(String message);
    void onGetResult(List<Fal> list);
}
