package com.runekeena.au297052movies.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.runekeena.au297052movies.database.DatabaseApp;
import com.runekeena.au297052movies.database.MovieDatabase;

public class MovieService extends Service {

    MovieDatabase movieDatabase;

    public MovieService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        DatabaseApp dba = (DatabaseApp) getApplicationContext();
        movieDatabase = dba.getDatabase();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void getMovieByTitle(String title){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = null;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String r = response;
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
    }
}
