package nickolai.lundby.movielibrary;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

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
        super(context, R.layout.listview_item, movies);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Movie movie = (Movie) getItem(position);

        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.listview_item, parent, false);
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

        return convertView;
    }
}
