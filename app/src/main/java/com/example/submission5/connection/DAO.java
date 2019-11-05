package com.example.submission5.connection;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.submission5.models.movies.MovieItems;
import com.example.submission5.models.tvshows.TVShowItems;

import java.util.List;

@Dao
public interface DAO {

    @Insert void addMovieItem(MovieItems movieItems);
    @Query("SELECT * FROM movie_favorite") List<MovieItems> getFavMovie();
    @Query("SELECT EXISTS (SELECT 1 FROM movie_favorite WHERE id=:id)") int isFavMov(int id);
    @Delete void delete(MovieItems movieItems);

    @Insert void addTvItem(TVShowItems tvShowItems);
    @Query("SELECT * FROM tvshow_favorite") List<TVShowItems> getFavTv();
    @Query("SELECT EXISTS (SELECT 1 FROM tvshow_favorite WHERE id=:id)") int isFavTv(int id);
    @Delete void delete(TVShowItems tvShowItems);
}
