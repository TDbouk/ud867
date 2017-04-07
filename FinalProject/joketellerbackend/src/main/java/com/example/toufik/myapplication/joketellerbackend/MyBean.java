package com.example.toufik.myapplication.joketellerbackend;

import com.example.JokeHolder;

/**
 * The object model for the data we are sending through endpoints.
 * It holds a {@link JokeHolder} object containing the joke.
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