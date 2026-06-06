package com.placement.codeedge.repository;

import com.placement.codeedge.model.MockInterview;
import com.placement.codeedge.model.enums.InterviewStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MockInterviewRepository extends JpaRepository<MockInterview, Long> {

    List<MockInterview> findByStatus(InterviewStatus status);

    List<MockInterview> findByTargetCompany(String company);

    List<MockInterview> findAllByOrderByScheduledTimeDesc();

    long countByStatus(InterviewStatus status);
}
