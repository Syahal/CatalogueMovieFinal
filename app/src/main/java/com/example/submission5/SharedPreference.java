package com.example.submission5;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreference {

    private static final  String SUBMISSION_5 = "MOVIE_CATALOGUE";

    public static final String MOVIE_TITLE = "MOVIE_TITLE";
    public static final String DAILY_REMINDER_STATUS = "DAILY_REMINDER_STATUS";
    public static final String NEW_RELEASE_REMINDER_STATUS = "NEW_RELEASE_REMINDER_STATUS";

    private final SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public SharedPreference(Context context) {
        sharedPreferences = context.getSharedPreferences(SUBMISSION_5, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveBoolean(String keySP, boolean value) {
        editor.putBoolean(keySP, value);
        editor.commit();
    }

    public String getNewReleaseMovieTitle() {
        return sharedPreferences.getString(MOVIE_TITLE, "");

    }

    public Boolean getStatusDailyReminder() {
        return sharedPreferences.getBoolean(DAILY_REMINDER_STATUS, false);
    }

    public Boolean getStatusNewReleaseReminder() {
        return sharedPreferences.getBoolean(NEW_RELEASE_REMINDER_STATUS, false);
    }
}
