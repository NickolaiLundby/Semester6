package com.runekeena.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class BurgerAdapter extends ArrayAdapter<Burger> {
        public BurgerAdapter(Context context, ArrayList<Burger> users) {
            super(context, 0, users);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            Burger burger = getItem(position);

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_burger, parent, false);
            }

            TextView txtName = (TextView) convertView.findViewById(R.id.txtName);
            TextView txtSeason = (TextView) convertView.findViewById(R.id.txtSeason);

            txtName.setText(burger.getName());
            txtSeason.setText(burger.getSeason());

            return convertView;
        }
}
