package com.example.bifal.activity.fallarim;

import com.example.bifal.api.ApiClient;
import com.example.bifal.api.ApiInterface;
import com.example.bifal.model.Fal;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FallarimPresenter {
    FallarimView view;

    public FallarimPresenter(FallarimView view) {
        this.view = view;
    }

    void getFallarim(int user_id){
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Fal>> call = apiInterface.getFallarim(user_id);
        call.enqueue(new Callback<List<Fal>>() {
            @Override
            public void onResponse(Call<List<Fal>> call, Response<List<Fal>> response) {
                if(response.isSuccessful() && response.body() != null){
                    view.onSuccess(response.message());
                    view.onGetResult(response.body());
                }else{
                    view.onRequestError(response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Fal>> call, Throwable t) {
                view.onRequestError(t.getMessage());
            }
        });
    }
}
