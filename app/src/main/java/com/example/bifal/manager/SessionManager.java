package com.example.bifal.manager;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private Context context;
    int PRIVATE_MODE = 0;

    public static final String FIRST_NAME = "FİRST_NAME";
    public static final String LAST_NAME = "LAST_NAME";
    public static final String EMAIL = "EMAIL";
    public static final String USERID = "USERID";
    public static final String ID = "ID";
    public static final String USERPHOTO = "USERPHOTO";

    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("LOGIN",PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }
    public void createSession(String first_name,String last_name, String email, String userid, String userPhoto){
        editor.putString(FIRST_NAME,first_name);
        editor.putString(LAST_NAME,last_name);
        editor.putString(EMAIL,email);
        editor.putString(USERID, userid);
        editor.putString(USERPHOTO, userPhoto);
        editor.apply();
    }
    public void create_id(String id){
        editor.putString(ID, id);
        editor.apply();
    }
    public HashMap<String, String> userDetail(){
        HashMap<String, String > user = new HashMap<>();
        user.put(FIRST_NAME, sharedPreferences.getString(FIRST_NAME, null));
        user.put(LAST_NAME, sharedPreferences.getString(LAST_NAME, null));
        user.put(EMAIL, sharedPreferences.getString(EMAIL,null));
        user.put(USERID, sharedPreferences.getString(USERID, null));
        user.put(ID, sharedPreferences.getString(ID, null));
        user.put(USERPHOTO, sharedPreferences.getString(USERPHOTO, null));
        return user;
    }
    public void logOut(){
        editor.clear();
        editor.commit();
    }
}
