package com.placement.codeedge.controller;

import com.placement.codeedge.model.MockInterview;
import com.placement.codeedge.model.enums.InterviewStatus;
import com.placement.codeedge.model.enums.Topic;
import com.placement.codeedge.service.CustomUserDetailsService.CustomUserDetails;
import com.placement.codeedge.service.InterviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Tag(name = "Interviews", description = "Mock interview scheduling endpoints")
public class MockInterviewController {

    private final InterviewService interviewService;

    // ─── MVC ──────────────────────────────────────────────────────────────
    @GetMapping("/interview")
    public String interviewPage(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        String userId = userDetails.getId();
        model.addAttribute("interviews", interviewService.getAll(userId));
        model.addAttribute("topics", Topic.values());
        model.addAttribute("scheduledCount", interviewService.countByStatus(userId, InterviewStatus.SCHEDULED));
        model.addAttribute("completedCount", interviewService.countByStatus(userId, InterviewStatus.COMPLETED));
        model.addAttribute("avgScore", String.format("%.0f", interviewService.averageScore(userId)));
        return "interview";
    }

    @PostMapping("/interview/schedule")
    public String scheduleInterview(
            @RequestParam String title,
            @RequestParam(required = false) String targetCompany,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime scheduledTime,
            @RequestParam(required = false, defaultValue = "60") Integer durationMinutes,
            @RequestParam(required = false) String[] topics,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            RedirectAttributes redirectAttributes) {
        interviewService.schedule(userDetails.getId(), title, targetCompany, scheduledTime, durationMinutes, topics);
        redirectAttributes.addFlashAttribute("success", "Interview scheduled successfully!");
        return "redirect:/interview";
    }

    @PostMapping("/interview/{id}/complete")
    public String completeInterview(
            @PathVariable String id,
            @RequestParam Integer score,
            @RequestParam(required = false, defaultValue = "0") Integer attempted,
            @RequestParam(required = false, defaultValue = "0") Integer solved,
            @RequestParam(required = false) String feedback,
            RedirectAttributes redirectAttributes) {
        interviewService.complete(id, score, attempted, solved, feedback);
        redirectAttributes.addFlashAttribute("success", "Interview marked as completed!");
        return "redirect:/interview";
    }

    @PostMapping("/interview/{id}/cancel")
    public String cancelInterview(@PathVariable String id, RedirectAttributes redirectAttributes) {
        interviewService.cancel(id);
        redirectAttributes.addFlashAttribute("success", "Interview cancelled.");
        return "redirect:/interview";
    }

    // ─── REST API ──────────────────────────────────────────────────────────
    @GetMapping("/api/interviews")
    @ResponseBody
    @Operation(summary = "Get all mock interviews")
    public List<MockInterview> getAllInterviews(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return interviewService.getAll(userDetails.getId());
    }

    @PostMapping("/api/interviews/{id}/complete")
    @ResponseBody
    @Operation(summary = "Mark interview as completed with score")
    public ResponseEntity<MockInterview> completeApi(
            @PathVariable String id,
            @RequestParam Integer score,
            @RequestParam(required = false) Integer attempted,
            @RequestParam(required = false) Integer solved,
            @RequestParam(required = false) String feedback) {
        try {
            return ResponseEntity.ok(interviewService.complete(id, score, attempted, solved, feedback));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
