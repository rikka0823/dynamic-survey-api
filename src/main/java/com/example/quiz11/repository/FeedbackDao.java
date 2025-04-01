package com.example.quiz11.repository;

import com.example.quiz11.entity.FeedBackId;
import com.example.quiz11.entity.Feedback;
import com.example.quiz11.vo.FeedbackDto;
import com.example.quiz11.vo.StatisticsDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackDao extends JpaRepository<Feedback, FeedBackId> {

    public boolean existsByQuizIdAndEmail(int quizId, String email);

    // 取得同一張問卷所有人的填答
    @Query(value = "select new com.example.quiz11.vo.FeedbackDto(" +
            " qz.id, f.fillinDate, qz.name, qz.description, f.userName," +
            " f.phone, f.email, f.age, qu.quesId, qu.quesName, f.answer)" +
            " from Quiz qz" +
            " join Ques qu on qz.id = qu.quizId" +
            " join Feedback f on qu.quizId = f.quizId and qu.quesId = f.quesId" +
            " where qz.id = ?1", nativeQuery = false)
    public List<FeedbackDto> getFeedbackByQuizId(int quizId);

    @Query(value = "select new com.example.quiz11.vo.StatisticsDto(" +
            " qz.name, qu.quesId, qu.quesName, qu.type, qu.options, f.answer)" +
            " from Quiz qz join Ques qu on qz.id = qu.quizId" +
            " join Feedback f on qu.quizId = f.quizId and qu.quesId = f.quesId" +
            " where qz.id = ?1", nativeQuery = false)
    public List<StatisticsDto> getStatisticsByQuizId(int quizId);
}
