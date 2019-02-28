package com.runekeena.uidemos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.TextView;

public class PickerDemo extends AppCompatActivity {

    // UI elements
    Button btnOk;
    Button btnCancel;
    SeekBar sbNum;
    NumberPicker np;

    // Variables
    int currentNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picker_demo);


        np = findViewById(R.id.np);
        np.setMinValue(0);
        np.setMaxValue(1000);
        np.setWrapSelectorWheel(true);
        sbNum = findViewById(R.id.sbNum);
        currentNumber = sbNum.getProgress();
        sbNum.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                currentNumber = progress;
                np.setValue(currentNumber);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

        });

        np = findViewById(R.id.np);
        np.setMinValue(0);
        np.setMaxValue(1000);
        np.setWrapSelectorWheel(true);
        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                currentNumber = newVal;
                sbNum.setProgress(currentNumber);
            }
        });

        btnOk = findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent resultintent= new Intent(PickerDemo.this, MainActivity.class);
                resultintent.putExtra("number", currentNumber);
                setResult(RESULT_OK, resultintent);
                finish();
            }
        });

        btnCancel = findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent resultintent= new Intent(PickerDemo.this, MainActivity.class);
                setResult(RESULT_CANCELED, resultintent);
                finish();
            }
        });


    }
}
