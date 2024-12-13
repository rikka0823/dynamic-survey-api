package com.example.quiz11.entity;


import java.io.Serializable;

public class FeedBackId implements Serializable {

    private static final long serialVersionUID = 1L;

    private int quizId;

    private int quesId;

    private String email;

    public FeedBackId() {
    }

    public FeedBackId(int quizId, int quesId, String email) {
        this.quizId = quizId;
        this.quesId = quesId;
        this.email = email;
    }

    public int getQuizId() {
        return quizId;
    }

    public int getQuesId() {
        return quesId;
    }

    public String getEmail() {
        return email;
    }
}
