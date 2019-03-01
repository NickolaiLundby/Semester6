package nickolai.lisberg.lundby.alldatabasesbelongtous;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends ArrayAdapter {
    // View lookup cache
    private static class ViewHolder {
        TextView place;
        TextView description;
        //ImageView picture;
    }

    public TaskAdapter(Context context, ArrayList<Task> tasks){
        super(context, R.layout.list_item, tasks);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Task task = (Task) getItem(position);

        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_item, parent, false);
            viewHolder.place = (TextView) convertView.findViewById(R.id.textView_task);
            viewHolder.description = (TextView) convertView.findViewById(R.id.textView_where);
            //viewHolder.picture = (ImageView) convertView.findViewById(R.id.imageView_picture);

            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.place.setText(task.getPlace());
        viewHolder.description.setText(task.getDescription());
        //viewHolder.picture.setImageBitmap();

        return convertView;
    }
}
