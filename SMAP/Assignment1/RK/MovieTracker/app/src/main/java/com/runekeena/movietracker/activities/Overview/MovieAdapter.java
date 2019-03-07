package com.runekeena.movietracker.activities.Overview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.runekeena.movietracker.R;
import com.runekeena.movietracker.model.Movie;

import java.util.ArrayList;

public class MovieAdapter extends ArrayAdapter {

    // viewholder class for holding ui elements
    private static class ViewHolder {
        TextView title, rating, userRating;
        ImageView image;
        CheckBox watched;
    }

    // constructor
    public MovieAdapter(Context context, ArrayList<Movie> movies){
        super(context, 0, movies);
    }

    // create or get view and set values
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Movie movie = (Movie) getItem(position);
        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_movie_item, parent, false);

            viewHolder.title = (TextView) convertView.findViewById(R.id.txtTitle);
            viewHolder.rating = (TextView) convertView.findViewById(R.id.txtRating);
            viewHolder.userRating = (TextView) convertView.findViewById(R.id.txtUserRating);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.imgMovie);
            viewHolder.watched = (CheckBox) convertView.findViewById(R.id.checkWatched);

            convertView.setTag(viewHolder);
        } else {
            viewHolder =(ViewHolder)convertView.getTag();
        }

        viewHolder.title.setText(movie.getTitle());
        if (movie.getUserRating() > -1){
            viewHolder.userRating.setText(String.valueOf(movie.getUserRating()));
        } else {
            viewHolder.userRating.setText(null);
        }
        viewHolder.rating.setText(String.valueOf(movie.getRating()));
        viewHolder.image.setImageResource(movie.getImgId());
        if (movie.isWatched()){
            viewHolder.watched.setChecked(movie.isWatched());
            viewHolder.watched.setText(R.string.watched);
        } else {
            viewHolder.watched.setChecked(movie.isWatched());
            viewHolder.watched.setText(null);
        }

        return convertView;

    }
}
