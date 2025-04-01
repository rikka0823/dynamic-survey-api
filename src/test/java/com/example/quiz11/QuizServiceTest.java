package com.example.quiz11;

import com.example.quiz11.constants.ResMessage;
import com.example.quiz11.service.ifs.QuizService;
import com.example.quiz11.vo.BasicRes;
import com.example.quiz11.vo.CreateUpdateReq;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * 此 Annotation 會在跑測試方法之前，先執行整個專案(從 main 方法進入執行)，並建立所有該託管的物件<br>
 * 測試方法中若有使用到 @Autowired，就要加上此 Annotation<br>
 * @SpringBootTest 只能用於 test 類別
 */
@SpringBootTest
public class QuizServiceTest {

    @Autowired
    private QuizService quizService;

    @Test
    public void createCheckQuizIdTest() {
        // check if quiz = 0
        CreateUpdateReq req = new CreateUpdateReq(1, "test_quiz", "test_quiz",
                LocalDate.now(), LocalDate.now(), false, new ArrayList<>());
        BasicRes res = quizService.create(req);
        Assert.isTrue(res.getMessage().equalsIgnoreCase(ResMessage.QUIZ_ID_ERROR.getMessage()),
                "check quiz_id fail!!");
    }


    @Test
    public void createCheckDateTest() {
        // check start_date 不能比 end_date 晚
        CreateUpdateReq req = new CreateUpdateReq("test_quiz", "test_quiz",
                LocalDate.now().plusDays(1), LocalDate.now(), false, new ArrayList<>());
        BasicRes res = quizService.create(req);
        Assert.isTrue(res.getMessage().equalsIgnoreCase(ResMessage.DATE_ERROR.getMessage()),
                "check date fail!!");
        System.out.println(res.getMessage());
    }
}
