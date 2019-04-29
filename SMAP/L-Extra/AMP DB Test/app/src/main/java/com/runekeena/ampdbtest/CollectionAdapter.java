package com.runekeena.ampdbtest;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.CollectionHolder> {

    private List<Collection> collections = new ArrayList<>();

    @NonNull
    @Override
    public CollectionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.collection_item, parent, false);
        return new CollectionHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CollectionHolder holder, int position) {
        Collection currentCollection = collections.get(position);
        holder.textViewTitle.setText(currentCollection.getTitle());
        holder.textViewDescription.setText(currentCollection.getDescription());
    }

    @Override
    public int getItemCount() {
        return collections.size();
    }

    public void setCollections(List<Collection> collections){
        this.collections = collections;
        notifyDataSetChanged();
    }

    class CollectionHolder extends RecyclerView.ViewHolder{
        private TextView textViewTitle, textViewDescription;

        public CollectionHolder(View itemView){
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewDescription = itemView.findViewById(R.id.text_view_description);
        }

    }
}
