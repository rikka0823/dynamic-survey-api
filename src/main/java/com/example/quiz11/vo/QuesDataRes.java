package com.example.quiz11.vo;

import com.example.quiz11.entity.Ques;

import java.util.List;

public class QuesDataRes {

    private List<Ques> quesList;

    public QuesDataRes() {
    }

    public QuesDataRes(List<Ques> quesList) {
        this.quesList = quesList;
    }

    public List<Ques> getQuesList() {
        return quesList;
    }
}
