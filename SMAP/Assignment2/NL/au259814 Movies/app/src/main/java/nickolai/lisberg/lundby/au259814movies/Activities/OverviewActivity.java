package nickolai.lisberg.lundby.au259814movies.Activities;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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

import nickolai.lisberg.lundby.au259814movies.Database.DatabaseApplication;
import nickolai.lisberg.lundby.au259814movies.Database.MovieDatabase;
import nickolai.lisberg.lundby.au259814movies.Models.Movie;
import nickolai.lisberg.lundby.au259814movies.R;
import nickolai.lisberg.lundby.au259814movies.Services.MovieService;
import nickolai.lisberg.lundby.au259814movies.Utilities.CSVReader;
import nickolai.lisberg.lundby.au259814movies.Utilities.LocaleHelper;
import nickolai.lisberg.lundby.au259814movies.Utilities.MovieAdapter;


public class OverviewActivity extends AppCompatActivity {

    // Variables
    ArrayList<Movie> arrayOfMovies;
    MovieAdapter movieAdapter;
    CSVReader csvReader;
    MovieDatabase db;

    // Service
    boolean mBound;
    MovieService mService;

    // Widgets
    Button btnExit;
    ListView listView;

    // Request constants
    public final static int REQUEST_EDIT = 100;
    public final static int REQUEST_DETAIL = 101;

    // Result constants
    public final static String RESULT_EDIT = "Result.Helper.EditActivity";

    // Storage constants
    public final static String STORAGE_DETAIL = "Storage.Helper.DetailActivity";

    // Content keys
    public final static String MOVIE_DETAILS_TITLE = "Content.Helper.Movie.Details.Title";
    public final static String MOVIE_DETAILS_CONTENT = "Content.Helper.Movie.Details";
    public final static String MOVIE_EDIT_CONTENT = "Content.Helper.Movie.Edit";
    public final static String MOVIE_POSITION = "Content.Helper.Movie.Position";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        // Variable initialization
        LocaleHelper.onAttach(OverviewActivity.this);

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
    }

    private void ReadDatabase() {
        if(mBound)
            listView.setAdapter(new MovieAdapter(this, mService.GetAllMovies()));
        else
            Toast.makeText(this, "Not bound to service", Toast.LENGTH_SHORT).show();
    }

    public void DetailsClick(Intent intent)
    {
        startActivityForResult(intent, REQUEST_DETAIL);
    }

    public void EditClick(Intent intent)
    {
        startActivityForResult(intent, REQUEST_EDIT);
    }

    private void BtnExitClick() {
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
        final SearchView searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (searchView.getQuery().toString().isEmpty() || searchView.getQuery().toString() == "") {
                    movieAdapter.getFilter().filter("");
                }
                movieAdapter.getFilter().filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.isEmpty() || newText == ""){
                    this.onQueryTextSubmit("");
                }
                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.select_language){
            String s = LocaleHelper.getLanguage(OverviewActivity.this);
            switch(LocaleHelper.getLanguage(OverviewActivity.this)){
                case "en":
                    LocaleHelper.setLocale(OverviewActivity.this, "da");
                    recreate();
                    break;
                case "da":
                    LocaleHelper.setLocale(OverviewActivity.this, "en");
                    recreate();
                    break;
                default:
                    LocaleHelper.setLocale(OverviewActivity.this, "en");
                    recreate();
                    break;
            }
        }
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent mIntent = new Intent(this, MovieService.class);
        bindService(mIntent, mConnection, BIND_AUTO_CREATE);
    }

    ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBound = true;
            MovieService.LocalBinder mLocalBinder = (MovieService.LocalBinder)service;
            mService = mLocalBinder.getServiceInstance();
            ReadDatabase();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBound = false;
            mService = null;
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        if(mBound) {
            unbindService(mConnection);
            mBound = false;
        }
    }
}
