package com.runekeena.au297052movies.model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

// Reference - Database - L5: Persistence

@Dao
public interface MovieDao {
    @Query("SELECT * FROM movie")
    List<Movie> getAll();

    @Query("SELECT * FROM movie WHERE NOT Movie.Watched")
    List<Movie> getAllUnwatched();

    @Query("SELECT * FROM movie WHERE title LIKE :title")
    Movie findByTitle(String title);

    @Insert
    void insertMovie(Movie movie);

    @Delete
    void delete(Movie movie);

    @Update
    void update(Movie movie);
}
