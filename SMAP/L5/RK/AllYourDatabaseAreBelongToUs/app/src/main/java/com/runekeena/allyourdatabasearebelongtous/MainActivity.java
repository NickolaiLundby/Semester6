package com.runekeena.allyourdatabasearebelongtous;

import android.arch.persistence.room.Room;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText editTask;
    EditText editPlace;
    Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTask = findViewById(R.id.editTask);
        editPlace = findViewById(R.id.editPlace);
        add = findViewById(R.id.btnAdd);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskApp ta = (TaskApp)getApplicationContext();
                TaskDatabase db = ta.getTaskDatabase();
                Task newTask = new Task();
                newTask.setDescription(editTask.getText().toString());
                newTask.setPlace(editPlace.getText().toString());
                db.taskDao().insertAll(newTask);
            }
        });
    }

}
