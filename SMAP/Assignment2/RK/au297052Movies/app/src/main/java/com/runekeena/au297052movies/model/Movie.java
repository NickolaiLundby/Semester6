package com.runekeena.au297052movies.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.runekeena.au297052movies.utils.MovieHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

// Parsable generated with plugin - https://github.com/mcharmas/android-parcelable-intellij-plugin
@Entity
public class Movie implements Parcelable {

    // variables
    @PrimaryKey
    @NonNull
    private String Title;
    private String Plot;
    private String Genres;
    private double Rating;
    private double UserRating;
    private boolean Watched;
    private int ImgId;
    private String UserComment;

    // getters and setters
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

    public String getGenres() {
        return Genres;
    }

    public void setGenres(String genres) {
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

    public void setUserComment(String comment) {
        UserComment = comment;
    }

    // Constructors
    public Movie(String title, String plot, String genres, double rating, double userRating, boolean watched, int imgId, String userComment) {
        Title = title;
        Plot = plot;
        Genres = genres;
        Rating = rating;
        UserRating = userRating;
        Watched = watched;
        ImgId = imgId;
        UserComment = userComment;
    }

    public Movie(String title, String plot, String genres, double rating, boolean watched, int imgId) {
        UserRating = -1;
        UserComment = null;
        Title = title;
        Plot = plot;
        Genres = genres;
        Rating = rating;
        Watched = watched;
        ImgId = imgId;
    }

    public Movie(){};

    public Movie(String apiResponse)
    {
        JSONObject jsonObject = null;
        try{
            jsonObject = new JSONObject(apiResponse);
            Title = jsonObject.getString("Title");
            Plot = jsonObject.getString("Plot");
            Genres = jsonObject.getString("Genre");
            JSONArray ratings = jsonObject.getJSONArray("Ratings");
            for (int i = 0; i < ratings.length(); i++) {
                JSONObject jo = ratings.getJSONObject(i);
                if(jo.getString("Source").equals("Internet Movie Database"))
                    Rating = Double.parseDouble(jo.getString("Value").split("/")[0]);
            }
            UserRating = 0.0;
            Watched = false;
            ImgId = MovieHelper.getImgId(jsonObject.getString("Genre"));
            UserComment = "";
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Parcelable

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Title);
        dest.writeString(this.Plot);
        dest.writeString(this.Genres);
        dest.writeDouble(this.Rating);
        dest.writeDouble(this.UserRating);
        dest.writeByte(this.Watched ? (byte) 1 : (byte) 0);
        dest.writeInt(this.ImgId);
        dest.writeString(this.UserComment);
    }

    protected Movie(Parcel in) {
        this.Title = in.readString();
        this.Plot = in.readString();
        this.Genres = in.readString();
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
