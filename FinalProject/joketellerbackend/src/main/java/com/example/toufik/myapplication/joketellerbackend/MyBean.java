package com.example.toufik.myapplication.joketellerbackend;

import com.example.JokeHolder;

/**
 * The object model for the data we are sending through endpoints
 */
public class MyBean {

    private JokeHolder jokeHolder;

    public JokeHolder getData() {
        return jokeHolder;
    }

    public void setData(JokeHolder data) {
        jokeHolder = data;
    }
}