package nickolai.lundby.movielibrary;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
    String currentLanguage = "en";
    Locale myLocale;

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

    // Content keys
    public final static String MOVIE_DETAILS_CONTENT = "movieDetailsContent";
    public final static String MOVIE_EDIT_CONTENT = "movieEditContent";
    public final static String MOVIE_POSITION = "moviePosition";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        // Variable initialization
        currentLanguage = currentLanguage = getIntent().getStringExtra("currentLanguage");
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

    public void setLocale(String localeName) {
        if (!localeName.equals(currentLanguage)) {
            myLocale = new Locale(localeName);
            Resources res = getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            Configuration conf = res.getConfiguration();
            conf.locale = myLocale;
            res.updateConfiguration(conf, dm);
            Intent refresh = new Intent(this, OverviewActivity.class);
            refresh.putExtra("currentLanguage", localeName);
            startActivity(refresh);
        } else {
            Toast.makeText(OverviewActivity.this, "Language already selected!", Toast.LENGTH_SHORT).show();
        }
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
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search);
        Button languageBtn = (Button)menu.findItem(R.id.select_language);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(OverviewActivity.this, "Now searching for " + query, Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        languageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(currentLanguage){
                    case "en":
                        setLocale("dk");
                        break;
                    case "dk":
                        setLocale("en");
                        break;
                    default:
                        setLocale("en");
                }
            }
        });
        return true;
    }
}
