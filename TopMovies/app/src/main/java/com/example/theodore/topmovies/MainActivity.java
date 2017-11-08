package com.example.theodore.topmovies;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MoviesAdapter.OnItemClicked {
    private ArrayList<Movie> movies = new ArrayList<>();

    private RecyclerView recyclerView;
    private MoviesAdapter mAdapter;
    private MoviesServer moviesServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int orientation = this.getResources().getConfiguration().orientation;
        recyclerView = (RecyclerView) findViewById(R.id.recycler_movies_view);
        mAdapter = new MoviesAdapter(movies);
        RecyclerView.LayoutManager mLayoutManager =  new GridLayoutManager(getApplicationContext(), (orientation == 1)? 2 : 3);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);

        moviesServer = new MoviesServer(getApplicationContext(), mAdapter, movies, "popular");
        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener((GridLayoutManager) mLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                moviesServer.nextPage();
            }
        });
        moviesServer.nextPage();
        mAdapter.setOnClick(this);
    }

    @Override
    public void onItemClick(MoviesAdapter.MyViewHolder holder, int position) {
        Intent intent = new Intent(MainActivity.this, MovieDetail.class);
        Movie movie = movies.get(position);
        intent.putExtra("movie", movie);
        startActivity(intent);
    }
}
