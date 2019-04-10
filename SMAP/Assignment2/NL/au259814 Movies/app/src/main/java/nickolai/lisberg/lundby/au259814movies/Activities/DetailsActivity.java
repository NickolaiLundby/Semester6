package nickolai.lisberg.lundby.au259814movies.Activities;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import nickolai.lisberg.lundby.au259814movies.Models.Movie;
import nickolai.lisberg.lundby.au259814movies.R;
import nickolai.lisberg.lundby.au259814movies.Services.MovieService;

public class DetailsActivity extends AppCompatActivity {

    // Variables
    Movie movie;

    // Service
    boolean mBound;
    MovieService mService;

    // Widgets
    Button btnOkay, btnDelete;
    TextView title, imdbRating, yourRating, plot, comment, genre;
    CheckBox watched;
    ImageView picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // Widget initialization
        btnOkay = findViewById(R.id.details_btnOkay);
        btnDelete = findViewById(R.id.details_btnDelete);
        title = findViewById(R.id.details_title);
        imdbRating = findViewById(R.id.details_imdbRating);
        yourRating = findViewById(R.id.details_yourRating);
        plot = findViewById(R.id.details_plot);
        comment = findViewById(R.id.details_comment);
        watched = findViewById(R.id.details_watched);
        picture = findViewById(R.id.details_picture);
        genre = findViewById(R.id.details_genres);

        // Listeners
        btnOkay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BtnOkayClick();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BtnDeleteClick();
            }
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
            LoadMovie();
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

    private void BtnOkayClick()
    {
        setResult(RESULT_OK, new Intent());
        finish();
    }

    private void BtnDeleteClick()
    {
        mService.DeleteMovie();
        setResult(RESULT_CANCELED, new Intent());
        finish();
    }


    public void LoadMovie() {
        movie = mService.GetCurrentMovie();
        title.setText(movie.getTitle());
        imdbRating.setText(String.valueOf(movie.getImdbRating()));
        yourRating.setText(String.valueOf(movie.getUserRating()));
        plot.setText(movie.getPlot());
        comment.setText(movie.getComment());
        watched.setChecked(movie.isWatched());
        picture.setImageBitmap(BitmapFactory.decodeResource(getResources(), movie.getPoster()));
        genre.setText(movie.getGenres());
    }
}
