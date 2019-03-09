package nickolai.lundby.movielibrary.Activities;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import nickolai.lundby.movielibrary.Models.Movie;
import nickolai.lundby.movielibrary.R;

public class DetailsActivity extends AppCompatActivity {

    // Variables
    Movie movie;
    String genres = "";

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


        movie = getIntent().getExtras().getParcelable(OverviewActivity.MOVIE_DETAILS_CONTENT);
        for (String s: movie.getGenres())
            genres = genres + " " + s;
        title.setText(movie.getTitle());
        imdbRating.setText(String.valueOf(movie.getImdbRating()));
        yourRating.setText(String.valueOf(movie.getUserRating()));
        plot.setText(movie.getPlot());
        comment.setText(movie.getComment());
        watched.setChecked(movie.isWatched());
        picture.setImageBitmap(BitmapFactory.decodeResource(getResources(), movie.getPoster()));
        genre.setText(genres);

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
