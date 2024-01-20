package com.umairjr.questionservice.dao;

import com.umairjr.questionservice.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionDao extends JpaRepository<Question, Integer> {

    List<Question> findByCategory(String category);

    @Query(value = "SELECT TOP (:numQs) id FROM question_table q WHERE q.category = :category ORDER BY NEWID()", nativeQuery = true)
    List<Integer> findRandomByCategory(String category,int numQs);
}
