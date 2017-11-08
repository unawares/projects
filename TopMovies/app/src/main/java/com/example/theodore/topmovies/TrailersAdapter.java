package com.example.theodore.topmovies;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Theodore on 11/8/17.
 */

public class TrailersAdapter extends ArrayAdapter<Trailer> {
    private ArrayList<Trailer> trailers;
    private Context context;

    public TrailersAdapter(Activity context, ArrayList<Trailer> trailers) {
        super(context,0, trailers);
        this.trailers = trailers;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Trailer trailer = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.movie_trailer_item, parent, false);
        }
        TextView trailerName = (TextView) convertView.findViewById(R.id.movie_trailer_name);
        trailerName.setText(trailer.name);
        return convertView;
    }
}
