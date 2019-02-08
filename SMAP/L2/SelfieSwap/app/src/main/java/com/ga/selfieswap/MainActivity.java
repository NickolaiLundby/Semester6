package com.ga.selfieswap;

import android.support.annotation.DrawableRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ImageView imgLeft;
    ImageView imgRight;
    Button bntSwap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgLeft = findViewById(R.id.imgleft);
        imgRight = findViewById(R.id.imgright);
        bntSwap = findViewById(R.id.bntswap);

        bntSwap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgLeft.setImageResource();
            }
        });
    }
}
