package com.example.submission5.models.tvshows;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.json.JSONObject;

@Entity(tableName = "tvshow_favorite")
public class TVShowItems implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "tvTitle")
    private String tvTitle;
    @ColumnInfo(name = "tvOverview")
    private String tvOverview;
    @ColumnInfo(name = "tvReleasedate")
    private String tvReleasedate;
    @ColumnInfo(name = "tvVoteAvg")
    private String tvVoteAvg;
    @ColumnInfo(name = "tvPopularity")
    private String tvPopularity;
    @ColumnInfo(name = "tvLanguage")
    private String tvLanguage;
    @ColumnInfo(name = "tvPosterpath")
    private String tvPosterpath;

    public TVShowItems() {

    }

    TVShowItems(JSONObject object) {
        try {
            int id = object.getInt("id");
            String tvTitle = object.getString("name");
            String tvOverview = object.getString("overview");
            String tvReleasedate = object.getString("first_air_date");
            String tvVoteAvg = object.getString("vote_average");
            String tvPopularity = object.getString("popularity");
            String tvLanguage = object.getString("original_language");
            String tvPosterpath = object.getString("poster_path");

            this.id = id;
            this.tvTitle = tvTitle;
            this.tvOverview = tvOverview;
            this.tvReleasedate = tvReleasedate;
            this.tvVoteAvg = tvVoteAvg;
            this.tvPopularity = tvPopularity;
            this.tvLanguage = tvLanguage;
            this.tvPosterpath = tvPosterpath;

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

    public String getTvTitle() {
        return tvTitle;
    }

    public void setTvTitle(String tvTitle) {
        this.tvTitle = tvTitle;
    }

    public String getTvOverview() {
        return tvOverview;
    }

    public void setTvOverview(String tvOverview) {
        this.tvOverview = tvOverview;
    }

    public String getTvReleasedate() {
        return tvReleasedate;
    }

    public void setTvReleasedate(String tvReleasedate) {
        this.tvReleasedate = tvReleasedate;
    }

    public String getTvVoteAvg() {
        return tvVoteAvg;
    }

    public void setTvVoteAvg(String tvVoteAvg) {
        this.tvVoteAvg = tvVoteAvg;
    }

    public String getTvPopularity() {
        return tvPopularity;
    }

    public void setTvPopularity(String tvPopularity) {
        this.tvPopularity = tvPopularity;
    }

    public String getTvLanguage() {
        return tvLanguage;
    }

    public void setTvLanguage(String tvLanguage) {
        this.tvLanguage = tvLanguage;
    }

    public String getTvPosterpath() {
        return tvPosterpath;
    }

    public void setTvPosterpath(String tvPosterpath) {
        this.tvPosterpath = tvPosterpath;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(tvTitle);
        parcel.writeString(tvOverview);
        parcel.writeString(tvReleasedate);
        parcel.writeString(tvVoteAvg);
        parcel.writeString(tvPopularity);
        parcel.writeString(tvLanguage);
        parcel.writeString(tvPosterpath);
    }

    protected TVShowItems(Parcel in) {
        id = in.readInt();
        tvTitle = in.readString();
        tvOverview = in.readString();
        tvReleasedate = in.readString();
        tvVoteAvg = in.readString();
        tvPopularity = in.readString();
        tvLanguage = in.readString();
        tvPosterpath = in.readString();
    }

    public static final Creator<TVShowItems> CREATOR = new Creator<TVShowItems>() {
        @Override
        public TVShowItems createFromParcel(Parcel in) {
            return new TVShowItems(in);
        }

        @Override
        public TVShowItems[] newArray(int size) {
            return new TVShowItems[size];
        }
    };
}
