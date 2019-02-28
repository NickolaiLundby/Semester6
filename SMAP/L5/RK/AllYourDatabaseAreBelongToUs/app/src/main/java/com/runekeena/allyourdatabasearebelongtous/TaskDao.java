package com.runekeena.allyourdatabasearebelongtous;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface TaskDao {
    @Query("SELECT * FROM task")
    List<Task> getAll();

    @Query("SELECT * FROM task WHERE uid LIKE :uid")
    Task findById(int uid);

    /*
    @Query("SELECT * FROM task WHERE uid IN (:taskIds)")
    List<Task> loadAllbyIds(int[] taskIds);
    */

    @Query("SELECT * FROM task WHERE place LIKE :place")
    List<Task> findByPlace(String place);

    @Insert
    void insertAll(Task... tasks);

    @Delete
    void delete(Task task);

}
