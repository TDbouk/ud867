package com.udacity.gradle.builditbigger;

import com.example.toufik.myapplication.joketellerbackend.myApi.model.JokeHolder;

public class JokeEvent {

    public JokeHolder jokeHolder = null;

    public JokeEvent(JokeHolder message) {
        this.jokeHolder = message;
    }
}