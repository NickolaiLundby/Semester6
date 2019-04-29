package nickolai.lisberg.lundby.au259814movies.Activities;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import nickolai.lisberg.lundby.au259814movies.Models.Constants;
import nickolai.lisberg.lundby.au259814movies.Models.Movie;
import nickolai.lisberg.lundby.au259814movies.R;
import nickolai.lisberg.lundby.au259814movies.Services.MovieService;

public class EditActivity extends AppCompatActivity {

    // Variables
    Movie movie;

    // Service
    boolean mBound;
    MovieService mService;

    // Widgets
    Button btnOkay, btnCancel;
    TextView yourRating, title;
    SeekBar ratingSeekbar;
    CheckBox watched;
    EditText comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        // Widget initialization
        btnOkay = findViewById(R.id.edit_buttonOkay);
        btnCancel = findViewById(R.id.edit_buttonCancel);
        yourRating = findViewById(R.id.edit_yourRating);
        title = findViewById(R.id.edit_title);
        ratingSeekbar = findViewById(R.id.edit_slider);
        ratingSeekbar.setMax(100);
        watched = findViewById(R.id.edit_watched);
        comment = findViewById(R.id.edit_comment);

        // Listeners
        btnOkay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BtnOkayClick();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BtnCancelClick();
            }
        });
        ratingSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                double rating = progress / 10.0;
                yourRating.setText(String.valueOf(rating));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent mIntent = new Intent(this, MovieService.class);
        bindService(mIntent, mConnection, BIND_AUTO_CREATE);
    }

    ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBound = true;
            MovieService.LocalBinder mLocalBinder = (MovieService.LocalBinder)service;
            mService = mLocalBinder.getServiceInstance();
            LoadMovie(null);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBound = false;
            mService = null;
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        if(mBound) {
            unbindService(mConnection);
            mBound = false;
        }
    }

    public void LoadMovie(Movie m) {
        if (m == null) {
            movie = mService.getCurrentMovie();
            yourRating.setText(String.valueOf(movie.getUserRating()));
            ratingSeekbar.setProgress((int) (movie.getUserRating()*10));
            title.setText(movie.getTitle());
            if(!movie.getComment().isEmpty())
                comment.setText(movie.getComment());
            watched.setChecked(movie.isWatched());
        } else {
            yourRating.setText(String.valueOf(m.getUserRating()));
            ratingSeekbar.setProgress((int) (m.getUserRating()*10));
            title.setText(m.getTitle());
            if(!m.getComment().isEmpty())
                comment.setText(m.getComment());
            watched.setChecked(m.isWatched());
        }
    }

    private void BtnOkayClick()
    {
        movie.setWatched(watched.isChecked());
        movie.setComment(comment.getText().toString());
        movie.setUserRating(Double.parseDouble(yourRating.getText().toString()));
        mService.UpdateMovie(movie);

        setResult(RESULT_OK, new Intent());
        finish();
    }

    private void BtnCancelClick()
    {
        setResult(RESULT_CANCELED);
        finish();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        movie.setWatched(watched.isChecked());
        movie.setComment(comment.getText().toString());
        movie.setUserRating(Double.parseDouble(yourRating.getText().toString()));
        outState.putParcelable(Constants.EDIT_SAVE_INSTANCE, movie);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Movie m = savedInstanceState.getParcelable(Constants.EDIT_SAVE_INSTANCE);
        LoadMovie(m);
    }
}
