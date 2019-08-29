package com.example.bifal.activity.main;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bifal.activity.anaSayfa.AnaSayfaActivity;
import com.example.bifal.manager.SessionManager;
import com.example.bifal.model.User;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.tomer.fadingtextview.FadingTextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainView {
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

    private int coin = 0, kahve_hakki = 1;
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

        if (checkBox.isChecked()) {
            btnSignIngoogle.setClickable(true);
        }
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    btnSignIngoogle.setClickable(true);
                } else {
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

    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void animasyonluGetir() {
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

                String sozlesme = getString(com.example.bifal.R.string.giris_one) + " " + "<i>" + getString(com.example.bifal.R.string.giris_two) + "</i>" + " " + getString(com.example.bifal.R.string.giris_three);
                String s = "Kulanım ve Gizlilik Koşulların için " + "<i>" + "tıklayınız.." + "</i>";
                tvGirisSozlesmesi.setText(Html.fromHtml(s));
                tvGirisSozlesmesi.setAnimation(fromBottomIcon);
                checkBox.setAnimation(fromBottomIcon);
                checkBox.setText(Html.fromHtml(sozlesme));

                Animation animation = new TranslateAnimation(0, 0, 0, -300);
                animation.setDuration(1000);
                animation.setFillAfter(true);
                ivLogo.startAnimation(animation);
            }
        }, 10000);
    }

    public void uiElement() {
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
        if (requestCode == RC_SIGN_IN) {
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

            if (account.getPhotoUrl() == null) {
                photo_url = "http://aytekincomezz.000webhostapp.com/YeniApp/image/default.jpg";
            } else {
                photo_url = account.getPhotoUrl().toString();
            }
            //presenter.saveUser(id,first_name, last_name, email, kahve_hakki, coin);
            Log.d("LOG", "hadleSignInResult çalıstıi");

            sessionManager.createSession(first_name, last_name, email, id, photo_url);
            startActivity(new Intent(MainActivity.this, AnaSayfaActivity.class));
            finish();

        } catch (ApiException e) {
            Log.w("Google Sign In Error", "signInResult:failed code=" + e.getStatusCode());
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            String photo = "";

            String first_name = account.getGivenName();
            String last_name = account.getFamilyName();
            String email = account.getEmail();
            String id = account.getId();
            /*if (account.getPhotoUrl().toString() == null) {
                photo = "http://aytekincomezz.000webhostapp.com/YeniApp/image/default.jpg";
            } else {
                photo = account.getPhotoUrl().toString();
            }
            //presenter.saveUser(id, first_name, last_name, email, kahve_hakki, coin);
            sessionManager.createSession(first_name, last_name, email, id, photo);
           // presenter.getUser(id);*/

            Log.d("LOG", "OnStart metodu calisti");
            startActivity(new Intent(MainActivity.this, AnaSayfaActivity.class));
            finish();
        }
        super.onStart();
    }


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

    @Override
    public void onGetResult(List<User> getUser) {

    }
}
