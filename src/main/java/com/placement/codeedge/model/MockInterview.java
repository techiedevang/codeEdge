package com.placement.codeedge.model;

import com.placement.codeedge.model.enums.InterviewStatus;
import com.placement.codeedge.model.enums.Topic;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "mock_interviews")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MockInterview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String targetCompany;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "interview_topics", joinColumns = @JoinColumn(name = "interview_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "topic")
    @Builder.Default
    private List<Topic> focusTopics = new ArrayList<>();

    private LocalDateTime scheduledTime;

    @Builder.Default
    private Integer durationMinutes = 60;

    private Integer score;

    @Column(length = 1000)
    private String feedback;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private InterviewStatus status = InterviewStatus.SCHEDULED;

    private Integer questionsAttempted;
    private Integer questionsSolved;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
