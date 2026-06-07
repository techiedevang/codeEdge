package com.placement.codeedge.repository;

import com.placement.codeedge.model.MockInterview;
import com.placement.codeedge.model.enums.InterviewStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MockInterviewRepository extends MongoRepository<MockInterview, String> {

    List<MockInterview> findByStatus(InterviewStatus status);

    List<MockInterview> findByTargetCompany(String company);

    List<MockInterview> findAllByOrderByScheduledTimeDesc();

    long countByStatus(InterviewStatus status);
}
