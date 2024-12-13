package com.example.quiz11;

import com.example.quiz11.constants.ResMessage;
import com.example.quiz11.entity.Ques;
import com.example.quiz11.service.ifs.QuizService;
import com.example.quiz11.service.ifs.QuizService2;
import com.example.quiz11.vo.BasicRes;
import com.example.quiz11.vo.CreateUpdateReq;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 此 Annotation 會在跑測試方法之前，先執行整個專案(從 main 方法進入執行)，並建立所有該託管的物件<br>
 * 測試方法中若有使用到 @Autowired，就要加上此 Annotation<br>
 * @SpringBootTest 只能用於 test 類別
 */
@SpringBootTest
public class QuizServiceTest {

    @Autowired
    private QuizService quizService;

//    @Test
//    public void createCheckQuizIdTest() {
//        // check if quiz = 0
//        CreateUpdateReq req = new CreateUpdateReq(1, "test_quiz", "test_quiz",
//                LocalDate.now(), LocalDate.now(), false, new ArrayList<>());
//        BasicRes res = quizService.create(req);
//        Assert.isTrue(res.getMessage().equalsIgnoreCase(ResMessage.QUIZ_ID_ERROR.getMessage()),
//                "check quiz_id fail!!");
//    }


//    @Test
//    public void createCheckDateTest() {
//        // check start_date 不能比 end_date 晚
//        CreateUpdateReq req = new CreateUpdateReq("test_quiz", "test_quiz",
//                LocalDate.now().plusDays(1), LocalDate.now(), false, new ArrayList<>());
//        BasicRes res = quizService.create(req);
//        Assert.isTrue(res.getMessage().equalsIgnoreCase(ResMessage.DATE_ERROR.getMessage()),
//                "check date fail!!");
//        System.out.println(res.getMessage());
//    }

    @Test
    public void lambdaTest() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        System.out.println(list.get(0));

        Map<Integer, String> map = new HashMap<>();
        map.put(1, "A");
        map.put(2, "B");
        map.put(3, "C");

        for (Map.Entry<Integer, String> item : map.entrySet()) {
            System.out.println(item.getKey());
            System.out.println(item.getValue());
        }

        for (Integer item : map.keySet()) {
            System.out.println(item);
            System.out.println(map.get(item));
        }

        map.forEach((k, v) -> System.out.println(k + "=" + v));

    }

    @Test
    public void ifTest() {
        QuizService2 ifs = new QuizService2() {
            @Override
            public void test() {
                System.out.println("f");
            }
        };

        QuizService2 ifess = () -> System.out.println("f");
    }

    @Test
    public void filterTest() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i <= 20; i++) {
            list.add(i);
        }

        List<Integer> enenList = list.stream()
                .filter(item -> {
                    return item % 2 == 0;
                })
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
