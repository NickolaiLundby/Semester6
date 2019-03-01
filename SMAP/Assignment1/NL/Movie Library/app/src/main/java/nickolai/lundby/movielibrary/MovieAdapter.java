package nickolai.lundby.movielibrary;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MovieAdapter extends ArrayAdapter {
    // View lookup cache
    private static class ViewHolder {
        TextView title, userRating, imdbRating;
        ImageView picture;
        CheckBox watched;
        //ImageView picture;
    }

    public MovieAdapter(Context context, ArrayList<Movie> movies){
        super(context, R.layout.list_view_item, movies);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Movie movie = (Movie) getItem(position);

        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_view_item, parent, false);
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
        viewHolder.picture.setImageBitmap(BitmapFactory.decodeResource(getContext().getResources(),movie.getPoster()));
        viewHolder.watched.setChecked(movie.isWatched());

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
                Intent editItent = new Intent(getContext(), EditActivity.class);
                editItent.putExtra(OverviewActivity.MOVIE_EDIT_CONTENT, (Movie) getItem(position));
                if(getContext() instanceof OverviewActivity){
                    ((OverviewActivity)getContext()).EditClick(editItent);
                }
                return true;
            }
        });

        return convertView;
    }
}
