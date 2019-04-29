package com.runekeena.ampdbtest;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "card_table", indices = @Index(value = "caId", unique = true))
public class Card {

    @PrimaryKey(autoGenerate = true)
    private int caId;
    private String title;
    private double price;

    public Card(String title, double price) {
        this.title = title;
        this.price = price;
    }

    public int getCaId() {
        return caId;
    }

    public void setCaId(int caId) {
        this.caId = caId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
