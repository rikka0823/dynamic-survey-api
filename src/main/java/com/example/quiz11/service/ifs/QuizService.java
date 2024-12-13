package com.example.quiz11.service.ifs;

import com.example.quiz11.vo.*;

import java.util.List;

public interface QuizService {

	public BasicRes create(CreateUpdateReq req);
	
	public BasicRes update(CreateUpdateReq req);

	public BasicRes delete(DeleteReq req);

	public SearchRes search(SearchReq req);

	public BasicRes fillin(FillinReq req);

	public SearchRes getQuizData();

	public SearchRes getQuizDataById(int quizId);

	public FeedbackRes feedback(int quizId);

	public StatisticsRes statistics(int quizId);

	public QuesDataRes getQuesData(int quizId);

}
