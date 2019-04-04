package nickolai.lisberg.lundby.au259814movies.Activities;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import nickolai.lisberg.lundby.au259814movies.Models.Movie;
import nickolai.lisberg.lundby.au259814movies.R;

public class DetailsActivity extends AppCompatActivity {

    // Variables
    Movie movie;
    String prefix = "";
    StringBuilder genres = new StringBuilder();

    // Widgets
    Button btnOkay;
    TextView title, imdbRating, yourRating, plot, comment, genre;
    CheckBox watched;
    ImageView picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // Widget initialization
        btnOkay = findViewById(R.id.details_btnOkay);
        title = findViewById(R.id.details_title);
        imdbRating = findViewById(R.id.details_imdbRating);
        yourRating = findViewById(R.id.details_yourRating);
        plot = findViewById(R.id.details_plot);
        comment = findViewById(R.id.details_comment);
        watched = findViewById(R.id.details_watched);
        picture = findViewById(R.id.details_picture);
        genre = findViewById(R.id.details_genres);

        // Variable initialization
        movie = getIntent().getExtras().getParcelable(OverviewActivity.MOVIE_DETAILS_CONTENT);

        // Assign widget values
        title.setText(movie.getTitle());
        imdbRating.setText(String.valueOf(movie.getImdbRating()));
        yourRating.setText(String.valueOf(movie.getUserRating()));
        plot.setText(movie.getPlot());
        comment.setText(movie.getComment());
        watched.setChecked(movie.isWatched());
        picture.setImageBitmap(BitmapFactory.decodeResource(getResources(), movie.getPoster()));
        genre.setText(movie.getGenres());

        // Listeners
        btnOkay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BtnOkayClick();
            }
        });
    }

    private void BtnOkayClick()
    {
        Intent resultIntent = new Intent();
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putParcelable(OverviewActivity.STORAGE_DETAIL, movie);
    }
}
