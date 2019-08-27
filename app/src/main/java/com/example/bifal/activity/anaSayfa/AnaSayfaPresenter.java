package com.example.bifal.activity.anaSayfa;

import android.util.Log;

import com.example.bifal.api.ApiClient;
import com.example.bifal.api.ApiInterface;
import com.example.bifal.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnaSayfaPresenter {
    private AnaSayfaView view;

    public AnaSayfaPresenter(AnaSayfaView view) {
        this.view = view;
    }

    void saveUser(String web_id, String name, String surname, String gmail, int kahve_hakki, int coin){
        view.showReflesh();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<User> call = apiInterface.saveUser(web_id ,name, surname, gmail, kahve_hakki, coin);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.d("USER",response.message());
                if(response.body() != null && response.isSuccessful()){
                    view.hideReflesh();
                    view.onSuccess(response.message());
                }else{
                    view.hideReflesh();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("USER",t.getLocalizedMessage());
                view.onRequestError(t.getMessage());
                view.hideReflesh();
            }
        });
    }

    void getUser(String web_id){
        view.showReflesh();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<User>> call = apiInterface.getUser(web_id);
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if(response.body() != null){
                    view.hideReflesh();
                    System.out.println("MESSAGE"+response.message());
                    view.onSuccess("Başarılı");
                    view.onGetResult(response.body());
                }else{
                    view.onRequestError("Hata");
                    view.hideReflesh();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                view.onRequestError(t.getMessage());
                view.hideReflesh();
            }
        });
    }
}
