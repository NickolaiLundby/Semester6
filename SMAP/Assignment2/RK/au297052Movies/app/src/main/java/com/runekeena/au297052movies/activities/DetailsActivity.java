package com.runekeena.au297052movies.activities;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.runekeena.au297052movies.R;
import com.runekeena.au297052movies.activities.Overview.OverviewActivity;
import com.runekeena.au297052movies.model.Movie;
import com.runekeena.au297052movies.services.MovieService;

import java.util.List;

public class DetailsActivity extends AppCompatActivity {

    // UI Elements
    private Button btnOk, btnDelete;
    private ImageView imgMovie;
    private TextView txtTitle, txtRating, txtUserRating, txtPlot, txtUserComment, txtGenre;
    private CheckBox checkWatched;

    // Variables
    boolean bound;
    MovieService movieService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // get movie object from indent
        //Movie m = getIntent().getExtras().getParcelable(OverviewActivity.MOVIE_DETAILS);

        // Setup ok button
        btnOk = findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Setup Delete button
        btnDelete = findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movieService.deleteMovie();
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent i = new Intent(this, MovieService.class);
        bindService(i, connection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (bound){
            unbindService(connection);
        }
    }

    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            bound = true;
            MovieService.LocalBinder mLocalBinder = (MovieService.LocalBinder)service;
            movieService = mLocalBinder.getService();
            setMovie();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            bound = false;
            movieService = null;
        }
    };


    void setMovie(){
        Movie m = movieService.getCurrentMovie();
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
    }
}

