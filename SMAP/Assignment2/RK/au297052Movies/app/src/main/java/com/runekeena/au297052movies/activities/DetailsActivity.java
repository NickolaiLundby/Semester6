package com.runekeena.au297052movies.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.runekeena.au297052movies.R;
import com.runekeena.au297052movies.activities.Overview.OverviewActivity;
import com.runekeena.au297052movies.model.Movie;

import java.util.List;

public class DetailsActivity extends AppCompatActivity {

    // UI Elements
    private Button btnOk;
    private ImageView imgMovie;
    private TextView txtTitle, txtRating, txtUserRating, txtPlot, txtUserComment, txtGenre;
    private CheckBox checkWatched;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // get movie object from indent
        Movie m = getIntent().getExtras().getParcelable(OverviewActivity.MOVIE_DETAILS);

        // Set image, title, rating, plot and user comment
        imgMovie = findViewById(R.id.imgMovie);
        imgMovie.setImageResource(m.getImgId());
        txtTitle = findViewById(R.id.txtTitle);
        txtTitle.setText(m.getTitle());
        txtRating = findViewById(R.id.txtRating);
        txtRating.setText(""+m.getRating());
        txtPlot = findViewById(R.id.txtPlot);
        txtUserComment = findViewById(R.id.txtUserComment);

        // Set user rating
        double userRating = m.getUserRating();
        txtUserRating = findViewById(R.id.txtUserRating);
        if(userRating > -1){
            txtUserRating.setText(""+userRating);
        } else {
            txtUserComment.setText(null);
        }
        txtPlot.setText(m.getPlot());
        txtUserComment.setText(m.getUserComment());

        txtGenre = findViewById(R.id.txtGenre);
        txtGenre.setText(m.getGenres());

        // Setup checkbox watched
        checkWatched = findViewById(R.id.checkWatched);
        checkWatched.setChecked(m.isWatched());

        // Setup ok button
        btnOk = findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
