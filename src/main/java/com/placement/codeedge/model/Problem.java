package com.placement.codeedge.model;

import com.placement.codeedge.model.enums.Difficulty;
import com.placement.codeedge.model.enums.Topic;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "problems")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Problem {

    @Id
    private String id;

    private String title;

    private Topic topic;

    private Difficulty difficulty;

    @Builder.Default
    private List<String> companies = new ArrayList<>();

    private String hint;

    private String leetcodeLink;

    private Double acceptanceRate;

    @Builder.Default
    private boolean solved = false;

    private String notes;

    private Integer timeTakenMinutes;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime solvedAt;
}
