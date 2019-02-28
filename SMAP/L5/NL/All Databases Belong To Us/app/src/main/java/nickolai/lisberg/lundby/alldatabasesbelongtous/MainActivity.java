package nickolai.lisberg.lundby.alldatabasesbelongtous;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Widgets
    Button btnAdd;
    EditText editTask, editPlace;

    // Variables
    List<Task> taskList;
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Widget initialization
        btnAdd = findViewById(R.id.button_add);
        editTask = findViewById(R.id.et_task);
        editPlace = findViewById(R.id.et_place);

        // Variable initialization
        DatabaseApplication dba = (DatabaseApplication) getApplicationContext();
        db = dba.GetDatabase();

        // Listeners
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BtnAddClick();
            }
        });
    }

    private void BtnAddClick()
    {
        Task addTask = new Task(editPlace.getText().toString(), editTask.getText().toString());
    }
}
