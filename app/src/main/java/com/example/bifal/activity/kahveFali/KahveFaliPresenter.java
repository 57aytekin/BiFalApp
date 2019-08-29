package com.example.bifal.activity.kahveFali;

import com.example.bifal.api.ApiClient;
import com.example.bifal.api.ApiInterface;
import com.example.bifal.model.Fal;
import com.example.bifal.model.Photo;
import com.example.bifal.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KahveFaliPresenter {

    KahveFaliView view;

    public KahveFaliPresenter(KahveFaliView view) {
        this.view = view;
    }

    public void update_user(String web_id, String name, String surname, String is_durumu, String medeni_durum, String iliski_durumu, String yas_tarih){
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<User> call = apiInterface.update_user(web_id, name, surname, is_durumu, medeni_durum, iliski_durumu, yas_tarih);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                System.out.println("USER_ID=>"+response.body().getId());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }
    void getUser(String web_id){
        view.showLoadin();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<User>> call = apiInterface.getUser(web_id);
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if(response.body() != null){
                    view.getUser(response.body());
                    view.hideLoading();
                }else{
                    view.hideLoading();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                view.onRequestError(t.getMessage());
                view.hideLoading();
            }
        });
    }

    void upload_image(int user_id, String name1, String name2, String name3, String image1, String image2, String image3){
        view.showLoadin();
        ApiInterface apiInterface = ApiClient.getPhotoApiClient().create(ApiInterface.class);
        Call<Photo> call = apiInterface.upload_image(user_id, name1, name2, name3, image1, image2, image3);
        call.enqueue(new Callback<Photo>() {
            @Override
            public void onResponse(Call<Photo> call, Response<Photo> response) {
                if(response.body() != null ){
                    view.hideLoading();
                }else{
                    view.onRequestError(response.message());
                    view.hideLoading();
                }
            }

            @Override
            public void onFailure(Call<Photo> call, Throwable t) {
                view.hideLoading();
                view.onRequestError(t.getLocalizedMessage());
            }
        });
    }

    void saveUserFal(int user_id, int fal_id){
        view.showLoadin();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<Fal> call = apiInterface.saveUserFal(user_id, fal_id);
        call.enqueue(new Callback<Fal>() {
            @Override
            public void onResponse(Call<Fal> call, Response<Fal> response) {
                if(response.isSuccessful() && response.body()!= null){
                    view.hideLoading();
                    view.onSucees(response.message());
                }else{
                    view.onRequestError(response.message());
                    view.hideLoading();
                }
            }

            @Override
            public void onFailure(Call<Fal> call, Throwable t) {
                view.onRequestError(t.getLocalizedMessage());
                view.hideLoading();
            }
        });
    }

    void update_kahve_hakki(int id, int kahve_hakki){
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<Void> call = apiInterface.updateKahveHakki(id, kahve_hakki);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }
    void update_token(int id, String token){
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<Void> call = apiInterface.update_token(id, token);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }
}
