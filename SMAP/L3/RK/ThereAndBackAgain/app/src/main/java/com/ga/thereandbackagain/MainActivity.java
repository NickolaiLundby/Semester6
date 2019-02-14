package com.ga.thereandbackagain;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button bnt_gotosecond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bnt_gotosecond = findViewById(R.id.bnt_gotosecond);

        bnt_gotosecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Intent secondintent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(secondintent);
            }
        });
    }
}
