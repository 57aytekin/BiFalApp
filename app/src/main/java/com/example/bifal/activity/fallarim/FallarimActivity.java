package com.example.bifal.activity.fallarim;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bifal.R;
import com.example.bifal.activity.FalScreenActivity;
import com.example.bifal.adapter.FallarimAdapter;
import com.example.bifal.itemClick.RecyclerItemClickListener;
import com.example.bifal.manager.SessionManager;
import com.example.bifal.model.Fal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class FallarimActivity extends AppCompatActivity implements FallarimView {
    RecyclerView recyclerView;
    FallarimAdapter adapter;
    TextView tvFallarimText;
    SessionManager sessionManager;
    FallarimPresenter presenter;
    List<Fal> fals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fallarim);
        uiElement();

        HashMap<String, String> user = sessionManager.userDetail();
        String id = user.get(SessionManager.ID);
        int user_id = Integer.parseInt(id);
        presenter.getFallarim(user_id);

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        String tarih = fals.get(position).getTarih();
                        if(onBesDakikaGectiMi(tarih) > 15){
                            Intent intent = new Intent(FallarimActivity.this, FalScreenActivity.class);
                            String name = fals.get(position).getName();
                            String fal = fals.get(position).getFal();
                            intent.putExtra("name",name);
                            intent.putExtra("fal",fal);
                            startActivity(intent);
                        }else{
                            Toast.makeText(FallarimActivity.this, "Faliniz henüz yorumlanmadı", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                }));

    }

    public void uiElement(){
        presenter = new FallarimPresenter(this);
        recyclerView = findViewById(R.id.recyclerFallarim);
        tvFallarimText = findViewById(R.id.tvFallarimText);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        sessionManager = new SessionManager(this);
        fals = new ArrayList<>();
    }

    public int onBesDakikaGectiMi(String tarih){

        SimpleDateFormat bicim = new SimpleDateFormat("dd.M.yyyy hh:mm:ss");

        try {
            Date date1 = bicim.parse(tarih);
            Date bugun = new Date();
            bicim.format(bugun);
            long difference = Math.abs(date1.getTime() - bugun.getTime());
            long dakika = difference / (1000*60);

            return (int) dakika;

        } catch (ParseException e) {
            e.printStackTrace();
            return  0;
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
    public void onGetResult(List<Fal> list) {
        if(list.size() > 0){
            tvFallarimText.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }else{
            tvFallarimText.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
        adapter = new FallarimAdapter(this, list);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        fals = list;
    }
}
