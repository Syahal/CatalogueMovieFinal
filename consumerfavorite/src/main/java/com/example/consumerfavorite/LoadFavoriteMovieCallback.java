package com.example.consumerfavorite;

import android.database.Cursor;

interface LoadFavoriteMovieCallback {
    void postExecute(Cursor notes);
}
