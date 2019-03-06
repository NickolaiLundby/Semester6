package com.runekeena.movietracker.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.runekeena.movietracker.activities.Overview.OverviewActivity;
import com.runekeena.movietracker.model.Movie;
import com.runekeena.movietracker.R;

public class EditActivity extends AppCompatActivity {

    //UI elements
    TextView txtTitle;
    TextView txtUserRating;
    SeekBar sbRating;
    CheckBox checkWatched;
    EditText editTxtComment;
    Button btnOk;
    Button btnCancel;

    //Variables
    Movie m;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        // Init UI elements
        txtTitle = findViewById(R.id.txtTitle);
        txtUserRating = findViewById(R.id.txtUserRating);
        sbRating = findViewById(R.id.sbRating);
        sbRating.setMax(100);
        checkWatched = findViewById(R.id.checkWatched);
        editTxtComment = findViewById(R.id.editTxtComment);
        btnOk = findViewById(R.id.btnOk);
        btnCancel = findViewById(R.id.btnCancel);

        m = getIntent().getExtras().getParcelable(OverviewActivity.MOVIE_DETAILS);
        position = getIntent().getExtras().getInt(OverviewActivity.MOVIE_POSITION);
        txtTitle.setText(m.getTitle());
        double userRating = m.getUserRating();
        if(userRating>-1){
            sbRating.setProgress((int)userRating*10);
            txtUserRating.setText(""+userRating);
        } else {
            txtUserRating.setText(null);
            sbRating.setProgress(0);
        }
        sbRating.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int p = sbRating.getProgress();
                if (p>0){
                    m.setUserRating((double)(p/10));
                } else {
                    m.setUserRating((double)0);

                }
                txtUserRating.setText(""+m.getUserRating());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        checkWatched.setChecked(m.isWatched());
        checkWatched.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                m.setWatched(checkWatched.isChecked());
            }
        });
        editTxtComment.setText(m.getUserComment());
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent(EditActivity.this, OverviewActivity.class);
                m.setComment(editTxtComment.getText().toString());
                resultIntent.putExtra(OverviewActivity.MOVIE_DETAILS, m);
                resultIntent.putExtra(OverviewActivity.MOVIE_POSITION, position);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cancelIntent = new Intent(EditActivity.this, OverviewActivity.class);
                setResult(RESULT_CANCELED, cancelIntent);
                finish();
            }
        });

    }
}
