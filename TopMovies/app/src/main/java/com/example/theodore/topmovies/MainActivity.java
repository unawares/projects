package com.example.theodore.topmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
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
        startActivity(getIntent());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
}
