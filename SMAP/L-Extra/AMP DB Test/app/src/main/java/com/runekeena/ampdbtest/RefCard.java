package com.runekeena.ampdbtest;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "ref_card_table", foreignKeys = {
        @ForeignKey(entity=Card.class,parentColumns = "caId",childColumns = "cardId", onDelete = CASCADE),
        @ForeignKey(entity=Collection.class,parentColumns = "coId",childColumns = "collectionId", onDelete = CASCADE)
        }, indices = {@Index(value = {"rcId"}, unique = true)})
public class RefCard {
    @PrimaryKey(autoGenerate = true)
    private int rcId;
    private int quantity;
    private int cardId;
    private int collectionId;

    @Ignore
    public RefCard(){};

    public RefCard(int quantity, int cardId, int collectionId) {
        this.quantity = quantity;
        this.cardId = cardId;
        this.collectionId = collectionId;
    }

    public int getRcId() {
        return rcId;
    }

    public void setRcId(int rcId) {
        this.rcId = rcId;
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
