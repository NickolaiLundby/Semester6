package com.ga.imageswap;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button bntImgLeft;
    Button bntSwap;
    ImageView imgViewLeft;
    ImageView imgViewRight;
    Bitmap myBitmap1;
    Bitmap myBitmap2;

    //Constants
    public final static int REQUEST_TO_CAMERA = 101;
    public final static int CAMERA_PERMISSION_CODE = 102;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgViewLeft = findViewById(R.id.imgViewLeft);
        imgViewRight = findViewById(R.id.imgViewRight);

        if(savedInstanceState != null){
            myBitmap1 = savedInstanceState.getParcelable("mybitmap1");
            myBitmap2 = savedInstanceState.getParcelable("mybitmap2");
            imgViewLeft.setImageBitmap(myBitmap1);
            imgViewRight.setImageBitmap(myBitmap2);
        }

        bntImgLeft = findViewById(R.id.bntImgLeft);
        bntImgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraRequest();

            }
        });

        bntSwap = findViewById(R.id.btnSwap);
        bntSwap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap tempBitmap = myBitmap1;
                myBitmap1 = myBitmap2;
                myBitmap2 = tempBitmap;
                imgViewLeft.setImageBitmap(myBitmap1);
                imgViewRight.setImageBitmap(myBitmap2);

            }
        });

    }

    private void cameraRequest() {

        if(getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            // Permissions
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
            } else {
                // Camera request
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, REQUEST_TO_CAMERA);
            }
        }
        else
            Toast.makeText(this, "Phone does not have camera feature", Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode){
            case REQUEST_TO_CAMERA:
                switch(resultCode){
                    case RESULT_OK:
                        myBitmap1 = (Bitmap)data.getExtras().get("data");
                        imgViewLeft.setImageBitmap(myBitmap1);

                    case RESULT_CANCELED:
                        break;

                    default:
                        break;
                }
            default:
                break;
        }
    }

    @Override public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("mybitmap1", myBitmap1);
        outState.putParcelable("mybitmap2", myBitmap2);
        super.onSaveInstanceState(outState);
    }
}
