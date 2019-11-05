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
import com.example.submission5.models.movies.MovieItems;

import java.util.List;

public class MovieAdapterFav extends RecyclerView.Adapter<MovieAdapterFav.ViewHolder> {
    private List<MovieItems> movieItems;

    public MovieAdapterFav(List<MovieItems> movieItems) {
        this.movieItems = movieItems;
    }

    @NonNull
    @Override
    public MovieAdapterFav.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_favorite, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapterFav.ViewHolder holder, int position) {
        MovieItems movies = movieItems.get(position);
        holder.tvMovieTitle.setText(movies.getMovieTitle());
        holder.tvMovieOverview.setText(movies.getMovieOverview());
        holder.tvMovieRelease.setText(movies.getMovieReleasedate());
        Glide.with(holder.itemView.getContext())
                .load("https://image.tmdb.org/t/p/w185" + movies.getMoviePosterpath())
                .apply(new RequestOptions().override(350, 550))
                .into(holder.imgPosterMovie);
    }

    @Override
    public int getItemCount() {
        return movieItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvMovieTitle, tvMovieRelease, tvMovieOverview;
        ImageView imgPosterMovie;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMovieTitle = itemView.findViewById(R.id.tv_name);
            tvMovieRelease = itemView.findViewById(R.id.tv_release);
            tvMovieOverview = itemView.findViewById(R.id.tv_overview);
            imgPosterMovie = itemView.findViewById(R.id.img_poster);
        }
    }
}
