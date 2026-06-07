package com.placement.codeedge.model;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SolvedProblem {
    private String problemId;
    private String notes;
    private Integer timeTakenMinutes;
    private LocalDateTime solvedAt;
}
