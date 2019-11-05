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

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private ArrayList<MovieItems> movieItems;
    private OnItemClickCallback onItemClickCallback;

    public void setData(ArrayList<MovieItems> items) {
        movieItems.clear();
        movieItems.addAll(items);
        notifyDataSetChanged();
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public interface OnItemClickCallback {
        void onItemClicked(MovieItems movieData);
    }

    public MovieAdapter(ArrayList<MovieItems> movieItems) {
        this.movieItems = movieItems;
    }

    @NonNull
    @Override
    public MovieAdapter.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_movie, viewGroup, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieAdapter.MovieViewHolder holder, int position) {
        MovieItems movies = movieItems.get(position);
        holder.tvMovieTitle.setText(movies.getMovieTitle());
        holder.tvMovieRelease.setText(movies.getMovieReleasedate());
        holder.tvMovieOverview.setText(movies.getMovieOverview());
        Glide.with(holder.itemView.getContext())
                .load("https://image.tmdb.org/t/p/w185" + movies.getMoviePosterpath())
                .apply(new RequestOptions().override(350, 550))
                .into(holder.imgPosterMovie);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickCallback.onItemClicked(movieItems.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieItems.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView tvMovieTitle, tvMovieRelease, tvMovieOverview;
        ImageView imgPosterMovie;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMovieTitle = itemView.findViewById(R.id.tv_name);
            tvMovieRelease = itemView.findViewById(R.id.tv_release);
            tvMovieOverview = itemView.findViewById(R.id.tv_overview);
            imgPosterMovie = itemView.findViewById(R.id.img_poster);
        }
    }
}
