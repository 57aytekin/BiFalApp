package com.example.bifal.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.bifal.R;

public class FalScreenActivity extends AppCompatActivity {
    public TextView tvName, tvFal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fal_screen);
        tvName = findViewById(R.id.tvNameFalScreen);
        tvFal = findViewById(R.id.tvFalScreen);

        String name = getIntent().getStringExtra("name");
        String fal = getIntent().getStringExtra("fal");

        tvName.setText(name);
        tvFal.setText(fal);
    }
}
