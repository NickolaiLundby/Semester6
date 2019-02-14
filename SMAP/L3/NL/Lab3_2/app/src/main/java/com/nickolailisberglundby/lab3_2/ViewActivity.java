package com.nickolailisberglundby.lab3_2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ViewActivity extends AppCompatActivity {
    Button btnToEditView;
    Button btnToCameraView;
    TextView txtResponseEditView;
    ImageView imageView;

    public final static int REQUEST_TO_EDIT_VIEW = 100;
    public final static int REQUEST_TO_CAMERA_VIEW = 101;

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

        btnToCameraView = findViewById(R.id.btn_toCameraView);
        btnToCameraView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnToCameraViewClick();
            }
        });

        imageView = findViewById(R.id.img_viewActivity);

        txtResponseEditView = findViewById(R.id.textView_viewActivity);
    }

    private void btnToEditViewClick()
    {
        Intent toEditView = new Intent(this, EditActivity.class);
        startActivityForResult(toEditView, REQUEST_TO_EDIT_VIEW);
    }

    private void btnToCameraViewClick()
    {
        Intent toCameraView = new Intent(this, CameraActivity.class);
        startActivityForResult(toCameraView, REQUEST_TO_CAMERA_VIEW);
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
            case REQUEST_TO_CAMERA_VIEW:
                switch(resultCode){
                    case RESULT_OK:
                        Bitmap photo = (Bitmap) data.getParcelableExtra("picture");
                        imageView.setImageBitmap(photo);

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
