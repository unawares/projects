package com.example.theodore.topmovies;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Theodore on 11/8/17.
 */

public class MovieDetailData implements Parcelable {
    public boolean adult;
    public String backdrop_path;
    // public String belongs_to_collection;
    public int budget;
    // public ArrayList<Object> genres;
    public String homepage;
    public int id;
    public String imdb_id;
    public String original_language;
    public String original_title;
    public String overview;
    public float popularity;
    public String poster_path;
    // public ArrayList<Object> production_companies;
    public String release_date;
    public long revenue;
    public int runtime;
    // public ArrayList<Object> spoken_languages;
    public String status;
    public String tagline;
    public String title;
    public boolean video;
    public float vote_average;
    public int vote_count;

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt((adult)? 1 : 0);
        out.writeString(backdrop_path);
        // out.writeString(belongs_to_collection);
        out.writeString(homepage);
        out.writeInt(id);
        out.writeString(imdb_id);
        out.writeString(original_language);
        out.writeString(original_title);
        out.writeString(overview);
        out.writeFloat(popularity);
        out.writeString(poster_path);
        out.writeString(release_date);
        out.writeLong(revenue);
        out.writeInt(runtime);
        out.writeString(status);
        out.writeString(tagline);
        out.writeString(title);
        out.writeInt((video)? 1 : 0);
        out.writeFloat(vote_average);
        out.writeInt(vote_count);

    }

    private MovieDetailData(Parcel in) {
        adult = in.readInt() == 1;
        backdrop_path = in.readString();
        // belongs_to_collection = in.readString();
        homepage = in.readString();
        id = in.readInt();
        imdb_id = in.readString();
        original_language = in.readString();
        original_title = in.readString();
        overview = in.readString();
        popularity = in.readFloat();
        poster_path = in.readString();
        release_date = in.readString();
        revenue = in.readLong();
        runtime = in.readInt();
        status = in.readString();
        video = in.readInt() == 1;
        vote_average = in.readFloat();
        vote_count = in.readInt();
    }

    public MovieDetailData() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<MovieDetailData> CREATOR
            = new Parcelable.Creator<MovieDetailData>() {
        @Override
        public MovieDetailData createFromParcel(Parcel in) {
            return new MovieDetailData(in);
        }

        @Override
        public MovieDetailData[] newArray(int size) {
            return new MovieDetailData[size];
        }
    };
}