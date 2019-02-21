package com.nickolailisberglundby.uidemons;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.TextView;

public class PickerActivity extends AppCompatActivity {
    // Widgets
    Button btnOkay;
    Button btnCancel;
    SeekBar sliderNumber;
    NumberPicker numberPicker;
    TextView resultText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picker);

        // Widget initialization
        btnOkay.findViewById(R.id.id_btn_picker_okay);
        btnCancel.findViewById(R.id.id_btn_picker_cancel);
        sliderNumber.findViewById(R.id.id_seekbar_picker);
        sliderNumber.setMax(1000);
        numberPicker.findViewById(R.id.id_np_picker);
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(1000);
        resultText.findViewById(R.id.id_picker_result_text);

        // Widget listeners
        btnOkay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra(MainActivity.PICKER_RESULT, sliderNumber.getProgress());
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED, new Intent());
                finish();
            }
        });

        sliderNumber.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                numberPicker.setValue(progress);
                resultText.setText("Value selected: " + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                sliderNumber.setProgress(newVal);
                resultText.setText("Value selected: " + newVal);
            }
        });
    }
}
