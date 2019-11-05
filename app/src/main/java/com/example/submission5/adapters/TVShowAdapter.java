package com.example.submission5.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.submission5.R;
import com.example.submission5.models.tvshows.TVShowItems;

import java.util.ArrayList;

public class TVShowAdapter extends RecyclerView.Adapter<TVShowAdapter.TVShowViewHolder> {
    private ArrayList<TVShowItems> tvShowItems;
    private OnItemClickCallback onItemClickCallback;

    public void setData(ArrayList<TVShowItems> items) {
        tvShowItems.clear();
        tvShowItems.addAll(items);
        notifyDataSetChanged();
    }

    public void setOnItemClickCallback(TVShowAdapter.OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public interface OnItemClickCallback {
        void onItemClicked(TVShowItems tvData);
    }

    public TVShowAdapter(ArrayList<TVShowItems> tvShowItems) {
        this.tvShowItems = tvShowItems;
    }

    @NonNull
    @Override
    public TVShowAdapter.TVShowViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_tvshow, viewGroup, false);
        return new TVShowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TVShowAdapter.TVShowViewHolder holder, int position) {
        TVShowItems tvshows = tvShowItems.get(position);
        holder.tvTvshowTitle.setText(tvshows.getTvTitle());
        holder.tvTvshowRelease.setText(tvshows.getTvReleasedate());
        holder.tvTvshowOverview.setText(tvshows.getTvOverview());
        Glide.with(holder.itemView.getContext())
                .load("https://image.tmdb.org/t/p/w185" + tvshows.getTvPosterpath())
                .apply(new RequestOptions().override(350, 550))
                .into(holder.imgPosterTvshow);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickCallback.onItemClicked(tvShowItems.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return tvShowItems.size();
    }

    public class TVShowViewHolder extends RecyclerView.ViewHolder {
        TextView tvTvshowTitle, tvTvshowRelease, tvTvshowOverview;
        ImageView imgPosterTvshow;

        public TVShowViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTvshowTitle = itemView.findViewById(R.id.tv_name);
            tvTvshowRelease = itemView.findViewById(R.id.tv_release);
            tvTvshowOverview = itemView.findViewById(R.id.tv_overview);
            imgPosterTvshow = itemView.findViewById(R.id.img_poster);
        }
    }
}
