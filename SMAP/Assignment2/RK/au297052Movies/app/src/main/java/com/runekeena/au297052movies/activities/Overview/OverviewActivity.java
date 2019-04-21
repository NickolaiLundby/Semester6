package com.runekeena.au297052movies.activities.Overview;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.runekeena.au297052movies.R;
import com.runekeena.au297052movies.activities.DetailsActivity;
import com.runekeena.au297052movies.activities.EditActivity;
import com.runekeena.au297052movies.services.MovieService;
import com.runekeena.au297052movies.model.Movie;

public class OverviewActivity extends AppCompatActivity {

    //UI elements
    private Button btnExit;
    private Button btnAdd;
    private ListView listMovies;

    // Constants
    public final static String MOVIE_DETAILS = "movie_details";
    public final static String MOVIE_POSITION = "movie_position";
    private final static int REQUEST_EDIT = 10;
    private final static  String MOVIE_ARRAY_LIST = "movie_array_list";
    public final static String DATABASE_UPDATED = "database_updated";

    // Variables
    private MovieAdapter movieAdapter;
    MovieService movieService;
    private boolean bound = false;

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            setListView();
            Log.d("Broadcast", "Received in OverviewActivity");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        /*
        Intent startIntent = new Intent(this, MovieService.class);
        startIntent.setAction(MovieService.ACTION_START_FOREGROUND_SERVICE);
        startService(startIntent);
        */

        // Setup exit button
        btnExit = findViewById(R.id.btnExit);
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDialog();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(bound){
            setListView();
        } else {
            Intent i = new Intent(this, MovieService.class);
            bindService(i, connection, BIND_AUTO_CREATE);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        try
        {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(DATABASE_UPDATED);
            registerReceiver(receiver, intentFilter);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(bound){
            unbindService(connection);
        }
    }

    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            bound = true;
            MovieService.LocalBinder mLocalBinder = (MovieService.LocalBinder)service;
            movieService = mLocalBinder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            bound = false;
            movieService = null;
        }
    };

    private void setListView(){
        // Setup ListView
        movieAdapter = new MovieAdapter(this, movieService.getMovieList());
        listMovies = findViewById(R.id.listMovies);
        listMovies.setAdapter(movieAdapter);
        listMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startDetailActivity(parent, position);
            }
        });
        listMovies.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                startEditActivity(parent, position);
                return true;
            }
        });
    }
    private void addDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.DialogTheme);
        TextView title = new TextView(this);
        title.setText(R.string.add_movie);
        title.setTextAppearance(this, R.style.DialogTitleTheme);
        builder.setCustomTitle(title);

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton(getResources().getString(android.R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String s = input.getText().toString();
                movieService.addMovie(s);
            }
        });
        builder.setNegativeButton(getResources().getString(android.R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

    }

    private void startDetailActivity(AdapterView<?> parent, int position){
        Intent detailsIntent = new Intent(OverviewActivity.this, DetailsActivity.class);
        detailsIntent.setAction(DetailsActivity.ACTION_CURRENT);
        Movie m = (Movie) parent.getItemAtPosition(position);
        movieService.setCurrentMovie(m);
        startActivity(detailsIntent);
    }

    private void startEditActivity(AdapterView<?> parent, int position){
        Intent editIntent = new Intent(OverviewActivity.this, EditActivity.class);
        Movie m = (Movie) parent.getItemAtPosition(position);
        movieService.setCurrentMovie(m);
        startActivity(editIntent);
    }


}
