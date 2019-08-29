package com.example.bifal.activity.anaSayfa;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.bifal.R;
import com.example.bifal.activity.KrediAlActivity;
import com.example.bifal.activity.ProfileActivity;
import com.example.bifal.activity.fallarim.FallarimActivity;
import com.example.bifal.activity.kahveFali.KahveFaliActivity;
import com.example.bifal.manager.SessionManager;
import com.example.bifal.model.Fal;
import com.example.bifal.model.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class AnaSayfaActivity extends AppCompatActivity implements View.OnClickListener, AnaSayfaView {
    public Button btnKahveFali, btnFallarim, btnKredi, btnProfil;
    TextView tvUsername, tvCafeCount, tvCointCount;
    SessionManager sessionManager;
    AnaSayfaPresenter presenter;
    private SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.bifal.R.layout.activity_ana_sayfa);
        uiElement();
        btnKahveFali.setOnClickListener(this);
        btnFallarim.setOnClickListener(this);
        btnKredi.setOnClickListener(this);
        btnProfil.setOnClickListener(this);

        sessionManager = new SessionManager(getApplicationContext());
        HashMap<String, String> user = sessionManager.userDetail();
        String name = user.get(SessionManager.FIRST_NAME);
        final String web_id = user.get(SessionManager.USERID);

        presenter.getUser(web_id);
        tvUsername.setText(name);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getUser(web_id);
            }
        });

    }

    public void gunGecmisMi(String tarih, int id){
        SimpleDateFormat bicim = new SimpleDateFormat("dd.M.yyyy hh:mm:ss");

        if(tarih != null){
            try {
                Date date1 = bicim.parse(tarih);
                Date bugun = new Date();
                bicim.format(bugun);
                long difference = Math.abs(date1.getTime() - bugun.getTime());

                long gun = difference / (1000*60*60*24);
                if(gun >= 1){
                    presenter.update_gun_kontrol(id, 1);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

    }

    public void uiElement(){
        btnKahveFali = findViewById(R.id.btnKahveFali);
        btnFallarim = findViewById(R.id.btnFallarim);
        btnKredi = findViewById(R.id.btnKrediAl);
        btnProfil = findViewById(R.id.btnProfile);
        tvUsername = findViewById(R.id.tvAnaSayfaName);
        tvCafeCount = findViewById(R.id.tvCafeCount);
        tvCointCount = findViewById(R.id.tvCoinCount);
        swipeRefreshLayout = findViewById(R.id.swipe);
        presenter = new AnaSayfaPresenter(this);
    }

    private Boolean exit = false;
    @Override
    public void onBackPressed() {
        if (exit) {
            finish(); // finish activity
        } else {
            Toast.makeText(this, "Çıkmak için tekrar basın",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);

        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnKahveFali:
                startActivity(new Intent(AnaSayfaActivity.this, KahveFaliActivity.class));
                break;
            case R.id.btnFallarim:
                startActivity(new Intent(AnaSayfaActivity.this, FallarimActivity.class));
                break;
            case R.id.btnKrediAl:
                startActivity(new Intent(AnaSayfaActivity.this, KrediAlActivity.class));
                break;
            case R.id.btnProfile:
                startActivity(new Intent(AnaSayfaActivity.this, ProfileActivity.class));
                break;

        }
    }

    @Override
    public void onSuccess(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetResult(List<User> list) {

        String kave = String.valueOf(list.get(0).getKahve_hakki());
        tvCafeCount.setText(kave);
        float coin = list.get(0).getCoin();
        tvCointCount.setText(""+coin);
        String id = ""+list.get(0).getId();
        sessionManager.create_id(id);
        String tarih = list.get(0).getGun_kontrol();
        gunGecmisMi(tarih, list.get(0).getId());
    }

    @Override
    public void onGetFals(List<Fal> fals) {

    }

    @Override
    public void showReflesh() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideReflesh() {
        swipeRefreshLayout.setRefreshing(false);
    }
}
