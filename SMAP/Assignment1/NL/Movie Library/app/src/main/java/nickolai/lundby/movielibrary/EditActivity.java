package nickolai.lundby.movielibrary;

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
    TextView yourRating;
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
        ratingSeekbar = findViewById(R.id.edit_slider);
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
    }

    private void BtnOkayClick()
    {

    }

    private void BtnCancelClick()
    {

    }
}
