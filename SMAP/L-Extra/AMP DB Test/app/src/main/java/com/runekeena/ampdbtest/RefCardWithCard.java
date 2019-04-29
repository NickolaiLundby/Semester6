package com.runekeena.ampdbtest;

import android.arch.persistence.room.Embedded;

public class RefCardWithCard extends RefCard{

    @Embedded
    RefCard refCard;

    @Embedded
    Card card;
}
