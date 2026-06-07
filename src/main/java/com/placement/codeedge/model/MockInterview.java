package com.placement.codeedge.model;

import com.placement.codeedge.model.enums.InterviewStatus;
import com.placement.codeedge.model.enums.Topic;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "mock_interviews")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MockInterview {

    @Id
    private String id;

    private String userId;

    private String title;

    private String targetCompany;

    @Builder.Default
    private List<Topic> focusTopics = new ArrayList<>();

    private LocalDateTime scheduledTime;

    @Builder.Default
    private Integer durationMinutes = 60;

    private Integer score;

    private String feedback;

    @Builder.Default
    private InterviewStatus status = InterviewStatus.SCHEDULED;

    private Integer questionsAttempted;
    private Integer questionsSolved;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
}
