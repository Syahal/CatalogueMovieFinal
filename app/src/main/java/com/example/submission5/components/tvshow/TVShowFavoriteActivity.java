package com.example.submission5.components.tvshow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.submission5.R;
import com.example.submission5.adapters.TVShowAdapterFav;
import com.example.submission5.models.tvshows.TVShowItems;

import java.util.List;

import static com.example.submission5.MainActivity.dbFavorite;

public class TVShowFavoriteActivity extends AppCompatActivity {
    private RecyclerView recyclerViewTVFav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tvshow_favorite);
        setTitle(R.string.tvshow_fav);

        recyclerViewTVFav = findViewById(R.id.rv_tvshows_fav);
        recyclerViewTVFav.setLayoutManager(new LinearLayoutManager(this));

        getFavTVShow();
    }

    private void getFavTVShow() {
        List<TVShowItems> tvShowItems = dbFavorite.dao().getFavTv();

        TVShowAdapterFav tvShowAdapterFav = new TVShowAdapterFav(tvShowItems);
        recyclerViewTVFav.setAdapter(tvShowAdapterFav);
    }
}
