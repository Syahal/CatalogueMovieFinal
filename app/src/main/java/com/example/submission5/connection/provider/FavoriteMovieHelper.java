package com.example.submission5.connection.provider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.example.submission5.connection.DBHelper;
import com.example.submission5.models.movies.MovieItems;

import java.util.ArrayList;

import static com.example.submission5.connection.DBContract.FavoriteMoviesColumn.LANGUAGE;
import static com.example.submission5.connection.DBContract.FavoriteMoviesColumn.OVERVIEW;
import static com.example.submission5.connection.DBContract.FavoriteMoviesColumn.POPULARITY;
import static com.example.submission5.connection.DBContract.FavoriteMoviesColumn.POSTER_PATH;
import static com.example.submission5.connection.DBContract.FavoriteMoviesColumn.RELEASE_DATE;
import static com.example.submission5.connection.DBContract.FavoriteMoviesColumn.TABLE_NAME;
import static com.example.submission5.connection.DBContract.FavoriteMoviesColumn.TITLE;
import static com.example.submission5.connection.DBContract.FavoriteMoviesColumn.VOTE_AVERAGE;
import static com.example.submission5.connection.DBContract.FavoriteMoviesColumn._ID;

public class FavoriteMovieHelper {
    private static final String DATABASE_TABLE = TABLE_NAME;
    private final DBHelper dbHelper;
    private static FavoriteMovieHelper INSTANCE;
    private SQLiteDatabase sqLiteDatabase;

    private FavoriteMovieHelper(Context context) {
        dbHelper = new DBHelper(context);
    }

    public static FavoriteMovieHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FavoriteMovieHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        sqLiteDatabase = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();

        if (sqLiteDatabase.isOpen())
            sqLiteDatabase.close();
    }

    public ArrayList<FavoriteMovieItem> queryFav() {
        ArrayList<FavoriteMovieItem> movieItemsArrayList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query(DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null, _ID + " ASC",
                null);
        cursor.moveToFirst();
        FavoriteMovieItem favoriteMovieItem;

        if (cursor.getCount() > 0) {
            do {
                favoriteMovieItem = new FavoriteMovieItem();
                favoriteMovieItem.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                favoriteMovieItem.setMovieTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                favoriteMovieItem.setMovieOverview(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)));
                favoriteMovieItem.setMovieReleasedate(cursor.getString(cursor.getColumnIndexOrThrow(RELEASE_DATE)));
                favoriteMovieItem.setMovieVoteAvg(cursor.getString(cursor.getColumnIndexOrThrow(VOTE_AVERAGE)));
                favoriteMovieItem.setMoviePopularity(cursor.getString(cursor.getColumnIndexOrThrow(POPULARITY)));
                favoriteMovieItem.setMoviePosterpath(cursor.getString(cursor.getColumnIndexOrThrow(POSTER_PATH)));
                favoriteMovieItem.setMovieLanguage(cursor.getString(cursor.getColumnIndexOrThrow(LANGUAGE)));

                movieItemsArrayList.add(favoriteMovieItem);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return movieItemsArrayList;
    }

    public long insertItem(FavoriteMovieItem favoriteMovieItem) {
        ContentValues inputValues = new ContentValues();
        inputValues.put(TITLE, favoriteMovieItem.getMovieTitle());
        inputValues.put(OVERVIEW, favoriteMovieItem.getMovieOverview());
        inputValues.put(RELEASE_DATE, favoriteMovieItem.getMovieReleasedate());
        inputValues.put(VOTE_AVERAGE, favoriteMovieItem.getMovieVoteAvg());
        inputValues.put(POPULARITY, favoriteMovieItem.getMoviePopularity());
        inputValues.put(LANGUAGE, favoriteMovieItem.getMovieLanguage());
        inputValues.put(POSTER_PATH, favoriteMovieItem.getMoviePosterpath());

        return sqLiteDatabase.insert(DATABASE_TABLE, null, inputValues);
    }

    public int updateItem(FavoriteMovieItem favoriteMovieItem) {
        ContentValues updateValues = new ContentValues();
        updateValues.put(TITLE, favoriteMovieItem.getMovieTitle());
        updateValues.put(OVERVIEW, favoriteMovieItem.getMovieOverview());
        updateValues.put(RELEASE_DATE, favoriteMovieItem.getMovieReleasedate());
        updateValues.put(VOTE_AVERAGE, favoriteMovieItem.getMovieVoteAvg());
        updateValues.put(POPULARITY, favoriteMovieItem.getMoviePopularity());
        updateValues.put(LANGUAGE, favoriteMovieItem.getMovieLanguage());
        updateValues.put(POSTER_PATH, favoriteMovieItem.getMoviePosterpath());

        return sqLiteDatabase.update(DATABASE_TABLE, updateValues, _ID + " = '" + favoriteMovieItem.getId() + "'", null);
    }

    public int deleteItem(int id) {
        return sqLiteDatabase.delete(TABLE_NAME, _ID + " = '" + id + "'", null);
    }

    public Cursor queryById(String id) {
        return sqLiteDatabase.query(DATABASE_TABLE,
                null,
                _ID + " = ?",
                new String[]{id},
                null,
                null,
                null,
                null);
    }

    public Cursor queryProvider() {
        return sqLiteDatabase.query(DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                _ID + " DESC");
    }

    public long insertProvider(ContentValues values) {
        return sqLiteDatabase.insert(DATABASE_TABLE, null, values);
    }

    public int updateProvider(String id, ContentValues values) {
        return sqLiteDatabase.update(DATABASE_TABLE, values, _ID + " = ?", new String[]{id});
    }

    public int deleteProvider(String id) {
        return sqLiteDatabase.delete(DATABASE_TABLE, _ID + " = ?", new String[]{id});
    }
}
