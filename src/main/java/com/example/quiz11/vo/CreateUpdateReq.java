package com.example.quiz11.vo;

import java.time.LocalDate;
import java.util.List;

import com.example.quiz11.constants.MsgConstants;
import com.example.quiz11.entity.Ques;
import com.example.quiz11.entity.Quiz;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

public class CreateUpdateReq extends Quiz {
	// 多題
	@Valid
	@NotEmpty(message = MsgConstants.QUES_IS_EMPTY)
	private List<Ques> quesList;
	
	public CreateUpdateReq() {
		super();
	}

	public CreateUpdateReq(String name, String description, LocalDate startDate,
			LocalDate endDate, boolean published, List<Ques> quesList) {
		super(name, description, startDate, endDate, published);
		this.quesList = quesList;
	}
	
	public CreateUpdateReq(int id, String name, String description, LocalDate startDate,
			LocalDate endDate, boolean published, List<Ques> quesList) {
		super(id, name, description, startDate, endDate, published);
		this.quesList = quesList;
	}

	public List<Ques> getQuesList() {
		return quesList;
	}
}
