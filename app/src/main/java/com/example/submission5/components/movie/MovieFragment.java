package com.example.submission5.components.movie;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.example.submission5.R;
import com.example.submission5.adapters.MovieAdapter;
import com.example.submission5.models.movies.MovieItems;
import com.example.submission5.models.movies.MovieViewModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {
    private ProgressBar progressBar;
    private ArrayList<MovieItems> movieList = new ArrayList<>();
    private MovieAdapter movieAdapter;
    private MovieViewModel movieViewModel;

    public MovieFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        progressBar = view.findViewById(R.id.progress_bar);
        RecyclerView recyclerViewMovie = view.findViewById(R.id.rv_movies);
        final EditText edtSearchMovie = view.findViewById(R.id.movie_search);
        ImageButton btnSearchMovie = view.findViewById(R.id.btn_search_movie);

        movieViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MovieViewModel.class);
        movieViewModel.getMovies().observe(this, getMovies);

        movieAdapter = new MovieAdapter(movieList);
        movieAdapter.notifyDataSetChanged();

        recyclerViewMovie.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewMovie.setAdapter(movieAdapter);

        movieViewModel.setMovies(getContext());

        movieAdapter.setOnItemClickCallback(new MovieAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(MovieItems movieData) {
                Intent movieIntent = new Intent(getContext(), MovieDetailActivity.class);
                movieIntent.putExtra(MovieDetailActivity.MOVIE_EXTRA, movieData);
                startActivity(movieIntent);
            }
        });

        btnSearchMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoading(true);
                String querySearchMovie = edtSearchMovie.getText().toString();

                if (querySearchMovie.length() > 0) {
                    movieViewModel.searchMovie(getContext(), querySearchMovie);
                } else {
                    movieViewModel.setMovies(getContext());
                }
            }
        });
    }

    private Observer<ArrayList<MovieItems>> getMovies = new Observer<ArrayList<MovieItems>>() {
        @Override
        public void onChanged(ArrayList<MovieItems> movieItems) {
            showLoading(true);

            if (movieItems != null) {
                movieAdapter.setData(movieItems);
                showLoading(false);
            }
        }
    };

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}
