package com.example.bifal.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {
    @Expose
    @SerializedName("id")
    private int id;
    @Expose
    @SerializedName("web_id")
    private String web_id;
    @Expose
    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("surname")
    private String surname;
    @Expose
    @SerializedName("gmail")
    private String gmail;
    @Expose
    @SerializedName("is_durumu")
    private String is_durumu;
    @Expose
    @SerializedName("medeni_durum")
    private String medeni_durum;
    @Expose
    @SerializedName("iliski_durumu")
    private String iliski_durumu;
    @Expose
    @SerializedName("yas_tarih")
    private String yas_tarih;
    @Expose
    @SerializedName("kahve_hakki")
    private int kahve_hakki;
    @Expose
    @SerializedName("coin")
    private int coin;
    @Expose
    @SerializedName("gun_kontrol")
    private String gun_kontrol;
    @Expose
    @SerializedName("success") private boolean success;
    @Expose
    @SerializedName("message") private String message;

    public String getGun_kontrol() {
        return gun_kontrol;
    }

    public void setGun_kontrol(String gun_kontrol) {
        this.gun_kontrol = gun_kontrol;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getWeb_id() {
        return web_id;
    }

    public void setWeb_id(String web_id) {
        this.web_id = web_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public String getIs_durumu() {
        return is_durumu;
    }

    public void setIs_durumu(String is_durumu) {
        this.is_durumu = is_durumu;
    }

    public String getMedeni_durum() {
        return medeni_durum;
    }

    public void setMedeni_durum(String medeni_durum) {
        this.medeni_durum = medeni_durum;
    }

    public String getIliski_durumu() {
        return iliski_durumu;
    }

    public void setIliski_durumu(String iliski_durumu) {
        this.iliski_durumu = iliski_durumu;
    }

    public String getYas_tarih() {
        return yas_tarih;
    }

    public void setYas_tarih(String yas_tarih) {
        this.yas_tarih = yas_tarih;
    }

    public int getKahve_hakki() {
        return kahve_hakki;
    }

    public void setKahve_hakki(int kahve_hakki) {
        this.kahve_hakki = kahve_hakki;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }
}
