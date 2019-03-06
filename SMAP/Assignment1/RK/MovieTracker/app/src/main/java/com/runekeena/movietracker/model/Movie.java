package com.runekeena.movietracker.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Movie implements Parcelable {

    private String Title;
    private String Plot;
    private List<String> Genres;
    private double Rating;
    private double UserRating;
    private boolean Watched;
    private int ImgId;
    private String UserComment;

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getPlot() {
        return Plot;
    }

    public void setPlot(String plot) {
        Plot = plot;
    }

    public List<String> getGenres() {
        return Genres;
    }

    public void setGenres(List<String> genres) {
        Genres = genres;
    }

    public double getRating() {
        return Rating;
    }

    public void setRating(double rating) {
        Rating = rating;
    }

    public double getUserRating() {
        return UserRating;
    }

    public void setUserRating(double userRating) {
        UserRating = userRating;
    }

    public boolean isWatched() {
        return Watched;
    }

    public void setWatched(boolean watched) {
        Watched = watched;
    }

    public int getImgId() {
        return ImgId;
    }

    public void setImgId(int imgId) {
        ImgId = imgId;
    }

    public String getUserComment() {
        return UserComment;
    }

    public void setComment(String comment) {
        UserComment = comment;
    }

    public Movie(String title, String plot, List<String> genres, double rating, double userRating, boolean watched, int imgId, String userComment) {
        Title = title;
        Plot = plot;
        Genres = genres;
        Rating = rating;
        UserRating = userRating;
        Watched = watched;
        ImgId = imgId;
        UserComment = userComment;
    }

    public Movie(String title, String plot, List<String> genres, double rating, boolean watched, int imgId) {
        UserRating = -1;
        UserComment = null;
        Title = title;
        Plot = plot;
        Genres = genres;
        Rating = rating;
        Watched = watched;
        ImgId = imgId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Title);
        dest.writeString(this.Plot);
        dest.writeStringList(this.Genres);
        dest.writeDouble(this.Rating);
        dest.writeDouble(this.UserRating);
        dest.writeByte(this.Watched ? (byte) 1 : (byte) 0);
        dest.writeInt(this.ImgId);
        dest.writeString(this.UserComment);
    }

    protected Movie(Parcel in) {
        this.Title = in.readString();
        this.Plot = in.readString();
        this.Genres = in.createStringArrayList();
        this.Rating = in.readDouble();
        this.UserRating = in.readDouble();
        this.Watched = in.readByte() != 0;
        this.ImgId = in.readInt();
        this.UserComment = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
