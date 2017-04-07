package com.example;

import java.util.ArrayList;
import java.util.Random;

/**
 * A class to hold the jokes in an {@link ArrayList<JokeHolder>} list.
 */
public class Joke {

    private ArrayList<JokeHolder> jokes = new ArrayList<>();

    public Joke() {
        init();
    }

    /**
     * Initialize jokes
     */
    private void init() {
        JokeHolder holder;

        holder = new JokeHolder();
        holder.setQuestion("Anton, do you think I am a bad mother?");
        holder.setAnswer("My name is Paul.");
        jokes.add(holder);

        holder = new JokeHolder();
        holder.setQuestion("What is the difference between a snowman and a snowwoman?");
        holder.setAnswer("Snowballs");
        jokes.add(holder);

        holder = new JokeHolder();
        holder.setQuestion("Can a kangaroo jump higher than a house?");
        holder.setAnswer("Of course, a house does not jump at all.");
        jokes.add(holder);

        holder = new JokeHolder();
        holder.setQuestion("Police Officer: \"Can you identify yourself sir?\"");
        holder.setAnswer("Driver pulls out his mirror and says: \"Yes, it is me\"");
        jokes.add(holder);

        holder = new JokeHolder();
        holder.setQuestion("What do you get when you cross-breed a cow and a shark?");
        holder.setAnswer("I don't know, but I wouldn't enjoy milking it.");
        jokes.add(holder);

        holder = new JokeHolder();
        holder.setQuestion("Dentist: \"You need a crown\"");
        holder.setAnswer("Patient: \"Finally someone who understands me\"");
        jokes.add(holder);
    }

    /**
     * Randoms an integer representing location of joke in the list
     *
     * @return <code>Integer</code>: joke index in list
     */
    private int randomJoke() {
        Random rand = new Random();
        return rand.nextInt(100) % jokes.size();
    }

    /**
     * A method that gets a random joke
     *
     * @return {@link JokeHolder} object
     */
    public JokeHolder getJoke() {
        return jokes.get(randomJoke());
    }

}

