package com.placement.codeedge.repository;

import com.placement.codeedge.model.Problem;
import com.placement.codeedge.model.enums.Difficulty;
import com.placement.codeedge.model.enums.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Long> {

    List<Problem> findByTopic(Topic topic);

    List<Problem> findByDifficulty(Difficulty difficulty);

    List<Problem> findByTopicAndDifficulty(Topic topic, Difficulty difficulty);

    List<Problem> findBySolved(boolean solved);

    List<Problem> findByTopicAndSolved(Topic topic, boolean solved);

    List<Problem> findByDifficultyAndSolved(Difficulty difficulty, boolean solved);

    List<Problem> findByTopicAndDifficultyAndSolved(Topic topic, Difficulty difficulty, boolean solved);

    long countBySolved(boolean solved);

    long countByDifficulty(Difficulty difficulty);

    long countByDifficultyAndSolved(Difficulty difficulty, boolean solved);

    long countByTopic(Topic topic);

    long countByTopicAndSolved(Topic topic, boolean solved);

    @Query("SELECT p FROM Problem p JOIN p.companies c WHERE LOWER(c) = LOWER(:company)")
    List<Problem> findByCompany(@Param("company") String company);

    @Query("SELECT p FROM Problem p WHERE LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Problem> searchByTitle(@Param("keyword") String keyword);
}
