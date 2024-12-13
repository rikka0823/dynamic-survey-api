package com.example.quiz11.entity;

import java.io.Serializable;

public class QuesId implements Serializable {

	private static final long serialVersionUID = 1L;

	private int quizId;
	
	private int quesId;

	public int getQuizId() {
		return quizId;
	}

	public int getQuesId() {
		return quesId;
	}
}
