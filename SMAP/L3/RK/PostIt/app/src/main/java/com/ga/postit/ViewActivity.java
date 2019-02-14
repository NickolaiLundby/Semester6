package com.ga.postit;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ViewActivity extends AppCompatActivity {

    Button btn_edit;
    TextView txt_view;

    final static int REQUEST_TEXT = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        btn_edit = findViewById(R.id.btn_ok);
        txt_view = findViewById(R.id.txt_view);

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent getText = new Intent(ViewActivity.this, EditActivity.class);
                startActivityForResult(getText, REQUEST_TEXT);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case REQUEST_TEXT: if (resultCode == RESULT_OK)
                txt_view.setText(data.getExtras().getString("result"));
            }
        }
}
