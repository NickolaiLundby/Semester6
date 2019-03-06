package com.runekeena.allyourdatabasearebelongtous;

import android.arch.persistence.room.Room;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText editTask;
    EditText editPlace;
    Button add;
    ListView listView;

    // Variables
    ArrayList<Task> arrayOfTasks;
    TaskAdaptor taskAdapter;
    TaskDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TaskApp ta = (TaskApp)getApplicationContext();
        db = ta.getTaskDatabase();

        List<Task> tasks = db.taskDao().getAll();
        arrayOfTasks = new ArrayList<Task>();

        for(int i = 0; i < tasks.size(); i++){
            arrayOfTasks.add(tasks.get(i));
        }


        taskAdapter = new TaskAdaptor(this, arrayOfTasks);

        editTask = findViewById(R.id.editTask);
        editPlace = findViewById(R.id.editPlace);
        add = findViewById(R.id.btnAdd);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTask();
            }
        });


        listView = findViewById(R.id.listView);

        listView.setAdapter(taskAdapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Task delTask = (Task) parent.getItemAtPosition(position);
                removeTask(delTask);
                return true;

            }
        });

    }

    private void addTask(){
        //Toast.makeText(this, editPlace.getText().toString(), Toast.LENGTH_SHORT).show();
        Task newTask = new Task(0, editTask.getText().toString(), editPlace.getText().toString());
        //Toast.makeText(this, ""+newTask.getUid(), Toast.LENGTH_SHORT).show();
        db.taskDao().insertTask(newTask);
        taskAdapter.add(newTask);
        //Toast.makeText(this, newTask.getDescription() + " " + newTask.getPlace(), Toast.LENGTH_SHORT).show();
    }

    private void removeTask(Task task){
        db.taskDao().delete(task);
        taskAdapter.remove(task);
        //Toast.makeText(this, task.getDescription()+task.getPlace(), Toast.LENGTH_SHORT).show();
    }

}
