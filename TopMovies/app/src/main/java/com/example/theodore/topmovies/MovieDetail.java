package com.example.theodore.topmovies;

import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;

public class MovieDetail extends AppCompatActivity {
    private static final String API_KEY = "3b7be16644ff4343aa6d5aa21a7bfba2";
    private static final String YOUTUBE_URL = "https://www.youtube.com/";
    private static final String GET_URL = "https://api.themoviedb.org/3/movie/";
    private static final String MEDIA_URL = "http://image.tmdb.org/t/p/w342/";

    private RequestQueue requestQueue;
    private Gson gson;

    private Movie movie;
    private MovieDetailData movieDetailData;
    private MovieTrailerData movieTrailersData;

    private TextView movie_title;
    private TextView movie_description;
    private TextView movie_release_date;
    private ImageView movie_image;
    private TextView movie_runtime;
    private TextView movie_vote_average;
    private TrailersAdapter adapter;
    ArrayList<Trailer> trailers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        setTitle("Movie Detail");

        movie = getIntent().getParcelableExtra("movie");
        movie_title = (TextView) findViewById(R.id.movie_title);
        movie_description = (TextView) findViewById(R.id.movie_description);
        movie_release_date = (TextView) findViewById(R.id.movie_release_date);
        movie_image = (ImageView) findViewById(R.id.movie_image);
        movie_runtime = (TextView) findViewById(R.id.movie_runtime);
        movie_vote_average = (TextView) findViewById(R.id.movie_vote_average);
        trailers = new ArrayList<Trailer>();
        adapter = new TrailersAdapter(MovieDetail.this, trailers);

        movie_title.setText(movie.title);
        movie_description.setText(movie.overview);
        movie_release_date.setText(movie.release_date);

        this.requestQueue = Volley.newRequestQueue(getApplicationContext());
        GsonBuilder gsonBuilder = new GsonBuilder();
        this.gson = gsonBuilder.create();

        Picasso.with(movie_image.getContext())
                .load(MEDIA_URL + movie.poster_path)
                .into(movie_image);

        StringRequest requestDetailData = new StringRequest(Request.Method.GET, buildGetUrl(), onMovieDetailDataLoaded, onMovieDetailDataError);
        StringRequest requestTrailers = new StringRequest(Request.Method.GET, buildGetTrailersUrl(), onMovieTrailersLoaded, onMovieTrailersDataError);
        requestQueue.add(requestDetailData);
        requestQueue.add(requestTrailers);
    }

    private String buildGetUrl() {
        String url = GET_URL + movie.id + "?api_key=" + API_KEY + "&language=en-US";
        return url;
    }

    private String buildGetTrailersUrl() {
        String url = GET_URL + movie.id + "/videos" + "?api_key=" + API_KEY;
        return url;
    }

    private String buildTrailerUrl(String key) {
        String url = YOUTUBE_URL + "watch?v=" + key;
        return url;
    }

    private final Response.Listener<String> onMovieDetailDataLoaded = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            MovieDetailData res = gson.fromJson(response, MovieDetailData.class);
            movieDetailData = res;

            movie_runtime.setText(movieDetailData.runtime + " min");
            movie_vote_average.setText(movieDetailData.vote_average + "/10");

            Log.i("MainActivity", response.toString());
        }
    };

    private final Response.ErrorListener onMovieDetailDataError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("MainActivity", error.toString());
        }
    };

    private final Response.Listener<String> onMovieTrailersLoaded = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            MovieTrailerData res = gson.fromJson(response, MovieTrailerData.class);
            movieTrailersData = res;
            ListView listView = (ListView) findViewById(R.id.movie_trailers);

            trailers.clear();
            trailers.addAll(movieTrailersData.results
                    .subList(0, (movieTrailersData.results.size() > 5)? 5 : movieTrailersData.results.size()));
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Trailer trailer = trailers.get(position);
                    String url = buildTrailerUrl(trailer.key);
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                }
            });

            ScrollView mainScroll = (ScrollView) findViewById(R.id.main_scroll);
            mainScroll.fullScroll(ScrollView.FOCUS_UP);

            Log.i("MainActivity", response.toString());
        }
    };

    private final Response.ErrorListener onMovieTrailersDataError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("MainActivity", error.toString());
        }
    };
}
