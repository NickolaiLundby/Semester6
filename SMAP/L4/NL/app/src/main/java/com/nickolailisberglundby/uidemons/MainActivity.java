package com.nickolailisberglundby.uidemons;

import android.content.Intent;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // Buttons
    Button btnToPicker;
    Button btnToEditText;
    Button btnToSliders;

    // TextViews
    TextView pickerResultText;
    TextView pickerResult;
    TextView editTextResultText;
    TextView editTextResult;
    TextView sliderResultText;
    TextView sliderResult;

    // Request constants
    public final static int REQUEST_PICKER_VIEW = 101;
    public final static int REQUEST_EDIT_TEXT_VIEW = 102;
    public final static int REQUEST_SLIDERS_VIEW = 103;

    // Result constants
    public final static String PICKER_RESULT = "pickerResult";
    public final static String EDIT_TEXT_RESULT = "editTextResult";
    public final static String SLIDER_RESULT = "sliderResult";

    // Storage constants
    public final static String PICKER_RESULT_STORAGE = "strpkrres";
    public final static String EDIT_TEXT_RESULT_STORAGE = "stredtres";
    public final static String SLIDER_RESULT_STORAGE = "strsldres";

    // Debugging
    Toast toast;
    int picky;

    // Content constats
    public final static String PICKER_RESULT_CONTENT = "strpkrcontent";
    public final static String SLIDER_RESULT_CONTENT = "strsldcontent";
    public final static String EDIT_TEXT_RESULT_CONTENT = "stredtcontent";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Button initialization
        btnToPicker = findViewById(R.id.id_btn_to_picker);
        btnToEditText = findViewById(R.id.id_btn_to_editText);
        btnToSliders = findViewById(R.id.id_btn_to_sliders);

        // Debugging
        toast = new Toast(this);
        picky = 0;

        // TextView initialization
        pickerResultText = findViewById(R.id.id_textView_picker_text);
        pickerResult = findViewById(R.id.id_textView_picker_result);
        editTextResultText = findViewById(R.id.id_textView_editText_text);
        editTextResult = findViewById(R.id.id_textView_editText_result);
        sliderResultText = findViewById(R.id.id_textView_slider_text);
        sliderResult = findViewById(R.id.id_textView_slider_result);

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

        // Get saved instances
        if (savedInstanceState != null)
        {
            sliderResult.setText(savedInstanceState.getString(MainActivity.SLIDER_RESULT_STORAGE));

            editTextResult.setText(savedInstanceState.getString(MainActivity.EDIT_TEXT_RESULT_STORAGE));

            picky = savedInstanceState.getInt(PICKER_RESULT_STORAGE);
            pickerResult.setText(String.valueOf(picky));
        }
    }

    public void BtnToPickerClick()
    {
        Intent pickerIntent = new Intent(this, PickerActivity.class);
        pickerIntent.putExtra(SLIDER_RESULT_CONTENT, sliderResult.getText());
        pickerIntent.putExtra(EDIT_TEXT_RESULT_CONTENT, editTextResult.getText());
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
                        picky = data.getExtras().getInt(PICKER_RESULT);
                        pickerResult.setText(String.valueOf(picky));
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
                        editTextResult.setText(data.getExtras().getString(EDIT_TEXT_RESULT));
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
                        sliderResult.setText(data.getExtras().getString(SLIDER_RESULT));
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

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putInt(MainActivity.PICKER_RESULT_STORAGE, picky);
        outState.putString(MainActivity.EDIT_TEXT_RESULT_STORAGE, editTextResult.getText().toString());
        outState.putString(MainActivity.SLIDER_RESULT_STORAGE, sliderResult.getText().toString());
    }

    /*@Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(savedInstanceState.getString(SLIDER_RESULT_STORAGE) != null && !savedInstanceState.getString(SLIDER_RESULT_STORAGE).isEmpty())
            sliderResult.setText(savedInstanceState.getString(SLIDER_RESULT_STORAGE));

        if(savedInstanceState.getString(EDIT_TEXT_RESULT_STORAGE) != null && !savedInstanceState.getString(EDIT_TEXT_RESULT_STORAGE).isEmpty())
            editTextResult.setText(savedInstanceState.getString(EDIT_TEXT_RESULT_STORAGE));

        if(savedInstanceState.getString(PICKER_RESULT_STORAGE) != null && !savedInstanceState.getString(PICKER_RESULT_STORAGE).isEmpty())
            pickerResult.setText(savedInstanceState.getString(PICKER_RESULT_STORAGE));
    }
    */
}
