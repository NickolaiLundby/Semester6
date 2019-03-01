package nickolai.lundby.movielibrary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

public class EditActivity extends AppCompatActivity {

    // Variables
    Movie movie;

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

        // Widget initialization
        btnOkay = findViewById(R.id.edit_buttonOkay);
        btnCancel = findViewById(R.id.edit_buttonCancel);
        yourRating = findViewById(R.id.edit_yourRating);
        title = findViewById(R.id.edit_title);
        ratingSeekbar = findViewById(R.id.edit_slider);
        ratingSeekbar.setMax(10);
        watched = findViewById(R.id.edit_watched);
        comment = findViewById(R.id.edit_comment);

        yourRating.setText(Double.toString(movie.getUserRating()));
        title.setText(movie.getTitle());
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
    }

    private void BtnOkayClick()
    {
        movie.setWatched(watched.isChecked());
        movie.setComment(comment.getText().toString());
        movie.setUserRating(Double.parseDouble(yourRating.getText().toString()));
        Intent resultIntent = new Intent();
        resultIntent.putExtra(OverviewActivity.RESULT_EDIT, (Movie) movie);
    }

    private void BtnCancelClick()
    {
        setResult(RESULT_CANCELED);
        finish();
    }
}
