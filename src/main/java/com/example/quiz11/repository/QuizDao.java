package com.example.quiz11.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.quiz11.entity.Quiz;
import com.example.quiz11.vo.CreateUpdateReq;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface QuizDao extends JpaRepository<Quiz, Integer> {

    @Transactional
    @Modifying
    @Query(value = "delete from quiz where id in(?1)", nativeQuery = true)
    public void deleteByIdIn(List<Integer> idList);

    @Query(value = "select id, name, description, start_date, end_date, published" +
            " from quiz where name like %?1% and start_date >= ?2" +
            " and end_date <= ?3", nativeQuery = true)
    public List<Quiz> getByConditions(String name, LocalDate startDate, LocalDate endDate);

//    public Quiz findByIdAndPublishedTrue(int quizId); // JPA 語法，效果同 getByIdAndPublishedTrue

    // 語法中的 published is true 也可以寫成  published = true;
    // null 也適用: e.g. name is null(name = null)
    @Query(value = "select id, name, description, start_date, end_date, published" +
            " from quiz where id = ?1 and published is true", nativeQuery = true)
    public Quiz getByIdAndPublishedTrue(int quizId);

    @Query(value = "select id, name, description, start_date, end_date, published" +
            " from quiz where id = ?1 and published is true" +
            " and start_date <= ?2 and end_date >= ?2", nativeQuery = true)
    public Quiz getByIdAndPublishedTrueBetween(int quizId, LocalDate fillinDate);


}
