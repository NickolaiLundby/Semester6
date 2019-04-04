package com.runekeena.au297052movies.database;

import android.app.Application;
import android.arch.persistence.room.Room;

public class DatabaseApp extends Application {
    public MovieDatabase database;

    public MovieDatabase getDatabase(){
        if (database == null){
            database = Room.databaseBuilder(getApplicationContext(), MovieDatabase.class, "moviedatabase").build();
        }
        return database;
    }
}
