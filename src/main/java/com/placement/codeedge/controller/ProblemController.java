package com.placement.codeedge.controller;

import com.placement.codeedge.model.Problem;
import com.placement.codeedge.model.enums.Difficulty;
import com.placement.codeedge.model.enums.Topic;
import com.placement.codeedge.service.ProblemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Tag(name = "Problems", description = "DSA Problem management endpoints")
public class ProblemController {

    private final ProblemService problemService;

    // ─── MVC (Thymeleaf page) ──────────────────────────────────────────────
    @GetMapping("/problems")
    public String problemsPage(
            @RequestParam(required = false) String topic,
            @RequestParam(required = false) String difficulty,
            @RequestParam(required = false) String solved,
            @RequestParam(required = false) String search,
            Model model) {

        List<Problem> problems = problemService.getFiltered(topic, difficulty, solved, search);

        model.addAttribute("problems", problems);
        model.addAttribute("topics", Topic.values());
        model.addAttribute("difficulties", Difficulty.values());
        model.addAttribute("selectedTopic", topic);
        model.addAttribute("selectedDifficulty", difficulty);
        model.addAttribute("selectedSolved", solved);
        model.addAttribute("searchQuery", search);
        model.addAttribute("totalCount", problems.size());
        model.addAttribute("solvedCount", problems.stream().filter(Problem::isSolved).count());

        return "problems";
    }

    // ─── REST API ──────────────────────────────────────────────────────────
    @GetMapping("/api/problems")
    @ResponseBody
    @Operation(summary = "Get filtered problems", description = "Filter by topic, difficulty, solved status, or keyword")
    public List<Problem> getProblems(
            @RequestParam(required = false) String topic,
            @RequestParam(required = false) String difficulty,
            @RequestParam(required = false) String solved,
            @RequestParam(required = false) String search) {
        return problemService.getFiltered(topic, difficulty, solved, search);
    }

    @GetMapping("/api/problems/{id}")
    @ResponseBody
    @Operation(summary = "Get problem by ID")
    public ResponseEntity<Problem> getById(@PathVariable Long id) {
        return problemService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/api/problems/{id}/solve")
    @ResponseBody
    @Operation(summary = "Mark a problem as solved")
    public ResponseEntity<Problem> markSolved(
            @PathVariable Long id,
            @RequestParam(required = false) Integer timeTaken,
            @RequestParam(required = false) String notes) {
        try {
            return ResponseEntity.ok(problemService.markSolved(id, timeTaken, notes));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/api/problems/{id}/unsolved")
    @ResponseBody
    @Operation(summary = "Mark a problem as unsolved")
    public ResponseEntity<Problem> markUnsolved(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(problemService.markUnsolved(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/api/problems/{id}/notes")
    @ResponseBody
    @Operation(summary = "Update notes for a problem")
    public ResponseEntity<Problem> updateNotes(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {
        try {
            return ResponseEntity.ok(problemService.updateNotes(id, body.get("notes")));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
