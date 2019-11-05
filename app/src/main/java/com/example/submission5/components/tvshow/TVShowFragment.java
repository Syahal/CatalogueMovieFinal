package com.example.submission5.components.tvshow;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.example.submission5.R;
import com.example.submission5.adapters.TVShowAdapter;
import com.example.submission5.models.tvshows.TVShowItems;
import com.example.submission5.models.tvshows.TVShowViewModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TVShowFragment extends Fragment {
    private ProgressBar progressBar;
    private ArrayList<TVShowItems> tvshowList = new ArrayList<>();
    private TVShowAdapter tvShowAdapter;
    private TVShowViewModel tvShowViewModel;

    public TVShowFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tvshow, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        progressBar = view.findViewById(R.id.progress_bar);
        RecyclerView recyclerViewTVShow = view.findViewById(R.id.rv_tvshows);
        final EditText edtSearchTVShow = view.findViewById(R.id.tv_search);
        ImageButton btnSearchTVShow = view.findViewById(R.id.btn_search_tv);

        tvShowViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(TVShowViewModel.class);
        tvShowViewModel.getTvShows().observe(this, getTVShow);

        tvShowAdapter = new TVShowAdapter(tvshowList);
        tvShowAdapter.notifyDataSetChanged();

        recyclerViewTVShow.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewTVShow.setAdapter(tvShowAdapter);

        tvShowViewModel.setTvShow(getContext());

        tvShowAdapter.setOnItemClickCallback(new TVShowAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(TVShowItems tvData) {
                Intent tvShowIntent = new Intent(getContext(), TVShowDetailActivity.class);
                tvShowIntent.putExtra(TVShowDetailActivity.TVSHOW_EXTRA, tvData);
                startActivity(tvShowIntent);
            }
        });

        btnSearchTVShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoading(true);
                String querySearchTVShow = edtSearchTVShow.getText().toString();

                if (querySearchTVShow.length() > 0) {
                    tvShowViewModel.searchTVShow(getContext(), querySearchTVShow);
                } else {
                    tvShowViewModel.setTvShow(getContext());
                }
            }
        });
    }

    private Observer<ArrayList<TVShowItems>> getTVShow = new Observer<ArrayList<TVShowItems>>() {
        @Override
        public void onChanged(ArrayList<TVShowItems> tvShowItems) {
            showLoading(true);

            if (tvShowItems != null) {
                tvShowAdapter.setData(tvShowItems);
                showLoading(false);
            }
        }
    };

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}
