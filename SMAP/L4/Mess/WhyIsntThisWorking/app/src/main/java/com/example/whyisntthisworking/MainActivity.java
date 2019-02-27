package com.example.whyisntthisworking;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

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

    ListView listView;

    // Variables
    int pickerResult;
    String editResultPlainText;
    String editResultPassword;
    String editResultEmail;
    int sliderResultRed;
    int sliderResultBlue;
    int sliderResultGreen;
    int backgroundColor;
    ArrayAdapter<String> adapter;
    ArrayList<String> demoList;

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
    public final static String SLIDER_VIEW_RESULT_RED = "sliderViewResultRed";
    public final static String SLIDER_VIEW_RESULT_GREEN = "sliderViewResultGreen";
    public final static String SLIDER_VIEW_RESULT_BLUE = "sliderViewResultBlue";
    // Storage
    public final static String PICKER_STORAGE_KEY = "pickerStorage";
    public final static String EDIT_PLAINTEXT_STORAGE_KEY = "editStoragePlainText";
    public final static String EDIT_PASSWORD_STORAGE_KEY = "editStorageEmail";
    public final static String EDIT_EMAIL_STORAGE_KEY = "editStoragePassword";
    public final static String SLIDER_RED_STORAGE_KEY = "sliderRedStorage";
    public final static String SLIDER_BLUE_STORAGE_KEY = "sliderBlueStorage";
    public final static String SLIDER_GREEN_STORAGE_KEY = "sliderGreenStorage";
    // Global content key
    public final static String CONTENT_KEY = "contentFromMain";
    public final static String CONTENT_KEY_EMAIL = "emailContentFromMain";
    public final static String CONTENT_KEY_PASSWORD = "passwordContentFromMain";
    public final static String CONTENT_KEY_PLAINTEXT = "plainTextContentFromMain";
    public final static String CONTENT_KEY_RED = "redContentFromMain";
    public final static String CONTENT_KEY_BLUE = "blueContentFromMain";
    public final static String CONTENT_KEY_GREEN = "greenContentFromMain";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Variable initialization
        pickerResult = 0;
        sliderResultBlue = 0;
        sliderResultGreen = 0;
        sliderResultRed = 0;
        backgroundColor = 0;
        demoList = new ArrayList<>();
        demoList.addAll(Arrays.asList(getResources().getStringArray(R.array.arrayDemoList)));
        adapter = new ArrayAdapter<String>(
                MainActivity.this,
                android.R.layout.simple_list_item_1,
                demoList);

        // Widget initialization
        btnPicker = findViewById(R.id.btn_main);
        btnEdit = findViewById(R.id.btn_main2);
        btnSlider = findViewById(R.id.btn_main3);
        txtViewPickerResult = findViewById(R.id.textView_pickerView_result);
        txtViewEditResultPlainText = findViewById(R.id.textView_edit_result_plaintext);
        txtViewEditResultEmail = findViewById(R.id.textView_edit_result_email);
        txtViewEditResultPassword = findViewById(R.id.textView_edit_result_password);
        txtViewSliderResult = findViewById(R.id.textView_slider_result);
        listView = findViewById(R.id.listViewMain);
        listView.setAdapter(adapter);

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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                switch(selectedItem)
                {
                    case "Picker Demo":
                        BtnPickerClick();
                        break;
                    case "Slider Demo":
                        BtnSliderClick();
                        break;
                    case "EditText Demo":
                        BtnEditClick();
                        break;
                    case "Main":
                        break;
                }
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
            sliderResultRed = savedInstanceState.getInt(SLIDER_RED_STORAGE_KEY);
            sliderResultGreen = savedInstanceState.getInt(SLIDER_GREEN_STORAGE_KEY);
            sliderResultBlue = savedInstanceState.getInt(SLIDER_BLUE_STORAGE_KEY);
            SetBackgroundColor(sliderResultRed, sliderResultGreen, sliderResultBlue);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.my_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView)item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(MainActivity.this, "Now searching for " + query, Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
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
        sliderIntent.putExtra(CONTENT_KEY_BLUE, sliderResultBlue);
        sliderIntent.putExtra(CONTENT_KEY_GREEN, sliderResultGreen);
        sliderIntent.putExtra(CONTENT_KEY_RED, sliderResultRed);
        startActivityForResult(sliderIntent, REQUEST_SLIDER_VIEW);
    }

    public void SetBackgroundColor(int red, int green, int blue)
    {
        backgroundColor = 0xff000000 + red * 0x10000 + green * 0x100 + blue;
        txtViewSliderResult.setBackgroundColor(backgroundColor);
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
            sliderResultRed = data.getExtras().getInt(SLIDER_VIEW_RESULT_RED);
            sliderResultBlue = data.getExtras().getInt(SLIDER_VIEW_RESULT_BLUE);
            sliderResultGreen = data.getExtras().getInt(SLIDER_VIEW_RESULT_GREEN);
            SetBackgroundColor(sliderResultRed, sliderResultGreen, sliderResultBlue);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(PICKER_STORAGE_KEY, pickerResult);
        outState.putString(EDIT_PLAINTEXT_STORAGE_KEY, editResultPlainText);
        outState.putString(EDIT_EMAIL_STORAGE_KEY, editResultEmail);
        outState.putString(EDIT_PASSWORD_STORAGE_KEY, editResultPassword);
        outState.putInt(SLIDER_RED_STORAGE_KEY, sliderResultRed);
        outState.putInt(SLIDER_GREEN_STORAGE_KEY, sliderResultGreen);
        outState.putInt(SLIDER_BLUE_STORAGE_KEY, sliderResultBlue);
    }
}
