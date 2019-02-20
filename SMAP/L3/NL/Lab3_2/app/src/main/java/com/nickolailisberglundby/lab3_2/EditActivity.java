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
    String editTextContent;

    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

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

        String s = getIntent().getStringExtra("text");

        if(s != null && !s.isEmpty())
            editText.setText(s);


        if (savedInstanceState != null)
        {
            editTextContent = savedInstanceState.getString(ViewActivity.EDITTEXTCONTENT_STORAGE_KEY);
            editText.setText(editTextContent);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putString(ViewActivity.EDITTEXTCONTENT_STORAGE_KEY, editText.getText().toString());
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        editTextContent = savedInstanceState.getString(ViewActivity.EDITTEXTCONTENT_STORAGE_KEY);
        editText.setText(editTextContent);
        super.onRestoreInstanceState(savedInstanceState);
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
