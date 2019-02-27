package nickolai.lundby.movielibrary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class OverviewActivity extends AppCompatActivity {

    // Variables
    private List<MovieSample> movieList = new ArrayList<>();

    // Widgets
    Button btnExit;
    ListView listView;

    // Request constants
    public final static int REQUEST_EDIT = 100;
    public final static int REQUEST_DETAIL = 101;

    // Result constants
    public final static String RESULT_EDIT = "resultFromEdit";
    public final static String RESULT_DETAIL = "resultFromDetail";

    // Storage constants
    public final static String STORAGE_EDIT = "storageEdit";
    public final static String STORAGE_DETAIL = "storageDetail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        // Variable initialization

        // Widget initialization
        btnExit = findViewById(R.id.overview_btnExit);
        listView = findViewById(R.id.overview_listview_movies);

        // Listeners
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BtnExitClick();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Load data from listView and add it to intent
                Intent detailsIntent = new Intent(OverviewActivity.this, DetailsActivity.class);
                startActivityForResult(detailsIntent, REQUEST_DETAIL);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Intent editIntent = new Intent(OverviewActivity.this, EditActivity.class);
                startActivityForResult(editIntent, REQUEST_EDIT);
                return true;
            }
        });
    }

    private void PrepareCsv()
    {
        // TODO: https://www.youtube.com/watch?v=i-TqNzUryn8
        MovieSample sample;
    }

    private void BtnExitClick()
    {
        finish();
        System.exit(0);
    }
}
