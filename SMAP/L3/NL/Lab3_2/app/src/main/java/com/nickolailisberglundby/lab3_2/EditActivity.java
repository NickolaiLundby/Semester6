package com.nickolailisberglundby.lab3_2;

import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditActivity extends AppCompatActivity {
    Button btnToMainView;
    Button btnCancel;

    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        if(savedInstanceState == null){ }
        else
        {
            editText.setText(savedInstanceState.getString("userInput"));
        }

        btnToMainView = findViewById(R.id.btn_editOk);
        btnToMainView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnToMainViewClick();
            }
        });

        btnCancel = findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnCancelClick();
            }
        });

        editText = findViewById(R.id.editText_input);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putString("userInput", editText.getText().toString());
    }

    private void btnCancelClick() {
        Intent result = new Intent();
        setResult(RESULT_CANCELED, result);
        finish();
    }

    private void btnToMainViewClick() {
        Intent result = new Intent();
        result.putExtra("input", editText.getText().toString());
        setResult(RESULT_OK, result);
        finish();
    }
}
