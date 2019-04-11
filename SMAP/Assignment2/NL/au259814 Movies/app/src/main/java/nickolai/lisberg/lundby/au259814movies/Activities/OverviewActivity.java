package nickolai.lisberg.lundby.au259814movies.Activities;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;

import nickolai.lisberg.lundby.au259814movies.Database.DatabaseApplication;
import nickolai.lisberg.lundby.au259814movies.Database.MovieDatabase;
import nickolai.lisberg.lundby.au259814movies.Models.Constants;
import nickolai.lisberg.lundby.au259814movies.Models.Movie;
import nickolai.lisberg.lundby.au259814movies.R;
import nickolai.lisberg.lundby.au259814movies.Services.MovieService;
import nickolai.lisberg.lundby.au259814movies.Utilities.CSVReader;
import nickolai.lisberg.lundby.au259814movies.Utilities.LocaleHelper;
import nickolai.lisberg.lundby.au259814movies.Utilities.MovieAdapter;


public class OverviewActivity extends AppCompatActivity {

    // Variables
    MovieAdapter movieAdapter;
    String mAddMovieTitle;

    // Service
    boolean mBound;
    MovieService mService;

    // Widgets
    Button btnExit, btnAdd;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        // Variable initialization
        LocaleHelper.onAttach(OverviewActivity.this);

        // Receiving from service
        RegisterMyReceiver();

        // Widget initialization
        btnExit = findViewById(R.id.overview_btnExit);
        btnAdd = findViewById(R.id.overview_btnAdd);
        listView = findViewById(R.id.overview_listview_movies);

        // Listeners
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BtnExitClick();
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BtnAddClick();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode){
            case Constants.REQUEST_EDIT:
                if (resultCode == RESULT_OK) {
                    ReadDatabase();
                }
            case Constants.REQUEST_DETAIL:
                if (resultCode == RESULT_CANCELED) {
                    ReadDatabase();
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

    private void ReadDatabase() {
        if(mBound)
            listView.setAdapter(new MovieAdapter(this, mService.getArrayOfMovies()));
        else
            Toast.makeText(this, getResources().getString(R.string.error_unbound_service), Toast.LENGTH_SHORT).show();
    }

    public void DetailsClick(Intent intent, Movie movie) {
        mService.setCurrentMovie(movie);
        startActivityForResult(intent, Constants.REQUEST_DETAIL);
    }

    public void EditClick(Intent intent, Movie movie) {
        mService.setCurrentMovie(movie);
        startActivityForResult(intent, Constants.REQUEST_EDIT);
    }

    private void BtnExitClick() {
        finish();
        System.exit(0);
    }

    private void BtnAddClick() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.prompt_button_title));

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton(getResources().getString(R.string.ButtonOkay), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mAddMovieTitle = input.getText().toString();
                mService.AddMovie(mAddMovieTitle);
            }
        });
        builder.setNegativeButton(getResources().getString(R.string.ButtonCancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void RegisterMyReceiver(){
        try
        {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(Constants.BROADCAST_DATABASE_UPDATED);
            registerReceiver(receiver, intentFilter);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ReadDatabase();
            Log.d(Constants.DEBUG_BROADCAST_TAG, Constants.DEBUG_BROADCAST_RECEIVED);
        }
    };
}
