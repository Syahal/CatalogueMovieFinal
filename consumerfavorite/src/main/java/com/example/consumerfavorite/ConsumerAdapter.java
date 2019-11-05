package com.example.consumerfavorite;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class ConsumerAdapter extends RecyclerView.Adapter<ConsumerAdapter.FavoriteMovieViewHolder> {
    private final ArrayList<FavoriteMoviesItem> favoriteMoviesItemArrayList = new ArrayList<>();
    private final Activity activity;

    public ConsumerAdapter(Activity activity) {
        this.activity = activity;
    }

    public ArrayList<FavoriteMoviesItem> getFavoriteMoviesItemArrayList() {
        return favoriteMoviesItemArrayList;
    }

    public void setFavoriteMoviesItemArrayList(ArrayList<FavoriteMoviesItem> favoriteMoviesItemArrayList) {
        this.favoriteMoviesItemArrayList.clear();
        this.favoriteMoviesItemArrayList.addAll(favoriteMoviesItemArrayList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ConsumerAdapter.FavoriteMovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_favorite, viewGroup, false);
        return new FavoriteMovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ConsumerAdapter.FavoriteMovieViewHolder holder, int i) {
        holder.tvMovieTitle.setText(getFavoriteMoviesItemArrayList().get(i).getMovieTitle());
        holder.tvMovieOverview.setText(getFavoriteMoviesItemArrayList().get(i).getMovieOverview());
        holder.tvMovieRelease.setText(getFavoriteMoviesItemArrayList().get(i).getMovieReleasedate());
        Glide.with(holder.itemView.getContext())
                .load("https://image.tmdb.org/t/p/w185" + getFavoriteMoviesItemArrayList().get(i).getMoviePosterpath())
                .apply(new RequestOptions().override(350, 550))
                .into(holder.imgPosterMovie);
    }

    @Override
    public int getItemCount() {
        return favoriteMoviesItemArrayList.size();
    }

    public class FavoriteMovieViewHolder extends RecyclerView.ViewHolder {
        TextView tvMovieTitle, tvMovieRelease, tvMovieOverview;
        ImageView imgPosterMovie;

        public FavoriteMovieViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMovieTitle = itemView.findViewById(R.id.tv_movie_name);
            tvMovieRelease = itemView.findViewById(R.id.tv_movie_release);
            tvMovieOverview = itemView.findViewById(R.id.tv_movie_overview);
            imgPosterMovie = itemView.findViewById(R.id.img_movie_poster);
        }
    }
}
