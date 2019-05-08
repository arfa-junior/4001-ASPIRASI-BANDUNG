package com.example.aspirasilapor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class AdapterLapor extends RecyclerView.Adapter<AdapterLapor.ViewHolder> {
    private List<CardLapor> daftarChat;
    private Context mContext;

    public AdapterLapor(List<CardLapor> daftarChat, Context mContext) {
        this.daftarChat = daftarChat;
        this.mContext = mContext;

    }
//
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AdapterLapor.ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.tampilanlapor, parent, false)) {
        };
    }


    @Override
    public void onBindViewHolder(AdapterLapor.ViewHolder holder, int position) {
        CardLapor card = daftarChat.get(position);
        holder.bindTo(card);

    }

    @Override
    public int getItemCount() {
        return daftarChat.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView kategori, deskripsi;
        private ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            kategori = itemView.findViewById(R.id.kategori);
            deskripsi = itemView.findViewById(R.id.deskripsi);
            image = itemView.findViewById(R.id.cardImg);


            itemView.setOnClickListener(this);
        }

        @SuppressLint("StaticFieldLeak")
        public void bindTo(final CardLapor card) {
            kategori.setText(card.getkategori());
            deskripsi.setText(card.getdeskripsi());


        }

        @Override
        public void onClick(View v) {
            Toast.makeText(mContext, kategori.getText().toString(), Toast.LENGTH_SHORT).show();
        }
    }
}