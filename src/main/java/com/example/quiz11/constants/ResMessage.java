package com.example.quiz11.constants;

public enum ResMessage {
	
	SUCCESS(200, "Success!!"),
	QUES_PARAM_ERROR(400, "Ques type error!!"),
	DATE_ERROR(400, "Date error!!"),
	QUIZID_MISMATCH(400,"QuizID mismatch!!"),
	QUIZ_NOT_FOUND(404, "Quiz not found"),
	QUIZ_UPDATE_FAILED(400, "Quiz update failed"),
	QUIZ_ID_ERROR(400, "Quiz id error!"),
	USERNAME_AND_EMAIL_REQUIRED(400, "Username and email required!"),
	ANSWER_REQUIRED(400, "Answer required!!"),
	DATE_RANGE_ERROR(400, "Date range error!!"),
	QUESTION_NOT_FOUND(404, "Question not found"),
	ONE_OPTION_IS_ALLOWED(400, "One option is allowed"),
	OPTIONS_TRANSFER_ERROR(400, "Options transfer error!!"),
	OPTION_ANSWER_MISMATCH(400, "Option answer mismatch"),
	EMAIL_DUPLICATED(400, "Email duplicated!!"),
	LOGIN_ERROR(400, "Login error!");
	
	private int code;
	
	private String message;

	private ResMessage(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}
