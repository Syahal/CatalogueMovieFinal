package com.example.consumerfavorite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.example.consumerfavorite.DBContract.FavoriteMoviesColumn.CONTENT_URL;
import static com.example.consumerfavorite.Helper.mapCursorToArrayList;

public class MainActivity extends AppCompatActivity implements LoadFavoriteMovieCallback {

    private ConsumerAdapter consumerAdapter;
    private DataObserver dataObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Consumer App");
        RecyclerView recyclerViewFavMovie = findViewById(R.id.rv_movies_fav);

        consumerAdapter = new ConsumerAdapter(this);

        recyclerViewFavMovie.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewFavMovie.setHasFixedSize(true);
        recyclerViewFavMovie.setAdapter(consumerAdapter);

        HandlerThread handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();

        Handler handler = new Handler(handlerThread.getLooper());

        dataObserver = new DataObserver(handler, this);
        getContentResolver().registerContentObserver(CONTENT_URL, true, dataObserver);
        new getData(this, this).execute();
    }

    @Override
    public void postExecute(Cursor notes) {
        ArrayList<FavoriteMoviesItem> favoriteMoviesItems = mapCursorToArrayList(notes);
        if (favoriteMoviesItems.size() > 0) {
            consumerAdapter.setFavoriteMoviesItemArrayList(favoriteMoviesItems);
        } else {
            Toast.makeText(this, "Tidak ada Data saat ini", Toast.LENGTH_SHORT).show();
            consumerAdapter.setFavoriteMoviesItemArrayList(new ArrayList<FavoriteMoviesItem>());
        }
    }

    private static class getData extends AsyncTask<Void, Void, Cursor> {
        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadFavoriteMovieCallback> weakCallback;

        private getData(Context context, LoadFavoriteMovieCallback loadFavoriteMovieCallback) {
            weakContext = new WeakReference<>(context);
            weakCallback  = new WeakReference<>(loadFavoriteMovieCallback);
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return weakContext.get().getContentResolver().query(CONTENT_URL, null, null, null, null);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            weakCallback.get().postExecute(cursor);
        }
    }

    static class DataObserver extends ContentObserver {
        final Context context;

        DataObserver(Handler handler, Context context) {
            super(handler);
            this.context = context;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            new getData(context, (MainActivity) context).execute();
        }
    }
}
