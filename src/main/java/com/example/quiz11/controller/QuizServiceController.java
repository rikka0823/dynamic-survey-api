package com.example.quiz11.controller;

import com.example.quiz11.service.ifs.QuizService;
import com.example.quiz11.vo.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "http://localhost:4200") // 允許來自 http://localhost:4200 的api請求
@RestController
public class QuizServiceController {

    @Autowired
    private QuizService quizService;

    @PostMapping(value = "quiz/create")
    public BasicRes create(@Valid @RequestBody CreateUpdateReq req) {
        return quizService.create(req);
    }
    
    @PostMapping(value = "quiz/update")
	public BasicRes update(@Valid @RequestBody CreateUpdateReq req) {
        return quizService.update(req);
	}

    @PostMapping(value = "quiz/delete")
	public BasicRes delete(@RequestBody DeleteReq req) {
        return quizService.delete(req);
	}
    
    @PostMapping(value = "quiz/search")
	public SearchRes search(@RequestBody SearchReq req) {
        return quizService.search(req);
	}

    @PostMapping(value = "quiz/fillin")
    public BasicRes fillin(@RequestBody FillinReq req) {
        return quizService.fillin(req);
    }


    @GetMapping(value = "quiz/getQuizData")
    public SearchRes getQuizData() {
        return quizService.getQuizData();
    }

    @GetMapping(value = "quiz/getQuizDataById")
    public SearchRes getQuizDataById(@RequestParam int quizId) {
        return quizService.getQuizDataById(quizId);
    }

    // 完整 URL 路徑: http:// localhost::8080/quiz/feedback?quizId=1
    @GetMapping(value = "quiz/feedback")
    public FeedbackRes feedback(@RequestParam int quizId) {
        return quizService.feedback(quizId);
    }

    @GetMapping(value = "quiz/statistics")
    public StatisticsRes statistics(@RequestParam int quizId) {
        return quizService.statistics(quizId);
    }

    @GetMapping(value = "quiz/getQuesData")
    public QuesDataRes getQuesData(@RequestParam int quizId) {
        return quizService.getQuesData(quizId);
    }
}
