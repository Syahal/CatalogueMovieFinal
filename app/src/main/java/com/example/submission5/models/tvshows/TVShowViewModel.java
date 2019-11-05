package com.example.submission5.models.tvshows;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.submission5.BuildConfig;
import com.example.submission5.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class TVShowViewModel extends ViewModel {
    private static final String API_KEY = BuildConfig.TMBD_API_KEY;
    private MutableLiveData<ArrayList<TVShowItems>> listTvShows = new MutableLiveData<>();

    public void setTvShow(final Context context) {
        AsyncHttpClient httpClient = new AsyncHttpClient();
        final ArrayList<TVShowItems> listTvItems = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/discover/tv?api_key=" + API_KEY + "&language=en-US";

        httpClient.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray jsonArray = jsonObject.getJSONArray("results");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject tvshow = jsonArray.getJSONObject(i);
                        TVShowItems tvShowItems = new TVShowItems(tvshow);
                        listTvItems.add(tvShowItems);
                    }
                    listTvShows.postValue(listTvItems);
                } catch (Exception e) {
                    Log.d("Error TV Show", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(context, context.getString(R.string.error_connection), Toast.LENGTH_SHORT).show();
                Log.d("TVShow Connection Error", error.getMessage());
            }
        });
    }

    public void searchTVShow(final Context context, String querySearchTVShow) {
        AsyncHttpClient httpClient = new AsyncHttpClient();
        final ArrayList<TVShowItems> tvShowItemsArrayList = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/search/tv?api_key=" + API_KEY + "&language=en-US&query=" + querySearchTVShow;

        httpClient.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray jsonArray = jsonObject.getJSONArray("results");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject tvshow = jsonArray.getJSONObject(i);
                        TVShowItems tvShowItems = new TVShowItems(tvshow);
                        tvShowItemsArrayList.add(tvShowItems);
                    }
                    listTvShows.postValue(tvShowItemsArrayList);
                } catch (Exception e) {
                    Log.d("Search TVShow Error", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(context, context.getString(R.string.error_connection), Toast.LENGTH_SHORT).show();
                Log.d("No Connection", error.getMessage());
            }
        });
    }

    public LiveData<ArrayList<TVShowItems>> getTvShows() {
        return listTvShows;
    }
}
