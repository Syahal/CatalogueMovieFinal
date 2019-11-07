package com.example.submission5.components.movie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;

import com.example.submission5.R;
import com.example.submission5.adapters.MovieAdapterFav;
import com.example.submission5.connection.provider.LoadFavoriteMovieCallback;
import com.example.submission5.models.movies.MovieItems;

import java.lang.ref.WeakReference;
import java.util.List;

import static com.example.submission5.MainActivity.dbFavorite;
import static com.example.submission5.connection.DBContract.FavoriteMoviesColumn.CONTENT_URL;

public class MovieFavoriteActivity extends AppCompatActivity {
    private RecyclerView recyclerViewMovieFav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_favorite);
        setTitle(R.string.movie_fav);

        recyclerViewMovieFav = findViewById(R.id.rv_movies_fav);
        recyclerViewMovieFav.setLayoutManager(new LinearLayoutManager(this));

        getFavMovie();
    }

    private void getFavMovie() {
        List<MovieItems> movieItems = dbFavorite.dao().getFavMovie();
        MovieAdapterFav movieAdapterFav = new MovieAdapterFav(movieItems);
        recyclerViewMovieFav.setAdapter(movieAdapterFav);
    }

    public static class DataObserver extends ContentObserver {
        final Context context;

        public DataObserver(Handler handler, Context context) {
            super(handler);
            this.context = context;
        }

        public void onChange(boolean selfChanged) {
            super.onChange(selfChanged);
            new LoadMovieFavoriteAsync(context, (LoadFavoriteMovieCallback) context).execute();
        }
    }

    private static class LoadMovieFavoriteAsync extends AsyncTask<Void, Void, Cursor> {
        private final WeakReference<Context> contextWeakReference;

        private LoadMovieFavoriteAsync(Context context, LoadFavoriteMovieCallback loadFavoriteMovieCallback) {
            contextWeakReference = new WeakReference<>(context);
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            Context context = contextWeakReference.get();
            return context.getContentResolver().query(CONTENT_URL, null, null, null, null);
        }

        @Override
        protected void onPostExecute(Cursor movFav){

        }
    }
}
