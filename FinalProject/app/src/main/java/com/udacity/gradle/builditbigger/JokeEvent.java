package com.udacity.gradle.builditbigger;

import com.example.toufik.myapplication.joketellerbackend.myApi.model.JokeHolder;

/**
 * A class that represents an event holding a JokeHolder object.
 * The event is passed via EventBus library.
 */
public class JokeEvent {

    public JokeHolder jokeHolder = null;

    public JokeEvent(JokeHolder message) {
        this.jokeHolder = message;
    }
}