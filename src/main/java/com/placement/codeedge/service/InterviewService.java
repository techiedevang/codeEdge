package com.placement.codeedge.service;

import com.placement.codeedge.model.MockInterview;
import com.placement.codeedge.model.enums.InterviewStatus;
import com.placement.codeedge.model.enums.Topic;
import com.placement.codeedge.repository.MockInterviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InterviewService {

    private final MockInterviewRepository interviewRepository;

    public List<MockInterview> getAll() {
        return interviewRepository.findAllByOrderByScheduledTimeDesc();
    }

    public Optional<MockInterview> getById(Long id) {
        return interviewRepository.findById(id);
    }

    @Transactional
    public MockInterview schedule(String title, String targetCompany,
                                  LocalDateTime scheduledTime, Integer durationMinutes,
                                  String[] topicNames) {
        List<Topic> topics = (topicNames != null)
                ? Arrays.stream(topicNames)
                    .map(Topic::valueOf)
                    .collect(Collectors.toList())
                : List.of();

        MockInterview interview = MockInterview.builder()
                .title(title)
                .targetCompany(targetCompany)
                .scheduledTime(scheduledTime)
                .durationMinutes(durationMinutes != null ? durationMinutes : 60)
                .focusTopics(topics)
                .status(InterviewStatus.SCHEDULED)
                .build();

        return interviewRepository.save(interview);
    }

    @Transactional
    public MockInterview complete(Long id, Integer score, Integer attempted,
                                  Integer solved, String feedback) {
        MockInterview interview = interviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Interview not found: " + id));
        interview.setStatus(InterviewStatus.COMPLETED);
        interview.setScore(score);
        interview.setQuestionsAttempted(attempted);
        interview.setQuestionsSolved(solved);
        interview.setFeedback(feedback);
        return interviewRepository.save(interview);
    }

    @Transactional
    public MockInterview cancel(Long id) {
        MockInterview interview = interviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Interview not found: " + id));
        interview.setStatus(InterviewStatus.CANCELLED);
        return interviewRepository.save(interview);
    }

    public long countByStatus(InterviewStatus status) {
        return interviewRepository.countByStatus(status);
    }

    public double averageScore() {
        return interviewRepository.findByStatus(InterviewStatus.COMPLETED)
                .stream()
                .filter(i -> i.getScore() != null)
                .mapToInt(MockInterview::getScore)
                .average()
                .orElse(0.0);
    }
}
