package com.runekeena.allyourdatabasearebelongtous;

import android.app.Application;
import android.arch.persistence.room.Room;

public class TaskApp extends Application {
    public TaskDatabase taskDatabase;

    public TaskDatabase getTaskDatabase(){
        if(taskDatabase == null){
            taskDatabase= Room.databaseBuilder(getApplicationContext(), TaskDatabase.class, "task-database").allowMainThreadQueries().build();
            //return taskDatabase;
        }

        return taskDatabase;
    }
}
