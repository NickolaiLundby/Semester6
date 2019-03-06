package com.runekeena.movietracker.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.runekeena.movietracker.activities.Overview.OverviewActivity;
import com.runekeena.movietracker.model.Movie;
import com.runekeena.movietracker.R;

import java.util.List;

public class DetailsActivity extends AppCompatActivity {

    // UI Elements
    Button btnOk;
    ImageView imgMovie;
    TextView txtTitle;
    TextView txtRating;
    TextView txtUserRating;
    TextView txtPlot;
    TextView txtUserComment;
    TextView txtGenre;
    CheckBox checkWatched;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        imgMovie = findViewById(R.id.imgMovie);
        txtTitle = findViewById(R.id.txtTitle);
        txtRating = findViewById(R.id.txtRating);
        txtUserRating = findViewById(R.id.txtUserRating);
        txtPlot = findViewById(R.id.txtPlot);
        txtUserComment = findViewById(R.id.txtUserComment);
        txtGenre = findViewById(R.id.txtGenre);
        checkWatched = findViewById(R.id.checkWatched);

        Movie m = getIntent().getExtras().getParcelable(OverviewActivity.MOVIE_DETAILS);
        imgMovie.setImageResource(m.getImgId());
        txtTitle.setText(m.getTitle());
        txtRating.setText(""+m.getRating());
        double userRating = m.getUserRating();
        if(userRating > -1){
            txtUserRating.setText(""+userRating);
        } else {
            txtUserComment.setText(null);
        }
        txtPlot.setText(m.getPlot());
        txtUserComment.setText(m.getUserComment());
        List<String> genres = m.getGenres();
        String genreString = "";
        int genreAmount = genres.size();
        for (String s: genres){
            genreAmount --;
            if (genreAmount > 0){
                genreString = genreString + s + ", ";
            } else {
                genreString = genreString + s;
            }
        }
        txtGenre.setText(genreString);
        checkWatched.setChecked(m.isWatched());
        btnOk = findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
