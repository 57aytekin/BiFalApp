package com.example.bifal.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bifal.R;
import com.example.bifal.activity.anaSayfa.AnaSayfaActivity;

public class KahveHakkiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kahve_hakki);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(KahveHakkiActivity.this, AnaSayfaActivity.class));
        finish();
    }
}
