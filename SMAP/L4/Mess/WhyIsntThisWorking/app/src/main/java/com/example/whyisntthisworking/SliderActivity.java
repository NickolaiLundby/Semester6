package com.example.whyisntthisworking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

public class SliderActivity extends AppCompatActivity {

    // Widgets
    Button btnOkay, btnCancel;
    SeekBar seekBarRed, seekBarBlue, seekBarGreen;

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

    public void BtnOkayClick()
    {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(MainActivity.SLIDER_VIEW_RESULT, 1);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    public void BtnCancelClick()
    {
        Intent resultIntent = new Intent();
        setResult(RESULT_CANCELED);
        finish();
    }
}
