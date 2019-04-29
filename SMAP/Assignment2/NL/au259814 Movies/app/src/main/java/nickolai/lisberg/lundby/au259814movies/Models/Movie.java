package nickolai.lisberg.lundby.au259814movies.Models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONObject;

import nickolai.lisberg.lundby.au259814movies.Utilities.MovieHelperClass;

@Entity
public class Movie implements Parcelable {

    @PrimaryKey
    @NonNull
    private String Title;
    private String Plot;
    private String Genres;
    private double ImdbRating;
    private double UserRating;
    private boolean Watched;
    private int Poster;
    private String Comment;

    public Movie(@NonNull String title, String plot, String genres, double imdbRating, double userRating, boolean watched, int poster, String comment) {
        Title = title;
        Plot = plot;
        Genres = genres;
        ImdbRating = imdbRating;
        UserRating = userRating;
        Watched = watched;
        Poster = poster;
        Comment = comment;
    }

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
                    ImdbRating = Double.parseDouble(jo.getString("Value").split("/")[0]);
                }
            UserRating = 0.0;
            Watched = false;
            Poster = MovieHelperClass.GetPosterId(jsonObject.getString("Genre"));
            Comment = "";
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Movie() {

    }

    @NonNull
    public String getTitle() {
        return Title;
    }

    @NonNull
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

    public double getImdbRating() {
        return ImdbRating;
    }

    public void setImdbRating(double imdbRating) {
        ImdbRating = imdbRating;
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

    public int getPoster() {
        return Poster;
    }

    public void setPoster(int poster) {
        Poster = poster;
    }

    public String getComment() { return Comment; }

    public void setComment(String comment) { Comment = comment; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Title);
        dest.writeString(this.Plot);
        dest.writeString(this.Genres);
        dest.writeDouble(this.ImdbRating);
        dest.writeDouble(this.UserRating);
        dest.writeByte(this.Watched ? (byte) 1 : (byte) 0);
        dest.writeInt(this.Poster);
        dest.writeString(this.Comment);
    }

    protected Movie(Parcel in) {
        this.Title = in.readString();
        this.Plot = in.readString();
        this.Genres = in.readString();
        this.ImdbRating = in.readDouble();
        this.UserRating = in.readDouble();
        this.Watched = in.readByte() != 0;
        this.Poster = in.readInt();
        this.Comment = in.readString();
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
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
