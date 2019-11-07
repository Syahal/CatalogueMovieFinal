package com.example.submission5.connection.provider;

import java.util.ArrayList;

public interface LoadFavoriteMovieCallback {
    void preExecute();

    void postExecute(ArrayList<FavoriteMovieItem> favoriteMovieItems);
}
