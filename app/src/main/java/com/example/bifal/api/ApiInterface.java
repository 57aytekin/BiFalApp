package com.example.bifal.api;

import com.example.bifal.model.Fal;
import com.example.bifal.model.Photo;
import com.example.bifal.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("save_user.php")
    Call<User>saveUser(
        @Field("web_id") String web_id,
        @Field("name") String name,
        @Field("surname") String surname,
        @Field("gmail") String gmail,
        @Field("kahve_hakki") int kahve_hakki,
        @Field("coin") int coin
    );

    @FormUrlEncoded
    @POST("update_user.php")
    Call<User> update_user(
        @Field("web_id") String web_id,
        @Field("name") String name,
        @Field("surname") String surname,
        @Field("is_durumu") String is_durumu,
        @Field("medeni_durum") String medeni_durum,
        @Field("iliski_durumu") String iliski_durumu,
        @Field("yas_tarih") String yas_tarih
    );

    @FormUrlEncoded
    @POST("get_user.php")
    Call<List<User>> getUser(
        @Field("web_id") String web_id
    );
    @FormUrlEncoded
    @POST("get_fallarim.php")
    Call<List<Fal>> getFallarim(
            @Field("user_id") int user_id
    );

    @FormUrlEncoded
    @POST("upload_image.php")
    Call<Photo> upload_image(
        @Field("user_id") int user_id,
        @Field("name1") String name1,
        @Field("name2") String name2,
        @Field("name3") String name3,
        @Field("image1") String image1,
        @Field("image2") String image2,
        @Field("image3") String image3
        );

    @FormUrlEncoded
    @POST("save_user_fal.php")
    Call<Fal> saveUserFal(
           @Field("user_id") int user_id,
           @Field("fal_id") int fal_id
    ) ;

    @FormUrlEncoded
    @POST("update_kahve_hakki.php")
    Call<Void> updateKahveHakki(
      @Field("id") int id,
      @Field("kahve_hakki") int kahve_hakki
    );
    @FormUrlEncoded
    @POST("update_gun_kontrol.php")
    Call<Void> update_gun_kontrol(
            @Field("id") int id,
            @Field("kahve_hakki") int kahve_hakki
    );
    @FormUrlEncoded
    @POST("update_token.php")
    Call<Void> update_token(
            @Field("id") int id,
            @Field("token") String token
    );
    @FormUrlEncoded
    @POST("push_notification.php")
    Call<Void> push_notifi(
            @Field("id") int id
    );
}
