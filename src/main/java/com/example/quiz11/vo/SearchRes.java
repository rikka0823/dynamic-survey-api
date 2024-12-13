package com.example.quiz11.vo;

import com.example.quiz11.entity.Quiz;

import java.util.List;

public class SearchRes extends  BasicRes {

    private Quiz quiz;

    private List<Quiz> quizList;

    public SearchRes() {
    }

    public SearchRes(int code, String message, Quiz quiz) {
        super(code, message);
        this.quiz = quiz;
    }

    public SearchRes(int code, String message, List<Quiz> quizList) {
        super(code, message);
        this.quizList = quizList;
    }

    public List<Quiz> getQuizList() {
        return quizList;
    }

    public Quiz getQuiz() {
        return quiz;
    }
}
