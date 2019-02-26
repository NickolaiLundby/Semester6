package com.example.whyisntthisworking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditActivity extends AppCompatActivity {

    // Widgets
    Button btnOkay;
    Button btnCancel;
    EditText editTextPlain;
    EditText editTextEmail;
    EditText editTextPassword;

    // Variables
    String resultText;
    String plainTextFromMain;
    String emailTextFromMain;
    String passwordTextFromMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit2);

        // Variable initialization
        plainTextFromMain = getIntent().getStringExtra(MainActivity.CONTENT_KEY_PLAINTEXT);
        emailTextFromMain = getIntent().getStringExtra(MainActivity.CONTENT_KEY_EMAIL);
        passwordTextFromMain = getIntent().getStringExtra(MainActivity.CONTENT_KEY_PASSWORD);
        resultText = "";

        //Widget initialization
        btnOkay = findViewById(R.id.button_edit_okay);
        btnCancel = findViewById(R.id.button_edit_cancel);
        editTextPlain = findViewById(R.id.editText_edit_textinput);
        if (plainTextFromMain != null && !plainTextFromMain.isEmpty())
            editTextPlain.setText(plainTextFromMain);
        editTextEmail = findViewById(R.id.editText_edit_emailinput);
        if (emailTextFromMain != null && !emailTextFromMain.isEmpty())
            editTextEmail.setText(emailTextFromMain);
        editTextPassword = findViewById(R.id.editText_edit_passwordinput);
        if (passwordTextFromMain != null && !passwordTextFromMain.isEmpty())
            editTextPassword.setText(passwordTextFromMain);

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

    public void BtnCancelClick()
    {
        Intent resultIntent = new Intent();
        setResult(RESULT_CANCELED, resultIntent);
        finish();
    }

    public void BtnOkayClick()
    {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(MainActivity.EDIT_VIEW_RESULT_PLAINTEXT, editTextPlain.getText().toString());
        resultIntent.putExtra(MainActivity.EDIT_VIEW_RESULT_EMAIL, editTextEmail.getText().toString());
        resultIntent.putExtra(MainActivity.EDIT_VIEW_RESULT_PASSWORD, editTextPassword.getText().toString());
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}
