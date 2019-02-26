package com.example.whyisntthisworking;

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
    TextView txtResult;
    NumberPicker numberPicker;
    SeekBar seekBar;

    // Variables
    int inputFromMain;
    int pickerResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        // Variable initialization
        inputFromMain = getIntent().getIntExtra(MainActivity.CONTENT_KEY, 0);
        pickerResult = inputFromMain;

        // Widget initialization
        btnOkay = findViewById(R.id.button_picker_okay);
        btnCancel = findViewById(R.id.button_picker_cancel);
        txtResult = findViewById(R.id.textView_pickerView_result);
        numberPicker = findViewById(R.id.numberPicker);
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(1000);
        seekBar = findViewById(R.id.seekBar);
        seekBar.setMax(1000);
        seekBar.setProgress(inputFromMain);
        if(inputFromMain != 0)
        {
            seekBar.setProgress(inputFromMain);
            numberPicker.setValue(inputFromMain);
            txtResult.setText(String.valueOf(inputFromMain));
        }

        // Listeners
        btnOkay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra(MainActivity.PICKER_VIEW_RESULT, pickerResult);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                setResult(RESULT_CANCELED, resultIntent);
                finish();
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                numberPicker.setValue(progress);
                pickerResult = progress;
                txtResult.setText(String.valueOf(pickerResult));
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
                seekBar.setProgress(newVal);
                pickerResult = newVal;
                txtResult.setText(String.valueOf(pickerResult));
            }
        });
    }
}
