package com.example.whyisntthisworking;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // Widgets
    Button btnPicker;
    Button btnEdit;
    Button btnSlider;

    TextView txtViewPickerResult;
    TextView txtViewEditResultPlainText;
    TextView txtViewEditResultEmail;
    TextView txtViewEditResultPassword;
    TextView txtViewSliderResult;

    // Variables
    int pickerResult;
    String editResultPlainText;
    String editResultPassword;
    String editResultEmail;
    int sliderResult;

    //--Constants--//
    // Requests
    public final static int REQUEST_PICKER_VIEW = 100;
    public final static int REQUEST_EDIT_VIEW = 101;
    public final static int REQUEST_SLIDER_VIEW = 102;
    // Results
    public final static String PICKER_VIEW_RESULT = "pickerViewResult";
    public final static String EDIT_VIEW_RESULT_PLAINTEXT = "editViewResultPlainText";
    public final static String EDIT_VIEW_RESULT_EMAIL = "editViewResultEmail";
    public final static String EDIT_VIEW_RESULT_PASSWORD = "editViewResultPassword";
    public final static String SLIDER_VIEW_RESULT = "sliderViewResult";
    // Storage
    public final static String PICKER_STORAGE_KEY = "pickerStorage";
    public final static String EDIT_PLAINTEXT_STORAGE_KEY = "editStoragePlainText";
    public final static String EDIT_PASSWORD_STORAGE_KEY = "editStorageEmail";
    public final static String EDIT_EMAIL_STORAGE_KEY = "editStoragePassword";
    public final static String SLIDER_STORAGE_KEY = "sliderStorage";
    // Global content key
    public final static String CONTENT_KEY = "contentFromMain";
    public final static String CONTENT_KEY_EMAIL = "emailContentFromMain";
    public final static String CONTENT_KEY_PASSWORD = "passwordContentFromMain";
    public final static String CONTENT_KEY_PLAINTEXT = "plainTextContentFromMain";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Widget initialization
        btnPicker = findViewById(R.id.btn_main);
        btnEdit = findViewById(R.id.btn_main2);
        btnSlider = findViewById(R.id.btn_main3);
        txtViewPickerResult = findViewById(R.id.textView_pickerView_result);
        txtViewEditResultPlainText = findViewById(R.id.textView_edit_result_plaintext);
        txtViewEditResultEmail = findViewById(R.id.textView_edit_result_email);
        txtViewEditResultPassword = findViewById(R.id.textView_edit_result_password);
        txtViewSliderResult = findViewById(R.id.textView_slider_result);

        // Variable initialization
        pickerResult = 0;
        sliderResult = 0;

        // Listeners
        btnPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BtnPickerClick();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BtnEditClick();
            }
        });

        btnSlider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BtnSliderClick();
            }
        });

        // Save instance state
        if(savedInstanceState != null)
        {
            pickerResult = savedInstanceState.getInt(PICKER_STORAGE_KEY);
            if(pickerResult != 0) {
                txtViewPickerResult.setText(String.valueOf(pickerResult));
            }
            editResultPlainText = savedInstanceState.getString(EDIT_PLAINTEXT_STORAGE_KEY);
            if(editResultPlainText != null && !editResultPlainText.isEmpty())
            {
                txtViewEditResultPlainText.setText(editResultPlainText);
            }
            editResultPassword = savedInstanceState.getString(EDIT_PASSWORD_STORAGE_KEY);
            if(editResultPassword != null && !editResultPassword.isEmpty())
            {
                txtViewEditResultPassword.setText(editResultPassword);
            }
            editResultEmail = savedInstanceState.getString(EDIT_EMAIL_STORAGE_KEY);
            if(editResultEmail != null && !editResultEmail.isEmpty())
            {
                txtViewEditResultEmail.setText(editResultEmail);
            }
            sliderResult = savedInstanceState.getInt(SLIDER_STORAGE_KEY);
            if(sliderResult != 0){
                txtViewSliderResult.setText(String.valueOf(sliderResult));
            }
        }
    }

    public void BtnPickerClick()
    {
        Intent pickerIntent = new Intent(this, PickerActivity.class);
        pickerIntent.putExtra(CONTENT_KEY, pickerResult);
        startActivityForResult(pickerIntent, REQUEST_PICKER_VIEW);
    }

    public void BtnEditClick()
    {
        Intent editIntent = new Intent(this, EditActivity.class);
        editIntent.putExtra(CONTENT_KEY_PLAINTEXT, editResultPlainText);
        editIntent.putExtra(CONTENT_KEY_PASSWORD, editResultPassword);
        editIntent.putExtra(CONTENT_KEY_EMAIL, editResultEmail);
        startActivityForResult(editIntent, REQUEST_EDIT_VIEW);
    }

    public void BtnSliderClick()
    {
        Intent sliderIntent = new Intent(this, SliderActivity.class);
        sliderIntent.putExtra(CONTENT_KEY, sliderResult);
        startActivityForResult(sliderIntent, REQUEST_SLIDER_VIEW);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_PICKER_VIEW && resultCode == RESULT_OK)
        {
            pickerResult = data.getExtras().getInt(PICKER_VIEW_RESULT);
            txtViewPickerResult.setText(String.valueOf(pickerResult));
        }
        if(requestCode == REQUEST_EDIT_VIEW && resultCode == RESULT_OK)
        {
            editResultPlainText = data.getExtras().getString(EDIT_VIEW_RESULT_PLAINTEXT);
            editResultEmail = data.getExtras().getString(EDIT_VIEW_RESULT_EMAIL);
            editResultPassword = data.getExtras().getString(EDIT_VIEW_RESULT_PASSWORD);
            ///PLAINTEXT
            if(editResultPlainText != null && !editResultPlainText.isEmpty())
                txtViewEditResultPlainText.setText(editResultPlainText);
            //PASSWORD
            if(editResultPassword != null && !editResultPassword.isEmpty())
                txtViewEditResultPassword.setText(editResultPassword);
            //EMAIL
            if(editResultEmail != null && !editResultEmail.isEmpty())
                txtViewEditResultEmail.setText(editResultEmail);
        }
        if(requestCode == REQUEST_SLIDER_VIEW && resultCode == RESULT_OK)
        {
            sliderResult = data.getExtras().getInt(SLIDER_VIEW_RESULT);
            txtViewSliderResult.setText(String.valueOf(sliderResult));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(PICKER_STORAGE_KEY, pickerResult);
        outState.putString(EDIT_PLAINTEXT_STORAGE_KEY, editResultPlainText);
        outState.putString(EDIT_EMAIL_STORAGE_KEY, editResultEmail);
        outState.putString(EDIT_PASSWORD_STORAGE_KEY, editResultPassword);
        outState.putInt(SLIDER_STORAGE_KEY, sliderResult);
    }
}
