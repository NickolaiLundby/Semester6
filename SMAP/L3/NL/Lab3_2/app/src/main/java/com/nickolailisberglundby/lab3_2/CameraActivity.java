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

public class CameraActivity extends AppCompatActivity {
    Button photoButton;

    public final static int CAMERA_REQUEST = 1888;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        photoButton = findViewById(R.id.btn_photo);
        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Intent result = new Intent();

        if(requestCode == CAMERA_REQUEST && resultCode == RESULT_OK){
            Bitmap photo = (Bitmap)data.getExtras().get("data");
            result.putExtra("picture", photo);
            setResult(RESULT_OK);
            finish();
        }
        else
            setResult(RESULT_CANCELED);
            finish();
    }
}
