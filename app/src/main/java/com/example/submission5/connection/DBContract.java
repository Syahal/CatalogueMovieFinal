package com.example.submission5.connection;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DBContract {

    public static final String AUTHORITY = "com.example.submission5";
    private static final String SCHEME = "content";

    public DBContract() {
    }

    public static final class FavoriteMoviesColumn implements BaseColumns {
        public static final String TABLE_NAME = "favorite_movie";
        public static final String _ID = "_id";
        public static final String TITLE = "title";
        public static final String RELEASE_DATE = "release_date";
        public static final String VOTE_AVERAGE = "vote_average";
        public static final String POPULARITY = "popularity";
        public static final String OVERVIEW = "overview";
        public static final String LANGUAGE = "original_language";
        public static final String POSTER_PATH = "poster_path";

        public static final Uri CONTENT_URL = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build();
    }

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }
}
