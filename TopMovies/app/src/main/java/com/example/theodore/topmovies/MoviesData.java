package com.example.theodore.topmovies;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Theodore on 11/7/17.
 */

class Movie implements Parcelable {
    public int vote_count;
    @SerializedName("id")
    public long id;
    public boolean video;
    public float vote_average;
    public String title;
    public float popularity;
    public String poster_path;
    public String original_language;
    public String original_title;
    //public int[] genre_ids;
    public String backdrop_path;
    public boolean adult;
    public String overview;
    public String release_date;

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(vote_count);
        out.writeLong(id);
        out.writeInt((video) ? 1 : 0);
        out.writeFloat(vote_average);
        out.writeString(title);
        out.writeFloat(popularity);
        out.writeString(poster_path);
        out.writeString(original_language);
        out.writeString(original_title);
        //out.writeIntArray(genre_ids);
        out.writeString(backdrop_path);
        out.writeInt((adult) ? 1 : 0);
        out.writeString(overview);
        out.writeString(release_date);
    }

    private Movie(Parcel in) {
        vote_count = in.readInt();
        id = in.readLong();
        video = in.readInt() == 1;
        vote_average = in.readFloat();
        title = in.readString();
        popularity = in.readFloat();
        poster_path = in.readString();
        original_language = in.readString();
        original_title = in.readString();
        //in.readIntArray(genre_ids);
        backdrop_path = in.readString();
        adult = in.readInt() == 1;
        overview = in.readString();
        release_date = in.readString();
    }

    public Movie() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Movie> CREATOR
            = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}

public class MoviesData implements Parcelable {
    public int page;
    public int total_results;
    public int total_pages;
    public Movie[] results;

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(page);
        out.writeInt(total_results);
        out.writeInt(total_pages);
        out.writeParcelableArray(results, flags);
    }

    private MoviesData(Parcel in) {
        page = in.readInt();
        total_results = in.readInt();
        total_pages = in.readInt();
        Parcelable[] res = in.readParcelableArray(Movie[].class.getClassLoader());
        results = Arrays.copyOf(res, res.length, Movie[].class);
    }

    public MoviesData() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<MoviesData> CREATOR
            = new Parcelable.Creator<MoviesData>() {
        @Override
        public MoviesData createFromParcel(Parcel in) {
            return new MoviesData(in);
        }

        @Override
        public MoviesData[] newArray(int size) {
            return new MoviesData[size];
        }
    };
}
