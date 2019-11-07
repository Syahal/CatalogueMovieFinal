package com.example.submission5.connection;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "db_favorite_movies";

    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_FAV = String.format("CREATE TABLE %s "
                    + "(%s INTEGER PRIMARY KEY NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            DBContract.FavoriteMoviesColumn.TABLE_NAME,
            DBContract.FavoriteMoviesColumn._ID,
            DBContract.FavoriteMoviesColumn.TITLE,
            DBContract.FavoriteMoviesColumn.RELEASE_DATE,
            DBContract.FavoriteMoviesColumn.VOTE_AVERAGE,
            DBContract.FavoriteMoviesColumn.POPULARITY,
            DBContract.FavoriteMoviesColumn.OVERVIEW,
            DBContract.FavoriteMoviesColumn.LANGUAGE,
            DBContract.FavoriteMoviesColumn.POSTER_PATH
    );

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_FAV);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DBContract.FavoriteMoviesColumn.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
