package com.example.quiz11.service.impl;

import java.time.LocalDate;
import java.util.*;

import com.example.quiz11.entity.Feedback;
import com.example.quiz11.repository.FeedbackDao;
import com.example.quiz11.vo.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.example.quiz11.constants.QuesType;
import com.example.quiz11.constants.ResMessage;
import com.example.quiz11.entity.Ques;
import com.example.quiz11.entity.Quiz;
import com.example.quiz11.repository.QuesDao;
import com.example.quiz11.repository.QuizDao;
import com.example.quiz11.service.ifs.QuizService;

import javax.transaction.Transactional;

@Service
public class QuizServiceImpl implements QuizService {

    @Autowired
    private QuizDao quizDao;

    @Autowired
    private QuesDao quesDao;

    @Autowired
    private FeedbackDao feedbackDao;

    private ObjectMapper mapper = new ObjectMapper();

    private BasicRes checkParams(CreateUpdateReq req) {
        // 以下程式碼已透過 @Valid 在 Quiz 中檢查
//        if (!StringUtils.hasText(req.getName()) || !StringUtils.hasText(req.getDescription())) {
//            return new BasicRes(ResMessage.QUIZ_PARAM_ERROR.getCode(), ResMessage.QUIZ_PARAM_ERROR.getMessage());
//        }

        // 檢查開始時間不得比結束時間晚
        if (req.getStartDate() == null || req.getEndDate() == null
                || req.getStartDate().isAfter(req.getEndDate())) {
            return new BasicRes(ResMessage.DATE_ERROR.getCode(), ResMessage.DATE_ERROR.getMessage());
        }

        // 檢查開始時間不能比今天早(問卷的開始時間最晚只能是今天)
//        if (req.getStartDate().isBefore(LocalDate.now())) {
//            return new BasicRes(ResMessage.DATE_ERROR.getCode(), ResMessage.DATE_ERROR.getMessage());
//        }

        // 檢查 ques
        for (Ques item : req.getQuesList()) {
            if (item.getQuesId() <= 0
                    || !StringUtils.hasText(item.getType())
                    || !StringUtils.hasText(item.getQuesName())

            ) {
                return new BasicRes(ResMessage.QUES_PARAM_ERROR.getCode(), ResMessage.QUES_PARAM_ERROR.getMessage());
            }

            // 檢查題目類型:單選(single)、多選(multi)、文字(text)
            if (!QuesType.checkType(item.getType())) {
                return new BasicRes(ResMessage.QUES_PARAM_ERROR.getCode(), ResMessage.QUES_PARAM_ERROR.getMessage());
            }

            // 檢查非文字類型時選項沒有值
            if (!item.getType().equalsIgnoreCase(QuesType.TEXT.toString()) &&
                    !StringUtils.hasText(item.getOptions())) {
                return new BasicRes(ResMessage.QUES_PARAM_ERROR.getCode(), ResMessage.QUES_PARAM_ERROR.getMessage());
            }
        }

        // 成功
        return null;
    }

