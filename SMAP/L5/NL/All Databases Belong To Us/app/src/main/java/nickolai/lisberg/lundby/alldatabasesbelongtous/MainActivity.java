package nickolai.lisberg.lundby.alldatabasesbelongtous;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Widgets
    Button btnAdd;
    EditText editTask, editPlace;
    ListView listView;

    // Variables
    ArrayList<Task> arrayOfTasks;
    TaskAdapter taskAdapter;
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Variable initialization
        DatabaseApplication dba = (DatabaseApplication) getApplicationContext();
        db = dba.GetDatabase();
        List<Task> tasks = db.taskDao().getAll();
        arrayOfTasks = new ArrayList<Task>();

        for(int i = 0; i < tasks.size(); i++){
            arrayOfTasks.add(tasks.get(i));
        }

        taskAdapter = new TaskAdapter(MainActivity.this, arrayOfTasks);

        // Widget initialization
        btnAdd = findViewById(R.id.button_add);
        editTask = findViewById(R.id.et_task);
        editPlace = findViewById(R.id.et_place);
        listView = findViewById(R.id.listview);
        listView.setAdapter(taskAdapter);

        // Listeners
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BtnAddClick();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Task t = (Task) parent.getItemAtPosition(position);
                db.taskDao().delete(t);
                taskAdapter.remove(t);
                return true;
            }
        });
    }

    private void BtnAddClick()
    {
        List<String> ls = new ArrayList<>();
        ls.add("En");
        ls.add("To");
        ls.add("Tre");
        Task addTask = new Task(editPlace.getText().toString(), editTask.getText().toString(), ls);
        db.taskDao().insertTask(addTask);
        taskAdapter.add(addTask);
    }
}
