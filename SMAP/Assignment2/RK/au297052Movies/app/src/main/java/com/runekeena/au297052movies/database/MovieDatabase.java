package com.runekeena.au297052movies.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.runekeena.au297052movies.model.Movie;
import com.runekeena.au297052movies.model.MovieDao;

@Database(entities = {Movie.class}, version = 1)
public abstract class MovieDatabase extends RoomDatabase {
    public abstract MovieDao movieDao();
}
