package com.abhijeet.ganpatiapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abhijeet.ganpatiapp.R;
import com.abhijeet.ganpatiapp.modelclass.SpotifyLinkModelClass;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class SpotifyLinkAdapter extends RecyclerView.Adapter<SpotifyLinkAdapter.ViewHolder> {

    private List<SpotifyLinkModelClass> dataList;

    public SpotifyLinkAdapter(List<SpotifyLinkModelClass> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public SpotifyLinkAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.spotify_link_item_design,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull SpotifyLinkAdapter.ViewHolder holder, int position) {

        String name = dataList.get(position).getName();
        String link = dataList.get(position).getSpotifyLink();

        holder.setData(name);


        holder.play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToUrl(v.getContext(), link);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private MaterialCardView play;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.aartiName);
            play = itemView.findViewById(R.id.playButton);

        }

        public void setData(String title){
            name.setText(title);
        }
    }

    public void goToUrl(Context context, String s){
        Uri uri = Uri.parse(s);
        context.startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }
}
