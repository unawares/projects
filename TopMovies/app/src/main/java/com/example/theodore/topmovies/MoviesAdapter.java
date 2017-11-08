package com.example.theodore.topmovies;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.support.v4.content.ContextCompat.startActivity;

/**
 * Created by Theodore on 11/7/17.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {
    private static final String MEDIA_URL = "http://image.tmdb.org/t/p/w342/";
    private ArrayList<Movie> moviesList;
    private View parent;
    private OnItemClicked onClick;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView movieImage;

        public MyViewHolder(View view) {
            super(view);
            movieImage = (ImageView) view.findViewById(R.id.movie_image);
        }
    }

    public MoviesAdapter(ArrayList<Movie> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_movie_list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Movie movie = moviesList.get(position);
        String url = MEDIA_URL + movie.poster_path;
        Picasso.with(holder.movieImage.getContext())
                .load(url)
                .into(holder.movieImage, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        holder.movieImage.getLayoutParams().height = GridLayoutManager.LayoutParams.MATCH_PARENT;
                    }

                    @Override
                    public void onError() {
                    }
                });
        holder.movieImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onItemClick(holder, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    public interface OnItemClicked {
        void onItemClick(MyViewHolder holder, int position);
    }

    public void setOnClick(OnItemClicked onClick) {
        this.onClick=onClick;
    }
}
