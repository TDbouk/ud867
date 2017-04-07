package com.example;

/**
 * A class that represents a jokes details: question and answer.
 */

public class JokeHolder {

    private String question;
    private String answer;

    public JokeHolder() {
        question = "";
        answer = "";
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "JokeHolder{" +
                "question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }
}
