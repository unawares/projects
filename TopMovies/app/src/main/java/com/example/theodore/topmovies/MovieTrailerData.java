package com.example.theodore.topmovies;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Theodore on 11/8/17.
 */

class Trailer implements Parcelable {
    public String id;
    public String iso_639_1;
    public String iso_3166_1;
    public String key;
    public String name;
    public String site;
    public int size;
    public String type;

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(id);
        out.writeString(iso_639_1);
        out.writeString(iso_3166_1);
        out.writeString(key);
        out.writeString(name);
        out.writeString(site);
        out.writeInt(size);
        out.writeString(type);
    }

    private Trailer(Parcel in) {
        id = in.readString();
        iso_639_1 = in.readString();
        iso_3166_1 = in.readString();
        key = in.readString();
        name = in.readString();
        site = in.readString();
        size = in.readInt();
        type = in.readString();
    }

    public Trailer() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Trailer> CREATOR
            = new Parcelable.Creator<Trailer>() {
        @Override
        public Trailer createFromParcel(Parcel in) {
            return new Trailer(in);
        }

        @Override
        public Trailer[] newArray(int size) {
            return new Trailer[size];
        }
    };
}

public class MovieTrailerData implements Parcelable {
    public int id;
    public ArrayList<Trailer> results;

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(id);
        out.writeTypedList(results);
    }

    private MovieTrailerData(Parcel in) {
        id = in.readInt();
        in.readTypedList(results, Trailer.CREATOR);
    }

    public MovieTrailerData() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<MovieTrailerData> CREATOR
            = new Parcelable.Creator<MovieTrailerData>() {
        @Override
        public MovieTrailerData createFromParcel(Parcel in) {
            return new MovieTrailerData(in);
        }

        @Override
        public MovieTrailerData[] newArray(int size) {
            return new MovieTrailerData[size];
        }
    };
}
