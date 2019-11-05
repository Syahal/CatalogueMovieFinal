package com.example.submission5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;

import com.example.submission5.components.movie.MovieFavoriteActivity;
import com.example.submission5.components.movie.MovieFragment;
import com.example.submission5.components.reminder.ReminderActivity;
import com.example.submission5.components.tvshow.TVShowFavoriteActivity;
import com.example.submission5.components.tvshow.TVShowFragment;
import com.example.submission5.connection.DBFavorite;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    public static DBFavorite dbFavorite;

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment fragment;

            switch (menuItem.getItemId()) {
                case R.id.movie_nav:
                    fragment = new MovieFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_layout, fragment, fragment.getClass().getSimpleName())
                            .commit();
                    return true;

                case R.id.tv_nav:
                    fragment = new TVShowFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_layout, fragment, fragment.getClass().getSimpleName())
                            .commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.app_name);

        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_menu);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

        if (savedInstanceState == null) {
            bottomNavigationView.setSelectedItemId(R.id.movie_nav);
        }

        dbFavorite = Room.databaseBuilder(getApplicationContext(), DBFavorite.class, "favorite_db").allowMainThreadQueries().build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.action_movie_fav:
                Intent favMovie = new Intent(this, MovieFavoriteActivity.class);
                startActivity(favMovie);
                break;

            case R.id.action_tvshow_fav:
                Intent favTvshow = new Intent(this, TVShowFavoriteActivity.class);
                startActivity(favTvshow);
                break;

            case R.id.action_language_set:
                Intent setLanguage = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(setLanguage);
                break;

            case R.id.action_reminder_set:
                Intent setReminder = new Intent(this, ReminderActivity.class);
                startActivity(setReminder);
                break;
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
