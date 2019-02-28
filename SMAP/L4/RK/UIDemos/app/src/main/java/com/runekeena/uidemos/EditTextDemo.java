package com.runekeena.uidemos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditTextDemo extends AppCompatActivity {

    // UI Elements
    EditText txtName;
    EditText txtEmail;
    EditText txtNumber;
    EditText txtPassword;
    Button btnOk;
    Button btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_text_demo);

        txtName = findViewById(R.id.txtName);
        /*txtName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtName.getText().clear();
            }
        });*/
        txtEmail = findViewById(R.id.txtEmail);
        /*txtEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtEmail.getText().clear();
            }
        });*/
        txtNumber = findViewById(R.id.txtNumber);
        /*txtNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtNumber.getText().clear();
            }
        });*/
        txtPassword = findViewById(R.id.txtPassword);
        /*txtPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtPassword.getText().clear();
            }
        });*/

        btnOk = findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent resultIntent= new Intent(EditTextDemo.this, MainActivity.class);
                resultIntent.putExtra("name", txtName.getText().toString());
                resultIntent.putExtra("email", txtEmail.getText().toString());
                resultIntent.putExtra("number", txtNumber.getText().toString());
                resultIntent.putExtra("password", txtPassword.getText().toString());
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

        btnCancel = findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent resultintent= new Intent(EditTextDemo.this, MainActivity.class);
                setResult(RESULT_CANCELED, resultintent);
                finish();
            }
        });





    }
}
