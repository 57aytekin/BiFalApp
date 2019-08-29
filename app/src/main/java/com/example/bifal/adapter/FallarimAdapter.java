package com.example.bifal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bifal.R;
import com.example.bifal.model.Fal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FallarimAdapter extends RecyclerView.Adapter<FallarimAdapter.MyViewHolder> {
    private Context context;
    private List<Fal> list;

    public FallarimAdapter(Context context, List<Fal> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater
                .from(viewGroup.getContext()).inflate(R.layout.fallarim_satir_gorunumu,viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        String tarih = list.get(i).getTarih();
        myViewHolder.tvTarih.setText(tarih);

        if(onBesDakikaGectiMi(tarih) > 15){
            //15dk geçmiş ise yorumlandı yazacan ve onClick olacak.
            myViewHolder.tvDurum.setText("Yorumlandı");

        }else{
            //15dk geçmemiş ise yorumlanıyor yazacak
            myViewHolder.tvDurum.setText("Yorumlanıyor...");
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTarih, tvDurum;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTarih = itemView.findViewById(R.id.tvSatirTarih);
            tvDurum = itemView.findViewById(R.id.tvSatirDurum);
        }
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
}
