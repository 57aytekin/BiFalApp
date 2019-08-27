package com.example.bifal.activity.main;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bifal.activity.anaSayfa.AnaSayfaActivity;
import com.example.bifal.manager.SessionManager;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.tomer.fadingtextview.FadingTextView;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements MainView{
    private int RC_SIGN_IN = 0;
    GoogleSignInClient mGoogleSignInClient;

    FadingTextView tvHg;
    Animation fromBottomIcon;
    ImageView ivLogo;
    SignInButton btnSignIngoogle;
    TextView tvGirisSozlesmesi;
    CheckBox checkBox;
    CallbackManager callbackManager;
    ProgressDialog progressDialog;
    private static final String EMAIL = "email";
    private SessionManager sessionManager;

    private int coin=0, kahve_hakki = 1;
    MainPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.bifal.R.layout.activity_main);
        uiElement();
        presenter = new MainPresenter(this);
        animasyonluGetir();
        sessionManager = new SessionManager(getApplicationContext());

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        if(checkBox.isChecked()){
            btnSignIngoogle.setClickable(true);
        }
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.isChecked()){
                    btnSignIngoogle.setClickable(true);
                }else{
                    btnSignIngoogle.setClickable(false);
                }
            }
        });
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        btnSignIngoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.this.signIn();
            }
        });

        callbackManager = CallbackManager.Factory.create();
        /*btnSignInFace.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                //startActivity(new Intent(getApplicationContext(), AnaSayfaActivity.class));
                //finish();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(MainActivity.this, "Bir hata oluştu tekrar deneyin: "+error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void animasyonluGetir(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                tvHg.stop();
                tvHg.setVisibility(View.GONE);
                //btnSignInFace.setVisibility(View.VISIBLE);
                btnSignIngoogle.setVisibility(View.VISIBLE);
                btnSignIngoogle.setClickable(false);
                checkBox.setVisibility(View.VISIBLE);
                btnSignIngoogle.setAnimation(fromBottomIcon);
                //btnSignInFace.setAnimation(fromBottomIcon);

                String sozlesme =getString(com.example.bifal.R.string.giris_one)+" "+"<i>"+getString(com.example.bifal.R.string.giris_two)+"</i>"+" "+getString(com.example.bifal.R.string.giris_three);
                String s = "Kulanım ve Gizlilik Koşulların için "+"<i>"+"tıklayınız.."+"</i>";
                tvGirisSozlesmesi.setText(Html.fromHtml(s));
                tvGirisSozlesmesi.setAnimation(fromBottomIcon);
                checkBox.setAnimation(fromBottomIcon);
                checkBox.setText(Html.fromHtml(sozlesme));

                Animation animation = new TranslateAnimation(0, 0,0, -300);
                animation.setDuration(1000);
                animation.setFillAfter(true);
                ivLogo.startAnimation(animation);
            }
        },10000);
    }

    public void uiElement(){
        tvHg = findViewById(com.example.bifal.R.id.tvHg);
        //btnSignInFace = findViewById(com.example.bifal.R.id.btnSingInFace);
        btnSignIngoogle = findViewById(com.example.bifal.R.id.btnSignInGoogle);
        tvGirisSozlesmesi = findViewById(com.example.bifal.R.id.tvGirisSozlemesi);
        checkBox = findViewById(com.example.bifal.R.id.checkBox);
        ivLogo = findViewById(com.example.bifal.R.id.logo);
        fromBottomIcon = AnimationUtils.loadAnimation(this, com.example.bifal.R.anim.from_bottom_icon);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Lütfen bekleyiniz.");
        progressDialog.setCancelable(false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> task) {
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            String first_name = account.getGivenName();
            String last_name = account.getFamilyName();
            String email = account.getEmail();
            String id = account.getId();
            String photo_url = "";

            if(account.getPhotoUrl() == null){
                photo_url = "http://aytekincomezz.000webhostapp.com/YeniApp/image/default.jpg";
            }else{
                photo_url = account.getPhotoUrl().toString();
            }
            //presenter.saveUser(id, first_name, last_name, email, kahve_hakki, coin);
            Log.d("LOG","hadleSignInResult çalıstıi");

            sessionManager.createSession(first_name,last_name,email,id,photo_url);
            startActivity(new Intent(MainActivity.this, AnaSayfaActivity.class));
            finish();

        } catch (ApiException e) {
            Log.w("Google Sign In Error","signInResult:failed code="+e.getStatusCode());
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account != null){
            String photo ="";
            /*if(account.getGivenName() == null){
                String email = account.getEmail();
                String id = account.getId();
                String last_name = account.getFamilyName();
                presenter.saveUser(id,"Name", last_name,email, kahve_hakki, coin);
                sessionManager.createSession("Name",last_name,email,id,photo);
            }else if(account.getFamilyName() == null){
                String email = account.getEmail();
                String id = account.getId();
                String first_name = account.getGivenName();
                presenter.saveUser(id,first_name, "Surname",email, kahve_hakki, coin);
                sessionManager.createSession(first_name,"Surname",email,id,photo);
            }*/
            String first_name = account.getGivenName();
            String last_name = account.getFamilyName();
            String email = account.getEmail();
            String id = account.getId();
            if(account.getPhotoUrl().toString() == null){
                photo = "http://aytekincomezz.000webhostapp.com/YeniApp/image/default.jpg";
            }else{
                photo = account.getPhotoUrl().toString();
            }
            presenter.saveUser(id, first_name, last_name, email, kahve_hakki, coin);
            sessionManager.createSession(first_name,last_name,email,id,photo);


            Log.d("LOG","OnStart metodu calisti");
            startActivity(new Intent(MainActivity.this, AnaSayfaActivity.class));
            finish();
        }
        super.onStart();
    }

    /*AccessTokenTracker tokenTracker = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
            if(currentAccessToken!= null){
                loaduserProfile(currentAccessToken);
                startActivity(new Intent(getApplicationContext(), AnaSayfaActivity.class));
                finish();
            }
        }
    };

    private void loaduserProfile(AccessToken newAccesToken){
        GraphRequest request = GraphRequest.newMeRequest(newAccesToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    String first_name = object.getString("first_name");
                    String last_name = object.getString("last_name");
                    String email = object.getString("email");
                    String id = object.getString("id");
                    String image_url = "https://graph.facebook.com/"+id+"/picture?type=normal";

                    Log.d("LOG","loaduserProflie calisti");
                    sessionManager.createSession(first_name,last_name,email,id,image_url);
                    presenter.saveUser(id, first_name, last_name, email, kahve_hakki, coin);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        Bundle parameters = new Bundle();
        parameters.putString("fields","first_name,last_name,email,id");
        request.setParameters(parameters);
        request.executeAsync();
    }*/

    @Override
    public void onSucces(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgress() {
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.hide();
    }
}
