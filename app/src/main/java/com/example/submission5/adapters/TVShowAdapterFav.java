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

import java.util.List;

public class TVShowAdapterFav extends RecyclerView.Adapter<TVShowAdapterFav.ViewHolder> {
    private List<TVShowItems> tvShowItems;

    public TVShowAdapterFav(List<TVShowItems> tvShowItems) {
        this.tvShowItems = tvShowItems;
    }

    @NonNull
    @Override
    public TVShowAdapterFav.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_favorite, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TVShowAdapterFav.ViewHolder holder, int position) {
        TVShowItems tvShow = tvShowItems.get(position);
        holder.tvTitle.setText(tvShow.getTvTitle());
        holder.tvOverview.setText(tvShow.getTvOverview());
        holder.tvAiringDate.setText(tvShow.getTvReleasedate());
        Glide.with(holder.itemView.getContext())
                .load("https://image.tmdb.org/t/p/w185" + tvShow.getTvPosterpath())
                .apply(new RequestOptions().override(350, 550))
                .into(holder.imgPosterTvshow);
    }

    @Override
    public int getItemCount() {
        return tvShowItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvAiringDate, tvOverview;
        ImageView imgPosterTvshow;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tv_name);
            tvOverview = itemView.findViewById(R.id.tv_overview);
            tvAiringDate = itemView.findViewById(R.id.tv_release);
            imgPosterTvshow = itemView.findViewById(R.id.img_poster);
        }
    }
}
