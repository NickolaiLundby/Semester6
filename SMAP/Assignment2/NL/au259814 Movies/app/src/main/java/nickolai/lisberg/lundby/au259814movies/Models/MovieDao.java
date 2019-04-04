package nickolai.lisberg.lundby.au259814movies.Models;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import nickolai.lisberg.lundby.au259814movies.Models.Movie;

@Dao
public interface MovieDao {
    @Query("SELECT * FROM movie")
    List<Movie> getAll();

    @Query("SELECT * FROM movie WHERE title LIKE :title")
    Movie findByTitle(String title);

    @Insert
    void insertMovie(Movie movie);

    @Delete
    void delete(Movie movie);

    @Update
    void update(Movie movie);
}
