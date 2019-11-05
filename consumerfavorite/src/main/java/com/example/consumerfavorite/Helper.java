package com.example.consumerfavorite;

import android.database.Cursor;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.example.consumerfavorite.DBContract.FavoriteMovieColumns.LANGUAGE;
import static com.example.consumerfavorite.DBContract.FavoriteMovieColumns.OVERVIEW;
import static com.example.consumerfavorite.DBContract.FavoriteMovieColumns.POPULARITY;
import static com.example.consumerfavorite.DBContract.FavoriteMovieColumns.POSTER_PATH;
import static com.example.consumerfavorite.DBContract.FavoriteMovieColumns.RELEASE_DATE;
import static com.example.consumerfavorite.DBContract.FavoriteMovieColumns.TITLE;
import static com.example.consumerfavorite.DBContract.FavoriteMovieColumns.VOTE_AVERAGE;

public class Helper {

    public static ArrayList<FavoriteMoviesItem> mapCursorToArrayList(Cursor cursor) {
        ArrayList<FavoriteMoviesItem> favoriteMoviesItemArrayList = new ArrayList<>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(_ID));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(TITLE));
                String overview = cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW));
                String releasseDate = cursor.getString(cursor.getColumnIndexOrThrow(RELEASE_DATE));
                String voteAverage = cursor.getString(cursor.getColumnIndexOrThrow(VOTE_AVERAGE));
                String popularity = cursor.getString(cursor.getColumnIndexOrThrow(POPULARITY));
                String language = cursor.getString(cursor.getColumnIndexOrThrow(LANGUAGE));
                String posterPath = cursor.getString(cursor.getColumnIndexOrThrow(POSTER_PATH));

                favoriteMoviesItemArrayList.add(new FavoriteMoviesItem(id, title, overview, releasseDate, voteAverage, popularity, language, posterPath));
            }
        }
        return favoriteMoviesItemArrayList;
    }
}
