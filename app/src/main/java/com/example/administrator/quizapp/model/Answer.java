package com.example.administrator.quizapp.model;

public class Answer {

    String content;
    boolean correct;

    public Answer(String content, boolean correct) {
        this.content = content;
        this.correct = correct;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }
}
