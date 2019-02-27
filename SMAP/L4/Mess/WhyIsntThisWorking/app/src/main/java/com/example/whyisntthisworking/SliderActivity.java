package com.example.whyisntthisworking;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

public class SliderActivity extends AppCompatActivity {

    // Widgets
    Button btnOkay, btnCancel;
    SeekBar seekBarRed, seekBarBlue, seekBarGreen;
    int Red, Green, Blue, BackgroundColor;
    ConstraintLayout sliderLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider);

        // Widget initialization
        btnOkay = findViewById(R.id.sliderView_okay_btn);
        btnCancel = findViewById(R.id.sliderView_cancel_btn);
        seekBarRed = findViewById(R.id.sliderView_seekbar_red);
        seekBarBlue = findViewById(R.id.sliderView_seekbar_blue);
        seekBarGreen = findViewById(R.id.sliderView_seekbar_green);
        sliderLayout = findViewById(R.id.sliderLayout);

        Red = getIntent().getIntExtra(MainActivity.CONTENT_KEY_RED, 0);
        Blue = getIntent().getIntExtra(MainActivity.CONTENT_KEY_BLUE, 0);
        Green = getIntent().getIntExtra(MainActivity.CONTENT_KEY_GREEN, 0);
        if(!(Red == 0 && Blue == 0 && Green == 0))
            SetLayout(Red, Green, Blue);

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

        seekBarBlue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Blue = progress;
                SetLayout();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekBarGreen.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Green = progress;
                SetLayout();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekBarRed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Red = progress;
                SetLayout();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void BtnOkayClick()
    {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(MainActivity.SLIDER_VIEW_RESULT_BLUE, Blue);
        resultIntent.putExtra(MainActivity.SLIDER_VIEW_RESULT_GREEN, Green);
        resultIntent.putExtra(MainActivity.SLIDER_VIEW_RESULT_RED, Red);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    public void BtnCancelClick()
    {
        Intent resultIntent = new Intent();
        setResult(RESULT_CANCELED);
        finish();
    }

    public void SetLayout()
    {
        BackgroundColor = 0xff000000 + Red * 0x10000 + Green * 0x100 + Blue;
        sliderLayout.setBackgroundColor(BackgroundColor);
    }

    public void SetLayout(int red, int green, int blue)
    {
        BackgroundColor = 0xff000000 + red * 0x10000 + green * 0x100 + blue;
        sliderLayout.setBackgroundColor(BackgroundColor);
    }
}
