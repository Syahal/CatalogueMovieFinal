package com.example.submission5.components.tvshow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.submission5.R;
import com.example.submission5.models.tvshows.TVShowItems;

import jp.wasabeef.glide.transformations.BlurTransformation;

import static com.example.submission5.MainActivity.dbFavorite;

public class TVShowDetailActivity extends AppCompatActivity {
    public static final String TVSHOW_EXTRA = "extra_tvshow";

    private int id;
    private String title, overview, airing, vote, popularity, language, poster;
    private TextView tvTVTitle, tvTVOverview, tvTVAiring, tvTVVote, tvTVPopularity, tvTVLanguage;
    private ImageView imgTVPoster, imgTVBlur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tvshow_detail);

        bindView();
        getData();
        setData();
    }

    private void bindView() {
        tvTVTitle = findViewById(R.id.tv_detail_name);
        tvTVOverview = findViewById(R.id.tv_detail_overview);
        tvTVAiring = findViewById(R.id.tv_detail_release);
        tvTVVote = findViewById(R.id.tv_detail_vote_average);
        tvTVPopularity = findViewById(R.id.tv_detail_popularity);
        tvTVLanguage = findViewById(R.id.tv_detail_original_language);
        imgTVPoster = findViewById(R.id.img_poster_detail);
        imgTVBlur = findViewById(R.id.img_blur_detail);
    }

    private void getData() {
        TVShowItems tvShowItems = getIntent().getParcelableExtra(TVSHOW_EXTRA);
        id = tvShowItems.getId();
        title = tvShowItems.getTvTitle();
        overview = tvShowItems.getTvOverview();
        airing = tvShowItems.getTvReleasedate();
        vote = tvShowItems.getTvVoteAvg();
        popularity = tvShowItems.getTvPopularity();
        language = tvShowItems.getTvLanguage();
        poster = tvShowItems.getTvPosterpath();
    }

    private void setData() {
        tvTVTitle.setText(title);
        tvTVOverview.setText(overview);
        tvTVAiring.setText(airing);
        tvTVVote.setText(vote);
        tvTVPopularity.setText(popularity);
        tvTVLanguage.setText(language);
        Glide.with(this)
                .load("https://image.tmdb.org/t/p/w342" + poster)
                .apply(RequestOptions.bitmapTransform(new BlurTransformation(10, 1)))
                .into(imgTVBlur);
        Glide.with(this)
                .load("https://image.tmdb.org/t/p/w185" + poster)
                .into(imgTVPoster);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.fav_menu, menu);

        if (dbFavorite.dao().isFavTv(id) == 1) {
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
        TVShowItems tvShowItems = new TVShowItems();

        tvShowItems.setId(id);
        tvShowItems.setTvTitle(title);
        tvShowItems.setTvOverview(overview);
        tvShowItems.setTvReleasedate(airing);
        tvShowItems.setTvVoteAvg(vote);
        tvShowItems.setTvPopularity(popularity);
        tvShowItems.setTvLanguage(language);
        tvShowItems.setTvPosterpath(poster);

        if (menuItem.getItemId() == R.id.is_favorite) {
            if (menuItem.isChecked()) {
                menuItem.setChecked(false);
                dbFavorite.dao().delete(tvShowItems);
                menuItem.setIcon(R.drawable.ic_add_to_favorite_24dp);
                Toast.makeText(this, R.string.remove_from_favorite, Toast.LENGTH_SHORT).show();
            } else {
                menuItem.setChecked(true);
                dbFavorite.dao().addTvItem(tvShowItems);
                menuItem.setIcon(R.drawable.ic_favorite_24dp);
                Toast.makeText(this, R.string.added_to_favorite, Toast.LENGTH_SHORT).show();
            }
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
