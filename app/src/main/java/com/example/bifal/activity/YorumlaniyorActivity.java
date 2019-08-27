package com.example.bifal.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.bifal.R;
import com.example.bifal.activity.anaSayfa.AnaSayfaActivity;

public class YorumlaniyorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yorumlaniyor);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(YorumlaniyorActivity.this, AnaSayfaActivity.class));
        finish();
    }
}
