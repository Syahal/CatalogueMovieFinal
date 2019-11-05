package com.example.submission5.connection;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.submission5.models.movies.MovieItems;
import com.example.submission5.models.tvshows.TVShowItems;

@Database(entities = {MovieItems.class, TVShowItems.class}, version = 1)
public abstract class DBFavorite extends RoomDatabase {
    public abstract DAO dao();
}
