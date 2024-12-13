package com.example.quiz11.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@IdClass(value = FeedBackId.class)
@Table(name = "feedback")
public class Feedback {
	
	@Id
	@Column(name = "quiz_id")
	private int quizId;
	
	@Id
	@Column(name = "ques_id")
	private int quesId;
	
	@Column(name = "answer")
	private String answer;
	
	@Column(name = "user_name")
	private String userName;
	
	@Column(name = "phone")
	private String phone;
	
	@Id
	@Column(name = "email")
	private String email;
	
	@Column(name = "age")
	private int age;
	
	@Column(name = "fillin_date")
	private LocalDate fillinDate;

	public Feedback() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Feedback(int quizId, int quesId, String answer, String userName, String phone, String email, int age,
			LocalDate fillinDate) {
		super();
		this.quizId = quizId;
		this.quesId = quesId;
		this.answer = answer;
		this.userName = userName;
		this.phone = phone;
		this.email = email;
		this.age = age;
		this.fillinDate = fillinDate;
	}

	public int getQuizId() {
		return quizId;
	}

	public int getQuesId() {
		return quesId;
	}

	public String getAnswer() {
		return answer;
	}

	public String getUserName() {
		return userName;
	}

	public String getPhone() {
		return phone;
	}

	public String getEmail() {
		return email;
	}

	public int getAge() {
		return age;
	}

	public LocalDate getFillinDate() {
		return fillinDate;
	}
}
