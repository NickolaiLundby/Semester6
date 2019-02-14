package com.ga.postit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EditActivity extends AppCompatActivity {

    Button btn_ok;
    Button btn_cancel;
    EditText txt_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        btn_ok = findViewById(R.id.btn_ok);
        btn_cancel = findViewById(R.id.btn_cancel);
        txt_edit = findViewById(R.id.txt_edit);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent resultintent= new Intent(EditActivity.this, ViewActivity.class);
                String result = txt_edit.getText().toString();
                resultintent.putExtra("result", result);
                setResult(RESULT_OK, resultintent);
                finish();
            }
        });
    }
}
