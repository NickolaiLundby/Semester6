package com.nickolailisberglundby.lab3_2;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ViewActivity extends AppCompatActivity {
    Button btnToEditView;
    TextView txtResponseEditView;
    public final static int REQUEST_TO_EDIT_VIEW = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        btnToEditView = findViewById(R.id.btn_toEditView);
        btnToEditView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnToEditViewClick();
            }
        });

        txtResponseEditView = findViewById(R.id.textView_viewActivity);
    }

    private void btnToEditViewClick()
    {
        Intent toEditView = new Intent(this, EditActivity.class);
        startActivityForResult(toEditView, REQUEST_TO_EDIT_VIEW);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode){
            case REQUEST_TO_EDIT_VIEW:
                switch(resultCode){
                    case RESULT_OK:
                        txtResponseEditView.setText(data.getExtras().getString("input"));
                        break;
                    case RESULT_CANCELED:
                        break;

                    default:
                        break;
                }
            default:
                break;
        }
    }
}
