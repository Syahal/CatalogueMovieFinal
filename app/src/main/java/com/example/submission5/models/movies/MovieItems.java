package com.example.submission5.models.movies;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.json.JSONObject;

@Entity(tableName = "movie_favorite")
public class MovieItems implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "movieTitle")
    private String movieTitle;
    @ColumnInfo(name =  "movieOverview")
    private String movieOverview;
    @ColumnInfo(name = "movieReleasedate")
    private String movieReleasedate;
    @ColumnInfo(name = "movieVoteAvg")
    private String movieVoteAvg;
    @ColumnInfo(name = "moviePopularity")
    private String moviePopularity;
    @ColumnInfo(name = "movieLanguage")
    private String movieLanguage;
    @ColumnInfo(name = "moviePosterpath")
    private String moviePosterpath;

    public MovieItems() {

    }

    MovieItems(JSONObject object) {
        try {
            int id = object.getInt("id");
            String movieTitle = object.getString("title");
            String movieOverview = object.getString("overview");
            String movieReleasedate = object.getString("release_date");
            String movieVoteAvg = object.getString("vote_average");
            String moviePopularity = object.getString("popularity");
            String movieLanguage = object.getString("original_language");
            String moviePosterpath = object.getString("poster_path");

            this.id = id;
            this.movieTitle = movieTitle;
            this.movieOverview = movieOverview;
            this.movieReleasedate = movieReleasedate;
            this.movieVoteAvg = movieVoteAvg;
            this.moviePopularity = moviePopularity;
            this.movieLanguage = movieLanguage;
            this.moviePosterpath = moviePosterpath;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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

    protected MovieItems(Parcel in) {
        id = in.readInt();
        movieTitle = in.readString();
        movieOverview = in.readString();
        movieReleasedate = in.readString();
        movieVoteAvg = in.readString();
        moviePopularity = in.readString();
        movieLanguage = in.readString();
        moviePosterpath = in.readString();
    }

    public static final Creator<MovieItems> CREATOR = new Creator<MovieItems>() {
        @Override
        public MovieItems createFromParcel(Parcel in) {
            return new MovieItems(in);
        }

        @Override
        public MovieItems[] newArray(int size) {
            return new MovieItems[size];
        }
    };
}
