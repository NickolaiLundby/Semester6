package nickolai.lundby.movielibrary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class OverviewActivity extends AppCompatActivity {

    // Variables

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

        // Listeners
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BtnExitClick();
            }
        });
    }

    private void BtnExitClick()
    {
        finish();
        System.exit(0);
    }
}
