package com.runekeena.allyourdatabasearebelongtous;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class TaskAdaptor extends ArrayAdapter {

    public TaskAdaptor(Context context, ArrayList<Task> tasks){
        super(context, 0, tasks);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Task task = (Task)getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_task, parent, false);
        }

        TextView description = (TextView)convertView.findViewById(R.id.description);
        TextView place = (TextView)convertView.findViewById(R.id.place);

        description.setText(task.getDescription());
        place.setText(task.getPlace());

        return convertView;

    }

}
