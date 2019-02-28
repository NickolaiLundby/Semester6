package com.runekeena.uidemos;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // UI elements
    Button btnPicker;
    Button btnEditTxt;
    Button btnSliders;


    // Request constants
    final static int REQUEST_NUMBER = 20;
    final static int REQUEST_TEXT = 100;
    final  static  int REQUEST_COLOUR = 120;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPicker = findViewById(R.id.btnPicker);
        btnPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent getNumber = new Intent(MainActivity.this, PickerDemo.class);
                startActivityForResult(getNumber, REQUEST_NUMBER);
            }
        });

        btnEditTxt = findViewById(R.id.btnEditTxt);
        btnEditTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent getText = new Intent(MainActivity.this, EditTextDemo.class);
                startActivityForResult(getText, REQUEST_TEXT);
            }
        });

        btnSliders = findViewById(R.id.btnSliders);
        btnSliders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent getColour = new Intent(MainActivity.this, SlidersDemo.class);
                startActivityForResult(getColour, REQUEST_COLOUR);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case REQUEST_NUMBER: if (resultCode == RESULT_OK)
                Toast.makeText(this, "Number selected: "+data.getExtras().getInt("number"), Toast.LENGTH_SHORT).show();
                                else
                Toast.makeText(this, "Pick number cancelled", Toast.LENGTH_SHORT).show();
                break;

            case REQUEST_TEXT: if (resultCode == RESULT_OK)
                Toast.makeText(this, "Name: "+ data.getExtras().getString("name")+" E-mail :"+data.getExtras().getString("email")
                        +" Phone number: "+data.getExtras().getString("number")+" Password: "+data.getExtras().getString("password"), Toast.LENGTH_LONG).show();
                    else
                Toast.makeText(this, "Edit Text cancelled", Toast.LENGTH_SHORT).show();
                break;

            case REQUEST_COLOUR: if (resultCode == RESULT_OK)
                Toast.makeText(this, "Colour: "+ data.getExtras().getString("colour"), Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "Colour selection cancelled", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.mainmenu, menu);
        MenuItem search = menu.findItem(R.id.search);
        SearchView sv = (SearchView)search.getActionView();

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(MainActivity.this, "now searching for " + query, Toast.LENGTH_SHORT).show();
                return true;
        }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }
}
