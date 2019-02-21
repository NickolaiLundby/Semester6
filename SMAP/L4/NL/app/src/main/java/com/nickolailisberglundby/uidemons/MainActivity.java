package com.nickolailisberglundby.uidemons;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // Buttons
    Button btnToPicker;
    Button btnToEditText;
    Button btnToSliders;

    // TextViews
    TextView pickerResult;
    TextView editTextResult;
    TextView sliderResult;

    // Request constants
    public final static int REQUEST_PICKER_VIEW = 101;
    public final static int REQUEST_EDIT_TEXT_VIEW = 102;
    public final static int REQUEST_SLIDERS_VIEW = 103;

    // Result constants
    public final static String PICKER_RESULT = "pickerResult";
    public final static String EDIT_TEXT_RESULT = "editTextResult";
    public final static String SLIDER_RESULT = "sliderResult";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Button initialization
        btnToPicker = findViewById(R.id.id_btn_to_picker);
        btnToEditText = findViewById(R.id.id_btn_to_editText);
        btnToSliders = findViewById(R.id.id_btn_to_sliders);

        // TextView initialization
        pickerResult = findViewById(R.id.id_textView_picker_res);
        editTextResult = findViewById(R.id.id_textView_editText_res);
        sliderResult = findViewById(R.id.id_textView_slider_res);

        // Button listeners
        btnToPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BtnToPickerClick();
            }
        });

        btnToEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BtnToEditTextClick();
            }
        });

        btnToSliders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BtnToSlidersClick();
            }
        });
    }

    public void BtnToPickerClick()
    {
        startActivityForResult(new Intent(this, PickerActivity.class), REQUEST_PICKER_VIEW);
    }

    public void BtnToEditTextClick()
    {
        startActivityForResult(new Intent(this, EditTextActivity.class), REQUEST_EDIT_TEXT_VIEW);
    }

    public void BtnToSlidersClick()
    {
        startActivityForResult(new Intent(this, SlidersActivity.class), REQUEST_SLIDERS_VIEW);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode)
        {
            case(REQUEST_PICKER_VIEW):
                switch(resultCode)
                {
                    case RESULT_OK:
                        pickerResult.setText("Picker result: " + data.getExtras().getInt(PICKER_RESULT));
                        break;
                    case RESULT_CANCELED:
                        break;
                    default:
                        break;
                }
            case(REQUEST_EDIT_TEXT_VIEW):
                switch(resultCode)
                {
                    case RESULT_OK:
                        editTextResult.setText("EditText result: " + data.getExtras().getString(EDIT_TEXT_RESULT));
                        break;
                    case RESULT_CANCELED:
                        break;
                    default:
                        break;
                }
            case(REQUEST_SLIDERS_VIEW):
                switch(resultCode)
                {
                    case RESULT_OK:
                        sliderResult.setText("Slider result: " + data.getExtras().getString(SLIDER_RESULT));
                        break;
                    case RESULT_CANCELED:
                        break;
                    default:
                        break;
                }
            default:
                break;
        }
    }
}
