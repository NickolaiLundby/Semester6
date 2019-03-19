package nickolai.lisberg.lundby.afragmentedworld.Utilities;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import nickolai.lisberg.lundby.afragmentedworld.Models.Movie;
import nickolai.lisberg.lundby.afragmentedworld.R;


// Filter inspiration:
// https://stackoverflow.com/questions/24769257/custom-listview-adapter-with-filter-android
public class MovieAdapter extends ArrayAdapter {
    private ArrayList<Movie> movies;

    private static class ViewHolder {
        TextView title, userRating, imdbRating;
        ImageView picture;
        CheckBox watched;
    }

    public MovieAdapter(Context context, ArrayList<Movie> movies){
        super(context, R.layout.fragment_movie, movies);
        this.movies = movies;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Movie movie = (Movie) getItem(position);

        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.fragment_movie, parent, false);
            viewHolder.title = (TextView) convertView.findViewById(R.id.lvItem_title);
            viewHolder.userRating = (TextView) convertView.findViewById(R.id.lvItem_userrating);
            viewHolder.imdbRating = (TextView) convertView.findViewById(R.id.lvItem_imdbrating);
            viewHolder.picture = (ImageView) convertView.findViewById(R.id.lvItem_image);
            viewHolder.watched = (CheckBox) convertView.findViewById(R.id.lvItem_checkbox);

            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.title.setText(movie.getTitle());
        viewHolder.userRating.setText(String.valueOf(movie.getUserRating()));
        viewHolder.imdbRating.setText(String.valueOf(movie.getImdbRating()));
        viewHolder.picture.setImageResource(movie.getPoster());
        viewHolder.watched.setChecked(movie.isWatched());

        /*
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailsIntent = new Intent(getContext(), DetailsActivity.class);
                detailsIntent.putExtra(OverviewActivity.MOVIE_DETAILS_CONTENT, (Movie) getItem(position));
                if(getContext() instanceof OverviewActivity){
                    ((OverviewActivity)getContext()).DetailsClick(detailsIntent);
                }
            }
        });
        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent editIntent = new Intent(getContext(), EditActivity.class);
                editIntent.putExtra(OverviewActivity.MOVIE_EDIT_CONTENT, (Movie) getItem(position));
                editIntent.putExtra(OverviewActivity.MOVIE_POSITION, position);
                if(getContext() instanceof OverviewActivity){
                    ((OverviewActivity)getContext()).EditClick(editIntent);
                }
                return true;
            }
        });
        */

        return convertView;
    }
}
