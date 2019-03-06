package com.runekeena.allyourdatabasearebelongtous;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Random;

@Entity
public class Task {

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "place")
    private String place;

    public Task(int uid, String description, String place) {
        this.uid = uid;
        this.description = description;
        this.place = place;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) { this.place = place;}
}
