package com.runekeena.movietracker.activities.Overview;

import android.content.Intent;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.runekeena.movietracker.activities.DetailsActivity;
import com.runekeena.movietracker.activities.EditActivity;
import com.runekeena.movietracker.helpers.CSVReader;
import com.runekeena.movietracker.model.Movie;
import com.runekeena.movietracker.R;

import java.io.InputStream;
import java.util.ArrayList;

public class OverviewActivity extends AppCompatActivity {

    //UI elements
    private Button btnExit;
    private ListView listMovies;

    // Constants
    public final static String MOVIE_DETAILS = "movie_details";
    public final static String MOVIE_POSITION = "movie_position";
    private final static int REQUEST_EDIT = 10;
    private final static  String MOVIE_ARRAY_LIST = "movie_array_list";

    // Variables
    private MovieAdapter movieAdapter;
    private ArrayList<Movie> movieArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        // get saved instance state or load movie data from CSV file
        if(savedInstanceState == null){
            InputStream inputStream = this.getResources().openRawResource(R.raw.movielist);
            CSVReader csvReader = new CSVReader();
            movieArrayList = csvReader.readMovieData(inputStream);
        } else {
            movieArrayList = savedInstanceState.getParcelableArrayList(MOVIE_ARRAY_LIST);
        }

        // Setup ListView
        movieAdapter = new MovieAdapter(this, movieArrayList);
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

        // Setup exit button
        btnExit = findViewById(R.id.btnExit);
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void startDetailActivity(AdapterView<?> parent, int position){
        Intent detailsIntent = new Intent(OverviewActivity.this, DetailsActivity.class);
        Movie m = (Movie) parent.getItemAtPosition(position);
        detailsIntent.putExtra("movie_details", m);
        startActivity(detailsIntent);
    }

    private void startEditActivity(AdapterView<?> parent, int position){
        Intent editIntent = new Intent(OverviewActivity.this, EditActivity.class);
        Movie m = (Movie) parent.getItemAtPosition(position);
        editIntent.putExtra(MOVIE_DETAILS, m);
        editIntent.putExtra(MOVIE_POSITION, position);
        startActivityForResult(editIntent, REQUEST_EDIT);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(MOVIE_ARRAY_LIST, movieArrayList);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case REQUEST_EDIT: if(resultCode == RESULT_OK) {
                Movie m = (Movie) data.getExtras().getParcelable(MOVIE_DETAILS);
                int p = (int) data.getExtras().getInt(MOVIE_POSITION);
                movieAdapter.remove(movieAdapter.getItem(p));
                movieAdapter.insert(m, p);
                break;
            }
            default:
                    break;
        }
    }
}
