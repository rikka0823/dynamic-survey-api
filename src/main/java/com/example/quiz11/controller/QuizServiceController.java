package com.example.quiz11.controller;

import com.example.quiz11.service.ifs.AccountService;
import com.example.quiz11.service.ifs.QuizService;
import com.example.quiz11.vo.*;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

@CrossOrigin(origins = {"http://localhost:4200", "http://localhost"})
@RestController
public class QuizServiceController {

    @Autowired
    private QuizService quizService;

    @Autowired
    private AccountService accountService;

    @PostMapping(value = "quiz/create")
    public BasicRes create(@Valid @RequestBody CreateUpdateReq req) {
        return quizService.create(req);
    }
    
    @PostMapping(value = "quiz/update")
	public BasicRes update(@Valid @RequestBody CreateUpdateReq req) {
        return quizService.update(req);
	}

    @Hidden
    @PostMapping(value = "quiz/delete")
	public BasicRes delete(@RequestBody DeleteReq req) {
        return quizService.delete(req);
	}
    
    @PostMapping(value = "quiz/search")
	public SearchRes search(@RequestBody SearchReq req) {
        // 因為 service 中有使用 cache，所以必須要確認 req 中的參數的值不是 null
        // 檢視條件
        String name = req.getName();
        // 若 name = null 或空字串或全空白字串，一律都轉換成空字串
        if (!StringUtils.hasText(name)) {
            name = "";
            // 把新的值 set 回 req
            req.setName(name);
        }

        LocalDate startDate = req.getStartDate();
        // 若沒有日期條件，將日期轉換成很早的時間
        if (startDate == null) {
            startDate = LocalDate.of(1970, 1, 1);
            req.setStartDate(startDate);
        }

        LocalDate endDate = req.getEndDate();
        // 若沒有結束日期條件，將日期轉換成久遠的未來時間
        if (endDate == null) {
            endDate = LocalDate.of(9999, 12, 31);
            req.setEndDate(endDate);
        }

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

    @PostMapping("account/register")
    public BasicRes createAccount(@RequestBody @Valid AccountReq req) {
        return accountService.createAccount(req);
    }

    @PostMapping("account/login")
    public BasicRes login(@RequestBody @Valid AccountReq req) {
        return accountService.login(req);
    }
}
