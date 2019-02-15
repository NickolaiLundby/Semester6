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

        if(savedInstanceState.getString(ViewActivity.EDITTEXTCONTENT_STORAGE_KEY) != null)
        {
            editText.setText(savedInstanceState.getString(ViewActivity.EDITTEXTCONTENT_STORAGE_KEY));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putString(ViewActivity.EDITTEXTCONTENT_STORAGE_KEY, editText.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        editTextContent = savedInstanceState.getString(ViewActivity.EDITTEXTCONTENT_STORAGE_KEY);
        editText.setText(editTextContent);
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
