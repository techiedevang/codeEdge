package com.placement.codeedge.controller;

import com.placement.codeedge.model.User;
import com.placement.codeedge.model.enums.Difficulty;
import com.placement.codeedge.model.enums.Topic;
import com.placement.codeedge.service.CustomUserDetailsService.CustomUserDetails;
import com.placement.codeedge.service.InterviewService;
import com.placement.codeedge.service.ProblemService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final ProblemService problemService;
    private final InterviewService interviewService;

    @GetMapping("/")
    public String dashboard(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        User user = userDetails.getUser();
        String userId = user.getId();

        long total = problemService.countTotal();
        long solved = problemService.countSolved(user);

        model.addAttribute("totalProblems", total);
        model.addAttribute("solvedProblems", solved);
        model.addAttribute("unsolvedProblems", total - solved);
        model.addAttribute("progressPercent", total > 0 ? (int)((solved * 100) / total) : 0);

        // Difficulty breakdown
        model.addAttribute("easyTotal",  problemService.countByDifficulty(Difficulty.EASY));
        model.addAttribute("easySolved", problemService.countSolvedByDifficulty(user, Difficulty.EASY));
        model.addAttribute("medTotal",   problemService.countByDifficulty(Difficulty.MEDIUM));
        model.addAttribute("medSolved",  problemService.countSolvedByDifficulty(user, Difficulty.MEDIUM));
        model.addAttribute("hardTotal",  problemService.countByDifficulty(Difficulty.HARD));
        model.addAttribute("hardSolved", problemService.countSolvedByDifficulty(user, Difficulty.HARD));

        // Topic breakdown
        List<Map<String, Object>> topicStats = new ArrayList<>();
        for (Topic t : Topic.values()) {
            Map<String, Object> stat = new LinkedHashMap<>();
            long tTotal  = problemService.countByTopic(t);
            long tSolved = problemService.countSolvedByTopic(user, t);
            stat.put("name", t.getDisplayName());
            stat.put("total", tTotal);
            stat.put("solved", tSolved);
            stat.put("percent", tTotal > 0 ? (int)((tSolved * 100) / tTotal) : 0);
            topicStats.add(stat);
        }
        model.addAttribute("topicStats", topicStats);

        // Interview stats
        model.addAttribute("scheduledInterviews", interviewService.countByStatus(userId,
                com.placement.codeedge.model.enums.InterviewStatus.SCHEDULED));
        model.addAttribute("completedInterviews", interviewService.countByStatus(userId,
                com.placement.codeedge.model.enums.InterviewStatus.COMPLETED));
        model.addAttribute("avgInterviewScore", String.format("%.0f", interviewService.averageScore(userId)));

        // Recent completed interviews (last 3)
        model.addAttribute("recentInterviews",
                interviewService.getAll(userId).stream()
                        .filter(i -> i.getStatus() == com.placement.codeedge.model.enums.InterviewStatus.COMPLETED)
                        .limit(3)
                        .toList());

        return "index";
    }
}
