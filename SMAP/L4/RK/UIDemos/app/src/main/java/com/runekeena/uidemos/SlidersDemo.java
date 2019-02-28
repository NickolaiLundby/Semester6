package com.runekeena.uidemos;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

public class SlidersDemo extends AppCompatActivity {

    // UI elements
    SeekBar sbRed;
    SeekBar sbGreen;
    SeekBar sbBlue;
    Button btnOk;
    Button btnCancel;

    // variables
    int red;
    int green;
    int blue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sliders_demo);

        red = 255;
        green = 255;
        blue= 255;

        sbRed = findViewById(R.id.sbRed);
        sbRed.setMax(255);
        sbRed.setProgress(255);
        sbRed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                red = sbRed.getProgress();
                setColour();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sbGreen = findViewById(R.id.sbGreen);
        sbGreen.setMax(255);
        sbGreen.setProgress(255);
        sbGreen.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                green = sbGreen.getProgress();
                setColour();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sbBlue = findViewById(R.id.sbBlue);
        sbBlue.setMax(255);
        sbBlue.setProgress(255);
        sbBlue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                blue = sbBlue.getProgress();
                setColour();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        btnOk = findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent resultIntent= new Intent(SlidersDemo.this, MainActivity.class);
                String colour = "#" + Integer.toHexString(sbRed.getProgress()) + Integer.toHexString(sbGreen.getProgress()) + Integer.toHexString(sbBlue.getProgress());
                resultIntent.putExtra("colour", colour);
                setResult(RESULT_OK, resultIntent);
                finish();

            }
        });

        btnCancel = findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    void setColour(){
        View view = this.getWindow().getDecorView();
        view.setBackgroundColor(Color.rgb(red, green, blue));
    }
}