    @Transactional
    @Override
    public BasicRes create(CreateUpdateReq req) {
        // 檢查新增問卷時，id 要為 0
        if (req.getId() != 0) {
            return new BasicRes(ResMessage.QUES_PARAM_ERROR.getCode(), ResMessage.QUES_PARAM_ERROR.getMessage());
        }

        // 參數檢查
        BasicRes checkResult = checkParams(req);
        if (checkParams(req) != null) {
            return checkResult;
        }

        // 新增問卷:
        // 因為 Quiz 中的 id 是 AI 自動生成的流水號，不會重複寫入，所以不用檢查資料是否已存在相同的 pk
        // quizDao 要執行 save 後才可以把該 id 的值回傳，
        Quiz quizRes = quizDao.save(new Quiz(req.getName(), req.getDescription(), req.getStartDate(),
                req.getEndDate(), req.isPublished()));

        // 將 quiz 中的 id 加入到 ques
        int quizId = quizRes.getId();
        for (Ques item : req.getQuesList()) {
            item.setQuizId(quizId);
        }

        // 新增問題
        quesDao.saveAll(req.getQuesList());

        // 成功
        return new BasicRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage());
    }

    @Transactional
    @Override
    public BasicRes update(CreateUpdateReq req) {
        // 參數檢查
        // 檢查更新問卷時，因為問卷已存在資料庫中，所以 id 不能為 0
        if (req.getId() == 0) {
            return new BasicRes(ResMessage.QUES_PARAM_ERROR.getCode(), ResMessage.QUES_PARAM_ERROR.getMessage());
        }

        // 參數檢查
        BasicRes checkResult = checkParams(req);
        if (checkParams(req) != null) {
            return checkResult;
        }

        // 需要檢查修改的日期是否是可以填寫的時間範圍內
        if (
            req.getStartDate() == null ||
            req.getEndDate() == null ||
            req.getStartDate().isAfter(req.getEndDate()) ||
            req.getStartDate().isBefore(LocalDate.now()) ||
            req.getEndDate().isBefore(req.getStartDate())) {
            return new BasicRes(ResMessage.DATE_RANGE_ERROR.getCode(), ResMessage.DATE_RANGE_ERROR.getMessage());
        }

        // 檢查 ques 的 quiz_id 是否與 quiz 的 id 相符
        int quizId = req.getId();
        for (Ques item : req.getQuesList()) {
            if (item.getQuizId() != quizId) {
                return new BasicRes(ResMessage.QUIZID_MISMATCH.getCode(), ResMessage.QUIZID_MISMATCH.getMessage());
            }
        }

        // 問卷可以更新的狀態: 1.未發布 ; 2.已發布但尚未開始
        Optional<Quiz> op = quizDao.findById(quizId);

        // 確認問卷是否存在
        if (op.isEmpty()) {
            return new BasicRes(ResMessage.QUIZ_NOT_FOUND.getCode(), ResMessage.QUIZ_NOT_FOUND.getMessage());
        }

        // 取得問卷(資料庫中的資料)
        Quiz quiz = op.get();

        // 確認問卷是否可以進行更新
        // 尚未發布: !quiz.isPublish()
        // 已發布但尚未開始: quiz.isPublish() && req.getStartDate().isAfter(LocalDate.now())
        if (!(!quiz.isPublished() ||
                (quiz.isPublished() && req.getStartDate().isAfter(LocalDate.now())))) {
            return new BasicRes(ResMessage.QUIZ_UPDATE_FAILED.getCode(), ResMessage.QUIZ_UPDATE_FAILED.getMessage());
        }

        // 更新問卷
        // 將 req 中的值 set 回從資料庫取出的 quiz 中，id 不需要
        quiz.setName(req.getName());
        quiz.setDescription(req.getDescription());
        quiz.setStartDate(req.getStartDate());
        quiz.setEndDate(req.getEndDate());
        quiz.setPublished(req.isPublished());

        // 更新問卷
        quizDao.save(quiz);

        // 先刪除相同 quiz_id 的問卷所有問題，再新增
        quesDao.deleteByQuizId(quizId);
        quesDao.saveAll(req.getQuesList());

        // 成功
        return new BasicRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage());
    }

    @Transactional
    @Override
    public BasicRes delete(DeleteReq req) {
        // 刪問卷
        quizDao.deleteByIdIn(req.getQuizIdList());

        // 刪相同 quiz_id 問卷的所有問題
        quesDao.deleteByQuizIdIn(req.getQuizIdList());

        return new BasicRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage());
    }

    @Override
    public SearchRes search(SearchReq req) {
        // 檢視條件
        String name = req.getName();
        // 若 name = null 或空字串或全空白字串，一律都轉換成空字串
        if (!StringUtils.hasText(name)) {
            name = "";
        }

        LocalDate startDate = req.getStartDate();
        // 若沒有日期條件，將日期轉換成很早的時間
        if (startDate == null) {
            startDate = LocalDate.of(1970, 1, 1);
        }

        LocalDate endDate = req.getEndDate();
        // 若沒有結束日期條件，將日期轉換成久遠的未來時間
        if (endDate == null) {
            endDate = LocalDate.of(9999, 12, 31);
        }

        return new SearchRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage(),
                quizDao.getByConditions(name, startDate, endDate));
    }

    @Transactional
    @Override
    public BasicRes fillin(FillinReq req) {
        // 參數檢查
        if (req.getQuizId() <= 0) {
            return new BasicRes(ResMessage.QUIZ_ID_ERROR.getCode(), ResMessage.QUIZ_ID_ERROR.getMessage());
        }
        if (!StringUtils.hasText(req.getUserName()) || !StringUtils.hasText(req.getEmail())) {
            return new BasicRes(ResMessage.USERNAME_AND_EMAIL_REQUIRED.getCode(), ResMessage.USERNAME_AND_EMAIL_REQUIRED.getMessage());
        }
        if (CollectionUtils.isEmpty(req.getAnswer())) {
            return new BasicRes(ResMessage.ANSWER_REQUIRED.getCode(), ResMessage.ANSWER_REQUIRED.getMessage());
        }

        // 檢查同一張問卷是否已有相同的 email 填寫過
        if (feedbackDao.existsByQuizIdAndEmail(req.getQuizId(), req.getEmail())) {
            return new BasicRes(ResMessage.EMAIL_DUPLICATED.getCode(), ResMessage.EMAIL_DUPLICATED.getMessage());
        }

        // 比對資料庫問卷的問卷和問題
        // 可以填寫的問卷必須是已發布的
        Quiz quiz = quizDao.getByIdAndPublishedTrue(req.getQuizId());
        if (quiz == null) {
            return new BasicRes(ResMessage.QUIZ_NOT_FOUND.getCode(), ResMessage.QUIZ_NOT_FOUND.getMessage());
        }

        // 需要檢查填寫的日期是否是可以填寫的時間範圍內
        if (
                req.getFillinDate() == null ||
                        req.getFillinDate().isBefore(quiz.getStartDate()) ||
                        req.getFillinDate().isAfter(quiz.getEndDate())) {
            return new BasicRes(ResMessage.DATE_RANGE_ERROR.getCode(), ResMessage.DATE_RANGE_ERROR.getMessage());
        }

        // 比對問題
        List<Ques> quesList = quesDao.getByQuizId(req.getQuizId());
        if (CollectionUtils.isEmpty(quesList)) {
            return new BasicRes(ResMessage.QUESTION_NOT_FOUND.getCode(), ResMessage.QUESTION_NOT_FOUND.getMessage());
        }

        // 題號, 選項(1~多個)
        Map<Integer, List<String>> answerMap = req.getAnswer();
        for (Ques item : quesList) {
            // req 中的選項(答案)
            List<String> ansList = answerMap.get((item.getQuesId()));

            // 必填但沒有答案
            if (item.isRequired() && CollectionUtils.isEmpty(ansList)) {
                return new BasicRes(ResMessage.ANSWER_REQUIRED.getCode(), ResMessage.ANSWER_REQUIRED.getMessage());
            }

            // 單選和文字時不能有多個答案
            if (
                    (item.getType().equals(QuesType.SINGLE.getType()) ||
                            item.getType().equals(QuesType.TEXT.getType())) &&
                            ansList.size() > 1) {
                return new BasicRes(ResMessage.ONE_OPTION_IS_ALLOWED.getCode(), ResMessage.ONE_OPTION_IS_ALLOWED.getMessage());
            }

            // 當問題 type 不是描述類型時，需要將資料庫中的選項字串轉換成選項類別
            if (!item.getType().equalsIgnoreCase(QuesType.TEXT.getType())) {
                // 把 Ques 字串 轉成 Options 類別
                List<Options> optionsList = new ArrayList<>();
                try {
                    optionsList = mapper.readValue(item.getOptions(), new TypeReference<>() {
                    });
                } catch (Exception e) {
                    return new BasicRes(ResMessage.OPTIONS_TRANSFER_ERROR.getCode(), ResMessage.OPTIONS_TRANSFER_ERROR.getMessage());
                }

                // 蒐集 List<Options> 中所有的 optionName
                List<String> optionsListInDB = new ArrayList<>();
                for (Options opt : optionsList) {
                    optionsListInDB.add(opt.getOptionName());
                }

                // 比對 req 中的答案與資料庫中的選項是否一致
                // 因為DB中的選項會比答案選項多，所以多的 List 去 contains 小的 List 中的每一項
                for (String ans : ansList) {
                    if (!optionsListInDB.contains(ans)) {
                        return new BasicRes(ResMessage.OPTION_ANSWER_MISMATCH.getCode(), ResMessage.OPTION_ANSWER_MISMATCH.getMessage());
                    }
                }
            }
        }

        // 存資料
        List<Feedback> feedbackList = new ArrayList<>();
        // 題號: Integer, 答案選項: List<String>
        for (Map.Entry<Integer, List<String>> map : answerMap.entrySet()) {
            try {
                // 將 Java 類別的 List<String> 的答案轉換成 JSON 格式字串
                String str = mapper.writeValueAsString(map.getValue());
                Feedback feedback = new Feedback(req.getQuizId(), map.getKey(), str, req.getUserName(),
                        req.getPhone(), req.getEmail(), req.getAge(), req.getFillinDate());
                feedbackList.add(feedback);
            } catch (JsonProcessingException e) {
                return new BasicRes(ResMessage.OPTIONS_TRANSFER_ERROR.getCode(), ResMessage.OPTIONS_TRANSFER_ERROR.getMessage());
            }
        }
        feedbackDao.saveAll(feedbackList);

        // 成功
        return new BasicRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage());
    }

    @Override
    public SearchRes getQuizData() {
        return new SearchRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage(), quizDao.findAll());
    }

    @Override
    public SearchRes getQuizDataById(int quizId) {
        return new SearchRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage(), quizDao.findById(quizId).orElse(null));
    }

    @Override
    public FeedbackRes feedback(int quizId) {
        // 參數檢查
        if (quizId <= 0) {
            return new FeedbackRes(ResMessage.QUIZ_ID_ERROR.getCode(), ResMessage.QUIZ_ID_ERROR.getMessage());
        }

        List<FeedbackDto> list = feedbackDao.getFeedbackByQuizId(quizId);
        return new FeedbackRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage(), list);
    }

    @Override
    public StatisticsRes statistics(int quizId) {
        // 參數檢查
        if (quizId <= 0) {
            return new StatisticsRes(ResMessage.QUIZ_ID_ERROR.getCode(), ResMessage.QUIZ_ID_ERROR.getMessage());
        }

        List<StatisticsDto> dtoList = feedbackDao.getStatisticsByQuizId(quizId);
        if (dtoList.isEmpty()) {
            return new StatisticsRes(ResMessage.QUIZ_ID_ERROR.getCode(), ResMessage.QUIZ_ID_ERROR.getMessage(),
                    new ArrayList<>());
        }

        // 用 Map 儲存每個問題的統計資料，避免重複計算
        Map<Integer, StatisticsVo> voMap = new HashMap<>();

        for (StatisticsDto dto : dtoList) {
            // 檢查並獲取對應的 StatisticsVo，若不存在則初始化
            StatisticsVo vo = voMap.computeIfAbsent(dto.getQuesId(),
                    k -> new StatisticsVo(dto.getQuizName(), dto.getQuesId(),
                            dto.getQuesName(), new HashMap<>()));

            // 如果是簡答題型，跳過該題
            if (dto.getType().equalsIgnoreCase(QuesType.TEXT.getType())) {
                continue;
            }

            // 解析選項和答案
            List<Options> optionsList = new ArrayList<>();
            List<String> answerList = new ArrayList<>();
            try {
                // 解析選項
                if (!dto.getType().equalsIgnoreCase(QuesType.TEXT.getType())) {
                    optionsList = mapper.readValue(dto.getOptionsStr(), new TypeReference<List<Options>>() {
                    });
                }
                // 解析答案
                if (StringUtils.hasText(dto.getAnswerStr()) && !dto.getType().equalsIgnoreCase(QuesType.TEXT.getType())) {
                    answerList = mapper.readValue(dto.getAnswerStr(), new TypeReference<List<String>>() {
                    });
                }
            } catch (Exception e) {
                return new StatisticsRes(ResMessage.OPTIONS_TRANSFER_ERROR.getCode(), ResMessage.OPTIONS_TRANSFER_ERROR.getMessage());
            }

            // 初始化選項計數 (只有第一次遇到這個問題才初始化)
            if (vo.getOptionCountMap().isEmpty()) {
                optionsList.forEach(option -> vo.getOptionCountMap().put(option.getOptionName(), 0));
            }

            // 更新選項計數
            answerList.forEach(answer -> vo.getOptionCountMap().merge(answer, 1, Integer::sum));
        }

        // 返回統計結果
        return new StatisticsRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage(), new ArrayList<>(voMap.values()));
    }

    public QuesDataRes getQuesData(int quizId) {
        return new QuesDataRes(quesDao.getByQuizId(quizId));
    }
}
