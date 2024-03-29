package com.example.consumerfavorite;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import static android.provider.BaseColumns._ID;
import static com.example.consumerfavorite.DBContract.getColumnInt;
import static com.example.consumerfavorite.DBContract.getColumnString;

public class FavoriteMoviesItem implements Parcelable {
    private int id;
    private String movieTitle;
    private String movieOverview;
    private String movieReleasedate;
    private String movieVoteAvg;
    private String moviePopularity;
    private String movieLanguage;
    private String moviePosterpath;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getMovieOverview() {
        return movieOverview;
    }

    public void setMovieOverview(String movieOverview) {
        this.movieOverview = movieOverview;
    }

    public String getMovieReleasedate() {
        return movieReleasedate;
    }

    public void setMovieReleasedate(String movieReleasedate) {
        this.movieReleasedate = movieReleasedate;
    }

    public String getMovieVoteAvg() {
        return movieVoteAvg;
    }

    public void setMovieVoteAvg(String movieVoteAvg) {
        this.movieVoteAvg = movieVoteAvg;
    }

    public String getMoviePopularity() {
        return moviePopularity;
    }

    public void setMoviePopularity(String moviePopularity) {
        this.moviePopularity = moviePopularity;
    }

    public String getMovieLanguage() {
        return movieLanguage;
    }

    public void setMovieLanguage(String movieLanguage) {
        this.movieLanguage = movieLanguage;
    }

    public String getMoviePosterpath() {
        return moviePosterpath;
    }

    public void setMoviePosterpath(String moviePosterpath) {
        this.moviePosterpath = moviePosterpath;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(movieTitle);
        parcel.writeString(movieOverview);
        parcel.writeString(movieReleasedate);
        parcel.writeString(movieVoteAvg);
        parcel.writeString(moviePopularity);
        parcel.writeString(movieLanguage);
        parcel.writeString(moviePosterpath);
    }

    public FavoriteMoviesItem(int id, String title, String overview, String release, String vote, String popularity, String language, String posterpath) {
        this.id = id;
        this.movieTitle = title;
        this.movieOverview = overview;
        this.movieReleasedate = release;
        this.movieVoteAvg = vote;
        this.moviePopularity = popularity;
        this.movieLanguage = language;
        this.moviePosterpath = posterpath;
    }

    public FavoriteMoviesItem(Cursor cursor) {
        this.id = getColumnInt(cursor, _ID);
        this.movieTitle = getColumnString(cursor, DBContract.FavoriteMoviesColumn.TITLE);
        this.movieOverview = getColumnString(cursor, DBContract.FavoriteMoviesColumn.OVERVIEW);
        this.movieReleasedate = getColumnString(cursor, DBContract.FavoriteMoviesColumn.RELEASE_DATE);
        this.movieVoteAvg = getColumnString(cursor, DBContract.FavoriteMoviesColumn.VOTE_AVERAGE);
        this.moviePopularity = getColumnString(cursor, DBContract.FavoriteMoviesColumn.POPULARITY);
        this.movieLanguage = getColumnString(cursor, DBContract.FavoriteMoviesColumn.LANGUAGE);
        this.moviePosterpath = getColumnString(cursor, DBContract.FavoriteMoviesColumn.POSTER_PATH);
    }

    private FavoriteMoviesItem(Parcel in) {
        this.id = in.readInt();
        this.movieTitle = in.readString();
        this.movieOverview = in.readString();
        this.movieReleasedate = in.readString();
        this.movieVoteAvg = in.readString();
        this.moviePopularity = in.readString();
        this.movieLanguage = in.readString();
        this.moviePosterpath = in.readString();
    }

    public static final Creator<FavoriteMoviesItem> CREATOR = new Creator<FavoriteMoviesItem>() {
        @Override
        public FavoriteMoviesItem createFromParcel(Parcel in) {
            return new FavoriteMoviesItem(in);
        }

        @Override
        public FavoriteMoviesItem[] newArray(int size) {
            return new FavoriteMoviesItem[size];
        }
    };
}
