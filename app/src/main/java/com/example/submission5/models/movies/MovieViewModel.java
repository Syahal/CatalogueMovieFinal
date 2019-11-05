package com.example.submission5.models.movies;

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

public class MovieViewModel extends ViewModel {
    private static final String API_KEY = BuildConfig.TMBD_API_KEY;
    private MutableLiveData<ArrayList<MovieItems>> listMovies = new MutableLiveData<>();

    public void setMovies(final Context context) {
        AsyncHttpClient httpClient = new AsyncHttpClient();
        final ArrayList<MovieItems> listMovieItems = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/discover/movie?api_key=" + API_KEY + "&language=en-US";

        httpClient.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray jsonArray = jsonObject.getJSONArray("results");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject movies = jsonArray.getJSONObject(i);
                        MovieItems movieItems = new MovieItems(movies);
                        listMovieItems.add(movieItems);
                    }
                    listMovies.postValue(listMovieItems);
                } catch (Exception e) {
                    Log.d("Error Movie", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(context, context.getString(R.string.error_connection), Toast.LENGTH_SHORT).show();
                Log.d("Movie Connection Error", error.getMessage());
            }
        });
    }

    public void searchMovie(final Context context, String querySearcMovie) {
        AsyncHttpClient httpClient = new AsyncHttpClient();
        final ArrayList<MovieItems> movieItemsArrayList = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/search/movie?api_key=" + API_KEY + "&language=en-US&query=" + querySearcMovie;

        httpClient.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray jsonArray = jsonObject.getJSONArray("results");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject movies = jsonArray.getJSONObject(i);
                        MovieItems movieItems = new MovieItems(movies);
                        movieItemsArrayList.add(movieItems);
                    }
                    listMovies.postValue(movieItemsArrayList);
                } catch (Exception e) {
                    Log.d("Search Movie Error", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(context, context.getString(R.string.error_connection), Toast.LENGTH_SHORT).show();
                Log.d("No Connection", error.getMessage());
            }
        });
    }

    public LiveData<ArrayList<MovieItems>> getMovies() {
        return listMovies;
    }
}
