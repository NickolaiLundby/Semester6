package com.runekeena.au297052movies.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.runekeena.au297052movies.R;
import com.runekeena.au297052movies.activities.Overview.OverviewActivity;
import com.runekeena.au297052movies.model.Movie;

public class EditActivity extends AppCompatActivity {

    //UI elements
    private TextView txtTitle, txtUserRating;
    private SeekBar sbRating;
    private CheckBox checkWatched;
    private EditText editTxtComment;
    private Button btnOk, btnCancel;

    //Variables
    private Movie m;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        // get movie object and position from intent
        m = getIntent().getExtras().getParcelable(OverviewActivity.MOVIE_DETAILS);
        position = getIntent().getExtras().getInt(OverviewActivity.MOVIE_POSITION);

        // set title
        txtTitle = findViewById(R.id.txtTitle);
        txtTitle.setText(m.getTitle());

        // setup user rating
        txtUserRating = findViewById(R.id.txtUserRating);
        sbRating = findViewById(R.id.sbRating);
        sbRating.setMax(100);
        double userRating = m.getUserRating();
        if(userRating>-1){
            sbRating.setProgress((int)userRating*10);
            txtUserRating.setText(""+userRating);
        } else {
            txtUserRating.setText(null);
            sbRating.setProgress(0);
        }

        // setup rating seekbar listninger
        sbRating.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // set user rating when seekbar progress changes and update rating text
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

        // setup checkbox watched
        checkWatched = findViewById(R.id.checkWatched);
        checkWatched.setChecked(m.isWatched());
        checkWatched.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                m.setWatched(checkWatched.isChecked());
            }
        });
        // set up comment edittext
        editTxtComment = findViewById(R.id.editTxtComment);
        editTxtComment.setText(m.getUserComment());

        // setup ok button
        btnOk = findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnEdits();
            }
        });

        // setup cancel button
        btnCancel = findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // return resultcode canceled and finish activity
                Intent cancelIntent = new Intent(EditActivity.this, OverviewActivity.class);
                setResult(RESULT_CANCELED, cancelIntent);
                finish();
            }
        });
    }

    private void returnEdits(){
        // return result code ok and intent with movie object and position. Finish activity
        Intent resultIntent = new Intent(EditActivity.this, OverviewActivity.class);
        m.setComment(editTxtComment.getText().toString());
        resultIntent.putExtra(OverviewActivity.MOVIE_DETAILS, m);
        resultIntent.putExtra(OverviewActivity.MOVIE_POSITION, position);
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}
