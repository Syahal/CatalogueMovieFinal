package com.example.submission5.components.movie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.submission5.R;
import com.example.submission5.components.widget.FavoriteWidgetProvider;
import com.example.submission5.models.movies.MovieItems;

import jp.wasabeef.glide.transformations.BlurTransformation;

import static com.example.submission5.connection.DBContract.FavoriteMoviesColumn.CONTENT_URL;
import static com.example.submission5.connection.DBContract.FavoriteMoviesColumn.LANGUAGE;
import static com.example.submission5.connection.DBContract.FavoriteMoviesColumn.OVERVIEW;
import static com.example.submission5.connection.DBContract.FavoriteMoviesColumn.POPULARITY;
import static com.example.submission5.connection.DBContract.FavoriteMoviesColumn.POSTER_PATH;
import static com.example.submission5.connection.DBContract.FavoriteMoviesColumn.RELEASE_DATE;
import static com.example.submission5.connection.DBContract.FavoriteMoviesColumn.TITLE;
import static com.example.submission5.connection.DBContract.FavoriteMoviesColumn.VOTE_AVERAGE;
import static com.example.submission5.connection.DBContract.FavoriteMoviesColumn._ID;
import static com.example.submission5.MainActivity.dbFavorite;

public class MovieDetailActivity extends AppCompatActivity {
    public static final String MOVIE_EXTRA = "movie_extra";

    private int id;
    private String title, overview, release, vote, popularity, language, poster;
    private TextView tvMovieTitle, tvMovieOverview, tvMovieRelease, tvMovieVote, tvMoviePopularity, tvMovieLanguage;
    private ImageView imgMoviePoster, imgMovieBlur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.movie_detail_header);
        setContentView(R.layout.activity_movie_detail);

        bindView();
        getData();
        setData();
    }

    public void bindView() {
        tvMovieTitle = findViewById(R.id.tv_detail_name);
        tvMovieOverview = findViewById(R.id.tv_detail_overview);
        tvMovieRelease = findViewById(R.id.tv_detail_release);
        tvMovieVote = findViewById(R.id.tv_detail_vote_average);
        tvMoviePopularity = findViewById(R.id.tv_detail_popularity);
        tvMovieLanguage = findViewById(R.id.tv_detail_original_language);
        imgMoviePoster = findViewById(R.id.img_poster_detail);
        imgMovieBlur = findViewById(R.id.img_blur_detail);
    }

    private void getData() {
        MovieItems movieItems = getIntent().getParcelableExtra(MOVIE_EXTRA);
        assert movieItems != null;
        id = movieItems.getId();
        title = movieItems.getMovieTitle();
        overview = movieItems.getMovieOverview();
        release = movieItems.getMovieReleasedate();
        vote = movieItems.getMovieVoteAvg();
        popularity = movieItems.getMoviePopularity();
        language = movieItems.getMovieLanguage();
        poster = movieItems.getMoviePosterpath();
    }

    private void setData() {
        tvMovieTitle.setText(title);
        tvMovieOverview.setText(overview);
        tvMovieRelease.setText(release);
        tvMovieVote.setText(vote);
        tvMoviePopularity.setText(popularity);
        tvMovieLanguage.setText(language);
        Glide.with(this)
                .load("https://image.tmdb.org/t/p/w342" + poster)
                .apply(RequestOptions.bitmapTransform(new BlurTransformation(10, 1)))
                .into(imgMovieBlur);
        Glide.with(this)
                .load("https://image.tmdb.org/t/p/w185" + poster)
                .into(imgMoviePoster);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.fav_menu, menu);

        if (dbFavorite.dao().isFavMov(id) == 1) {
            menu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_favorite_24dp));
            menu.getItem(0).setChecked(true);
        } else {
            menu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_add_to_favorite_24dp));
            menu.getItem(0).setChecked(false);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        MovieItems movieItems = new MovieItems();
        movieItems.setId(id);
        movieItems.setMovieTitle(title);
        movieItems.setMovieOverview(overview);
        movieItems.setMovieReleasedate(release);
        movieItems.setMovieVoteAvg(vote);
        movieItems.setMoviePopularity(popularity);
        movieItems.setMovieLanguage(language);
        movieItems.setMoviePosterpath(poster);

        ContentValues values = new ContentValues();
        values.put(_ID, id);
        values.put(TITLE, title);
        values.put(OVERVIEW, overview);
        values.put(RELEASE_DATE, release);
        values.put(VOTE_AVERAGE, vote);
        values.put(POPULARITY, popularity);
        values.put(LANGUAGE, language);
        values.put(POSTER_PATH, poster);

        if (menuItem.getItemId() == R.id.is_favorite) {
            if (menuItem.isChecked()) {
                menuItem.setChecked(false);
                dbFavorite.dao().delete(movieItems);

                Uri uri = Uri.parse(CONTENT_URL + "/" + id);
                getContentResolver().delete(uri, null, null);

                menuItem.setIcon(R.drawable.ic_add_to_favorite_24dp);
                Toast.makeText(this, R.string.remove_from_favorite, Toast.LENGTH_SHORT).show();
            } else {
                menuItem.setChecked(true);
                dbFavorite.dao().addMovieItem(movieItems);

                getContentResolver().insert(CONTENT_URL, values);

                menuItem.setIcon(R.drawable.ic_favorite_24dp);
                Toast.makeText(this, R.string.added_to_favorite, Toast.LENGTH_SHORT).show();
            }
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
