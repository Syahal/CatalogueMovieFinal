package com.example.submission5.components.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;


import com.bumptech.glide.Glide;
import com.example.submission5.R;
import com.example.submission5.models.movies.MovieItems;

import java.util.List;

import static com.example.submission5.MainActivity.dbFavorite;

public class StackRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {
    private List<MovieItems> movieItems;
    private final Context mContext;

    StackRemoteViewFactory(Context context) {
        this.mContext = context;
    }

    @Override
    public void onCreate() {
        movieItems = dbFavorite.dao().getFavMovie();
    }

    @Override
    public void onDataSetChanged() {
        movieItems = dbFavorite.dao().getFavMovie();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return movieItems.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.list_widget);

        if (movieItems.size() > 0) {
            MovieItems items = movieItems.get(position);

            try {
                Bitmap bitmap = Glide.with(mContext)
                        .asBitmap()
                        .load("https://image.tmdb.org/t/p/w342" + items.getMoviePosterpath())
                        .submit()
                        .get();

                remoteViews.setImageViewBitmap(R.id.widget, bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }

            Bundle bundle = new Bundle();
            bundle.putString(FavoriteWidgetProvider.EXTRA_ITEM, items.getMovieTitle());
            Intent intent = new Intent();
            intent.putExtras(bundle);
            remoteViews.setOnClickFillInIntent(R.id.widget, intent);

            return remoteViews;
        } else {
            return null;
        }
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
