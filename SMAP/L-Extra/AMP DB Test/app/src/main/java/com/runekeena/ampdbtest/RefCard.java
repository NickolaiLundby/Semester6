package com.runekeena.ampdbtest;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "ref_card_table", foreignKeys = {
        @ForeignKey(entity=Card.class,parentColumns = "id",childColumns = "cardId", onDelete = CASCADE),
        @ForeignKey(entity=Collection.class,parentColumns = "id",childColumns = "collectionId", onDelete = CASCADE)
        }, indices = {@Index(value = {"id"}, unique = true)})
public class RefCard {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int quantity;
    private int cardId;
    private int collectionId;

    public RefCard(int quantity, int cardId) {
        this.quantity = quantity;
        this.cardId = cardId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public int getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(int collectionId) {
        this.collectionId = collectionId;
    }
}
