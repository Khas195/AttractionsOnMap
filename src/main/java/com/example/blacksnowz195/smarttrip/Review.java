package com.example.blacksnowz195.smarttrip;

import java.util.Random;

/**
 * Created by BlackSnowZ195 on 05/08/2015.
 */
public class Review {
    private String text;
    private String author;
    private String rating;

    public void SetText(String text) {
        this.text = text;
    }

    public void SetAuthor(String author_name) {
        this.author = author_name;
    }

    public void SetRating(String rating) {
        this.rating = rating;
    }

    public String GetText() {
        return text;
    }

    public String GetAuthor() {
        return author;
    }

    public String GetRating() {
        return rating;
    }
}
