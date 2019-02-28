package com.runekeena.whome;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    // UI elements
    EditText firstName;
    EditText lastName;
    EditText age;
    EditText phone;

    //
    private static String MY_DATA_NAME = "MyDataName";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Edit text
        firstName = findViewById(R.id.editTxtFirstName);
        lastName = findViewById(R.id.editTxtLastName);
        age = findViewById(R.id.editTxtAge);
        phone = findViewById(R.id.editTxtPhone);

        // Shared preferences
        SharedPreferences prefs = getSharedPreferences(MY_DATA_NAME, MODE_PRIVATE);
        String strFirstName = prefs.getString("firstName", "First Name");
        firstName.setText(strFirstName);
        String strLastName = prefs.getString("lastName", "Last Name");
        lastName.setText(strLastName);
        int intAge = prefs.getInt("age", 0);
        age.setText(String.valueOf(intAge));
        int intPhone = prefs.getInt("phone", 12345678);
        phone.setText(String.valueOf(intPhone));

    }

    @Override
    protected void onStop() {
        super.onStop();
        // Shared preferences
        SharedPreferences.Editor editor = getSharedPreferences(MY_DATA_NAME, MODE_PRIVATE).edit();
        editor.putString("firstName", firstName.getText().toString());
        editor.putString("lastName", lastName.getText().toString());
        editor.putInt("age", Integer.parseInt(age.getText().toString()));
        editor.putInt("phone", Integer.parseInt(phone.getText().toString()));
        editor.apply();
    }
}
