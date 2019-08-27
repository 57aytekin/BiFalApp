package com.example.bifal.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Fal {

    @Expose
    @SerializedName("id")
    private int id;
    @Expose

    @SerializedName("user_id")
    private int user_id;

    @SerializedName("fal_id")
    private int fal_id;

    @SerializedName("name")
    private String name;

    @Expose
    @SerializedName("fal")
    private String fal;

    @Expose
    @SerializedName("tarih")
    private String tarih;

    @Expose
    @SerializedName("success")private boolean success;

    @Expose
    @SerializedName("message")private String message;

    public int getFal_id() {
        return fal_id;
    }

    public void setFal_id(int fal_id) {
        this.fal_id = fal_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTarih() {
        return tarih;
    }

    public void setTarih(String tarih) {
        this.tarih = tarih;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
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

    public String getFal() {
        return fal;
    }

    public void setFal(String fal) {
        this.fal = fal;
    }
}
