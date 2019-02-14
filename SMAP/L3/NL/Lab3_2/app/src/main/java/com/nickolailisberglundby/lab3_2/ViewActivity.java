package com.nickolailisberglundby.lab3_2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
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
    Button btnCamera;
    TextView txtResponseEditView;
    ImageView imageView;
    Bitmap myBitmap;
    String txtViewContent;

    //Request constants
    public final static int REQUEST_TO_EDIT_VIEW = 100;
    public final static int REQUEST_TO_CAMERA = 101;

    //Other constants
    public final static String BITMAP_STORAGE_KEY = "bitmapstorekey";
    public final static String TXTVIEWCONTENT_STORAGE_KEY = "txtviewstorekey";
    public final static String EDITTEXTCONTENT_STORAGE_KEY = "edittextstorekey";

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

        btnCamera = findViewById(R.id.btn_toCameraView);
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnCameraClick();
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

    private void btnCameraClick()
    {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, REQUEST_TO_CAMERA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode){
            case REQUEST_TO_EDIT_VIEW:
                switch(resultCode){
                    case RESULT_OK:
                        txtViewContent = data.getExtras().getString("input");
                        txtResponseEditView.setText(txtViewContent);
                        break;
                    case RESULT_CANCELED:
                        break;

                    default:
                        break;
                }
            case REQUEST_TO_CAMERA:
                switch(resultCode){
                    case RESULT_OK:
                        myBitmap = (Bitmap)data.getExtras().get("data");
                        imageView.setImageBitmap(myBitmap);

                    case RESULT_CANCELED:
                        break;

                    default:
                        break;
                }
            default:
                break;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(BITMAP_STORAGE_KEY, myBitmap);
        outState.putString(TXTVIEWCONTENT_STORAGE_KEY, txtViewContent);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        myBitmap = savedInstanceState.getParcelable(BITMAP_STORAGE_KEY);
        txtViewContent = savedInstanceState.getString(TXTVIEWCONTENT_STORAGE_KEY);
        imageView.setImageBitmap(myBitmap);
        txtResponseEditView.setText(txtViewContent);
    }
}
