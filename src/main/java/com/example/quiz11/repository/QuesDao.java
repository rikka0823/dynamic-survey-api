package com.example.quiz11.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.quiz11.entity.Ques;
import com.example.quiz11.entity.QuesId;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface QuesDao extends JpaRepository<Ques, QuesId> {

    @Transactional
    @Modifying
    @Query(value = "delete from ques where quiz_id = ?1", nativeQuery = true)
    public void deleteByQuizId(int quizId);

    @Transactional
    @Modifying
    @Query(value = "delete from ques where quiz_id in(?1)", nativeQuery = true)
    public void deleteByQuizIdIn(List<Integer> quizIdList);

    @Query(value = "select quiz_id, ques_id, ques_name, type, required, options" +
            " from ques where quiz_id = ?1", nativeQuery = true)
    public List<Ques> getByQuizId(int quizId);

}
