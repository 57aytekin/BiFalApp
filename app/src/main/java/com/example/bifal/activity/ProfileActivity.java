package com.example.bifal.activity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bifal.R;
import com.example.bifal.activity.main.MainActivity;
import com.example.bifal.manager.SessionManager;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {
    private SessionManager sessionManager;
    private CircleImageView ivProfilePhoto;
    private ImageView ivPencil;
    private EditText etAd, etSoyad, etGmail;
    private Button btnGuncelle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        uiElement();
        sessionManager = new SessionManager(getApplicationContext());
        HashMap<String, String > user = sessionManager.userDetail();
        etAd.setText(user.get(sessionManager.FIRST_NAME));
        etSoyad.setText(user.get(sessionManager.LAST_NAME));
        etGmail.setText(user.get(sessionManager.EMAIL));
        Picasso.get().load(user.get(sessionManager.USERPHOTO)).into(ivProfilePhoto);

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    public void uiElement(){
        final Drawable backArrow = getResources().getDrawable(R.drawable.ic_back_white);
        backArrow.setColorFilter(getResources().getColor(R.color.colorWhite), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(backArrow);

        ivProfilePhoto = findViewById(R.id.ivProfileResim);
        ivPencil = findViewById(R.id.ivProfilePencil);
        etAd = findViewById(R.id.etProfileAd);
        etSoyad = findViewById(R.id.etProfileSoyad);
        etGmail = findViewById(R.id.etProfileGmail);
        btnGuncelle = findViewById(R.id.btnProfileGuncelle);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.profile_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_ayar:
                Toast.makeText(this, "Ayar sayfası", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_cikis:
                //cikiş
                Toast.makeText(this, "Çıkış yapıldi", Toast.LENGTH_SHORT).show();
                googleLogout();
                sessionManager.logOut();
                break;
        }
        return true;
    }

    public void googleLogout(){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        startActivity(new Intent(ProfileActivity.this, MainActivity.class));
                    }
                });

    }


}
