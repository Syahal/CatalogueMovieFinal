package com.example.submission5.connection.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;

import androidx.annotation.NonNull;

import com.example.submission5.components.movie.MovieFavoriteActivity;

import static com.example.submission5.connection.DBContract.AUTHORITY;
import static com.example.submission5.connection.DBContract.FavoriteMoviesColumn.CONTENT_URL;
import static com.example.submission5.connection.DBContract.FavoriteMoviesColumn.TABLE_NAME;

public class FavoriteMovieProvider extends ContentProvider {
    private static final int NOTE_1 = 1;
    private static final int NOTE_2 = 2;
    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
    private FavoriteMovieHelper favoriteMovieHelper;

    static {
        URI_MATCHER.addURI(AUTHORITY, TABLE_NAME, NOTE_1);
        URI_MATCHER.addURI(AUTHORITY, TABLE_NAME + "/#", NOTE_2);
    }

    @Override
    public boolean onCreate() {
        favoriteMovieHelper = FavoriteMovieHelper.getInstance(getContext());
        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] strings, String s, String[] strings1, String s1) {
        favoriteMovieHelper.open();
        Cursor cursor;
        switch (URI_MATCHER.match(uri)) {
            case NOTE_1:
                cursor = favoriteMovieHelper.queryProvider();
                break;
            case NOTE_2:
                cursor = favoriteMovieHelper.queryById(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }
        return cursor;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues contentValues) {
        favoriteMovieHelper.open();
        long added;
        switch (URI_MATCHER.match(uri)) {
            case NOTE_1:
                added = favoriteMovieHelper.insertProvider(contentValues);
                break;
            default:
                added = 0;
                break;
        }
        getContext().getContentResolver().notifyChange(CONTENT_URL, new MovieFavoriteActivity.DataObserver(new Handler(), getContext()));
        return Uri.parse(CONTENT_URL + "/" + added);
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues contentValues, String s, String[] strings) {
        favoriteMovieHelper.open();
        int updated;
        switch (URI_MATCHER.match(uri)) {
            case NOTE_2:
                updated = favoriteMovieHelper.updateProvider(uri.getLastPathSegment(), contentValues);
                break;

            default:
                updated = 0;
                break;
        }
        getContext().getContentResolver().notifyChange(CONTENT_URL, new MovieFavoriteActivity.DataObserver(new Handler(), getContext()));
        return updated;
    }

    @Override
    public int delete(@NonNull Uri uri, String s, String[] strings) {
        favoriteMovieHelper.open();
        int deleted;
        switch (URI_MATCHER.match(uri)) {
            case NOTE_2:
                deleted = favoriteMovieHelper.deleteProvider(uri.getLastPathSegment());
                break;

            default:
                deleted = 0;
                break;
        }
        getContext().getContentResolver().notifyChange(CONTENT_URL, new MovieFavoriteActivity.DataObserver(new Handler(), getContext()));
        return deleted;
    }
}
