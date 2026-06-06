package com.placement.codeedge.model;

import com.placement.codeedge.model.enums.Difficulty;
import com.placement.codeedge.model.enums.Topic;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "problems")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Problem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Topic topic;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Difficulty difficulty;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "problem_companies", joinColumns = @JoinColumn(name = "problem_id"))
    @Column(name = "company")
    @Builder.Default
    private List<String> companies = new ArrayList<>();

    @Column(length = 500)
    private String hint;

    private String leetcodeLink;

    private Double acceptanceRate;

    @Builder.Default
    private boolean solved = false;

    @Column(length = 1000)
    private String notes;

    private Integer timeTakenMinutes;

    @CreationTimestamp
    private LocalDateTime createdAt;

    private LocalDateTime solvedAt;
}
