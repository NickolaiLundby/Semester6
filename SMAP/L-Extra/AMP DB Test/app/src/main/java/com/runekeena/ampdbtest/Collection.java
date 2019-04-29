package com.runekeena.ampdbtest;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "collection_table",  indices = @Index(value = "coId", unique = true))
public class Collection {
    @PrimaryKey(autoGenerate = true)
    private int coId;
    private String title;
    private String description;

    public Collection(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public int getCoId() {
        return coId;
    }

    public void setCoId(int coId) {
        this.coId = coId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}