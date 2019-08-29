package com.example.bifal.activity.yorumlaniyor;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bifal.R;
import com.example.bifal.activity.anaSayfa.AnaSayfaActivity;

import java.util.Locale;

public class YorumlaniyorActivity extends AppCompatActivity {
    private static final long START_TIME_IN_MILIS = 1000000;

    private CountDownTimer mCountDownTimer;
    private long mTımeLeftInMilis = START_TIME_IN_MILIS;
    private TextView textView;
    private YorumlaniyorPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yorumlaniyor);
        textView = findViewById(R.id.minute);
        presenter = new YorumlaniyorPresenter();
        startTime();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(YorumlaniyorActivity.this, AnaSayfaActivity.class));
        finish();
    }
    private void startTime(){
        mCountDownTimer = new CountDownTimer(mTımeLeftInMilis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTımeLeftInMilis = millisUntilFinished;
                updateCountDown();
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }
    private void updateCountDown(){
        int minutes = (int) (mTımeLeftInMilis / 1000) / 60;
        int second = (int) (mTımeLeftInMilis/1000) %60;

        if(minutes == 0){
            System.out.println("TIME OUT ===> "+"Tıme 0 oldu");
            presenter.push_notifi(38);
            mCountDownTimer.cancel();
            mTımeLeftInMilis = START_TIME_IN_MILIS;
        }

        String timeFormat = String.format(Locale.getDefault(), "%02d:%02d", minutes, second);
        textView.setText(timeFormat);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong("milisLeft",mTımeLeftInMilis);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        mTımeLeftInMilis = savedInstanceState.getLong("milisLeft");
        updateCountDown();
    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);

        mTımeLeftInMilis = prefs.getLong("milisLeft", START_TIME_IN_MILIS);
        updateCountDown();


    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putLong("milisLeft", mTımeLeftInMilis);
        editor.apply();
    }
}
