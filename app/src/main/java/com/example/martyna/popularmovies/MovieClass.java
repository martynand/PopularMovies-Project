package com.example.martyna.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieClass implements Parcelable {

    private String poster_path;
    private String original_title;
    private String plot_synopsis;
    private String user_rating;
    private String release_date;

    public MovieClass() {
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getPlot_synopsis() {
        return plot_synopsis;
    }

    public void setPlot_synopsis(String plot_synopsis) {
        this.plot_synopsis = plot_synopsis;
    }

    public String getUser_rating() {
        return user_rating;
    }

    public void setUser_rating(String user_rating) {
        this.user_rating = user_rating;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    private MovieClass(Parcel in) {
        poster_path = in.readString();
        original_title = in.readString();
        plot_synopsis = in.readString();
        user_rating = in.readString();
        release_date = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(poster_path);
        parcel.writeString(original_title);
        parcel.writeString(plot_synopsis);
        parcel.writeString(user_rating);
        parcel.writeString(release_date);
    }

    public static final Parcelable.Creator<MovieClass> CREATOR = new Parcelable.Creator<MovieClass>() {
        @Override
        public MovieClass createFromParcel(Parcel in) {
            return new MovieClass(in);
        }

        @Override
        public MovieClass[] newArray(int size) {
            return new MovieClass[size];
        }

    };
}

