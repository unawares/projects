package com.example.theodore.topmovies;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Theodore on 11/7/17.
 */

public class MoviesServer {
    private static final String API_KEY = "3b7be16644ff4343aa6d5aa21a7bfba2";
    private static final String MOVIES_URL = "http://api.themoviedb.org/3/movie/";
    private RequestQueue requestQueue;
    private Gson gson;
    private int page;
    private String type;
    private MoviesAdapter adapter;
    private ArrayList<MoviesData> moviesDataArrayList;
    private ArrayList<Movie> movies;

    public MoviesServer(Context context, MoviesAdapter adapter, ArrayList<Movie> movies, String type) {
        this.requestQueue = Volley.newRequestQueue(context);
        GsonBuilder gsonBuilder = new GsonBuilder();
        this.gson = gsonBuilder.create();
        this.page = 0;
        this.type = type;
        this.movies = movies;
        this.adapter = adapter;
        this.moviesDataArrayList = new ArrayList<MoviesData>();
    }

    public void nextPage() {
        String url = buildUrl();
        StringRequest request = new StringRequest(Request.Method.GET, url, onMoviesLoaded, onMoviesError);
        requestQueue.add(request);
    }

    private String buildUrl() {
        String url = MOVIES_URL + type + "?api_key=" + API_KEY + "&page=" + (++page);
        return url;
    }

    private final Response.Listener<String> onMoviesLoaded = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            MoviesData res = gson.fromJson(response, MoviesData.class);
            moviesDataArrayList.add(res);
            movies.addAll(Arrays.asList(res.results));
            adapter.notifyDataSetChanged();
            Log.i("MainActivity", response.toString());
        }
    };

    private final Response.ErrorListener onMoviesError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("MainActivity", error.toString());
        }
    };
}
