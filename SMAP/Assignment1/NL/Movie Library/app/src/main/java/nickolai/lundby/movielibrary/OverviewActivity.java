package nickolai.lundby.movielibrary;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Locale;


public class OverviewActivity extends AppCompatActivity {

    // Variables
    ArrayList<Movie> arrayOfMovies;
    MovieAdapter movieAdapter;
    CSVReader csvReader;
    MovieDatabase db;

    // Widgets
    Button btnExit;
    ListView listView;

    // Request constants
    public final static int REQUEST_EDIT = 100;
    public final static int REQUEST_DETAIL = 101;

    // Result constants
    public final static String RESULT_EDIT = "resultFromEdit";

    // Storage constants
    public final static String STORAGE_DETAIL = "storageDetail";

    // Content keys
    public final static String MOVIE_DETAILS_CONTENT = "movieDetailsContent";
    public final static String MOVIE_EDIT_CONTENT = "movieEditContent";
    public final static String MOVIE_POSITION = "moviePosition";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        // Variable initialization
        LocaleClass.onAttach(OverviewActivity.this);
        DatabaseApplication dba = (DatabaseApplication) getApplicationContext();
        db = dba.GetDatabase();
        arrayOfMovies = new ArrayList<>(db.movieDao().getAll());
        if(arrayOfMovies.isEmpty())
        {
            InputStream inputStream = getResources().openRawResource(R.raw.movielist);
            csvReader = new CSVReader(inputStream);
            arrayOfMovies = csvReader.read();
            for(Movie m : arrayOfMovies)
                db.movieDao().insertMovie(m);
        }
        movieAdapter = new MovieAdapter(this, arrayOfMovies);

        // Widget initialization
        btnExit = findViewById(R.id.overview_btnExit);
        listView = findViewById(R.id.overview_listview_movies);
        listView.setAdapter(movieAdapter);

        // Listeners
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BtnExitClick();
            }
        });
    }

    public void DetailsClick(Intent intent)
    {
        startActivityForResult(intent, REQUEST_DETAIL);
    }

    public void EditClick(Intent intent)
    {
        startActivityForResult(intent, REQUEST_EDIT);
    }

    private void BtnExitClick()
    {
        finish();
        System.exit(0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode){
            case REQUEST_EDIT:
                if (resultCode == RESULT_OK) {
                    int i = data.getExtras().getInt(MOVIE_POSITION);
                    Movie add = data.getExtras().getParcelable(RESULT_EDIT);
                    movieAdapter.remove(movieAdapter.getItem(i));
                    movieAdapter.insert(add, i);
                    db.movieDao().update(add);
                }
            default:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.movie_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) item.getActionView();
        MenuItem item2 = menu.findItem(R.id.select_language);
        Button languageBtn = (Button)item2.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                movieAdapter.getFilter().filter(newText);
                return true;
            }
        });

        languageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(LocaleClass.getLanguage(OverviewActivity.this)){
                    case "en":
                        LocaleClass.setLocale(OverviewActivity.this, "dk");
                        recreate();
                        break;
                    case "dk":
                        LocaleClass.setLocale(OverviewActivity.this, "en");
                        recreate();
                        break;
                    default:
                        LocaleClass.setLocale(OverviewActivity.this, "en");
                        recreate();
                        break;
                }
            }
        });
        return true;
    }
}
