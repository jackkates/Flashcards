package com.jackkates.flashcards.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by jackkates on 1/17/15.
 */
public class Set {

    private int id;

    private String title;

    @SerializedName("created_by")
    private String username;

    @SerializedName("terms")
    private List<Card> cards;

    public int getID() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getUsername() {
        return username;
    }

    public List<Card> getCards() {
        return cards;
    }
}
