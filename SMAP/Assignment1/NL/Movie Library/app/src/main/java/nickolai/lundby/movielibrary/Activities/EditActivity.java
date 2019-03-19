package nickolai.lundby.movielibrary.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import nickolai.lundby.movielibrary.Models.Movie;
import nickolai.lundby.movielibrary.R;

public class EditActivity extends AppCompatActivity {

    // Variables
    Movie movie;
    int moviePosition;

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

        // Variable initialization
        movie = getIntent().getExtras().getParcelable(OverviewActivity.MOVIE_EDIT_CONTENT);
        moviePosition = getIntent().getExtras().getInt(OverviewActivity.MOVIE_POSITION);

        // Widget initialization
        btnOkay = findViewById(R.id.edit_buttonOkay);
        btnCancel = findViewById(R.id.edit_buttonCancel);
        yourRating = findViewById(R.id.edit_yourRating);
        title = findViewById(R.id.edit_title);
        ratingSeekbar = findViewById(R.id.edit_slider);
        ratingSeekbar.setMax(100);
        watched = findViewById(R.id.edit_watched);
        comment = findViewById(R.id.edit_comment);

        // Assign widget values
        yourRating.setText(String.valueOf(movie.getUserRating()));
        ratingSeekbar.setProgress((int) movie.getUserRating()*10);
        title.setText(movie.getTitle());
        if(!movie.getComment().isEmpty())
            comment.setText(movie.getComment());
        watched.setChecked(movie.isWatched());

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

    private void BtnOkayClick()
    {
        movie.setWatched(watched.isChecked());
        movie.setComment(comment.getText().toString());
        movie.setUserRating(Double.parseDouble(yourRating.getText().toString()));
        Intent resultIntent = new Intent();
        resultIntent.putExtra(OverviewActivity.RESULT_EDIT, movie);
        resultIntent.putExtra(OverviewActivity.MOVIE_POSITION, moviePosition);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    private void BtnCancelClick()
    {
        setResult(RESULT_CANCELED);
        finish();
    }
}
