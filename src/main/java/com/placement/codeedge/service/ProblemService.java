package com.placement.codeedge.service;

import com.placement.codeedge.model.Problem;
import com.placement.codeedge.model.enums.Difficulty;
import com.placement.codeedge.model.enums.Topic;
import com.placement.codeedge.repository.ProblemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProblemService {

    private final ProblemRepository problemRepository;

    public List<Problem> getAll() {
        return problemRepository.findAll();
    }

    public List<Problem> getFiltered(String topic, String difficulty, String solved, String search) {
        // If there's a search keyword, filter by title first
        if (search != null && !search.isBlank()) {
            return problemRepository.searchByTitle(search.trim());
        }

        Topic t = (topic != null && !topic.isBlank() && !topic.equals("ALL"))
                ? Topic.valueOf(topic) : null;
        Difficulty d = (difficulty != null && !difficulty.isBlank() && !difficulty.equals("ALL"))
                ? Difficulty.valueOf(difficulty) : null;
        Boolean s = (solved != null && !solved.isBlank() && !solved.equals("ALL"))
                ? Boolean.valueOf(solved) : null;

        if (t != null && d != null && s != null) return problemRepository.findByTopicAndDifficultyAndSolved(t, d, s);
        if (t != null && d != null)               return problemRepository.findByTopicAndDifficulty(t, d);
        if (t != null && s != null)               return problemRepository.findByTopicAndSolved(t, s);
        if (d != null && s != null)               return problemRepository.findByDifficultyAndSolved(d, s);
        if (t != null)                            return problemRepository.findByTopic(t);
        if (d != null)                            return problemRepository.findByDifficulty(d);
        if (s != null)                            return problemRepository.findBySolved(s);

        return problemRepository.findAll();
    }

    public Optional<Problem> getById(Long id) {
        return problemRepository.findById(id);
    }

    @Transactional
    public Problem markSolved(Long id, Integer timeTaken, String notes) {
        Problem p = problemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Problem not found: " + id));
        p.setSolved(true);
        p.setSolvedAt(LocalDateTime.now());
        if (timeTaken != null) p.setTimeTakenMinutes(timeTaken);
        if (notes != null && !notes.isBlank()) p.setNotes(notes);
        return problemRepository.save(p);
    }

    @Transactional
    public Problem markUnsolved(Long id) {
        Problem p = problemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Problem not found: " + id));
        p.setSolved(false);
        p.setSolvedAt(null);
        p.setTimeTakenMinutes(null);
        return problemRepository.save(p);
    }

    @Transactional
    public Problem updateNotes(Long id, String notes) {
        Problem p = problemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Problem not found: " + id));
        p.setNotes(notes);
        return problemRepository.save(p);
    }

    public List<Problem> getByCompany(String company) {
        return problemRepository.findByCompany(company);
    }

    public long countSolved() {
        return problemRepository.countBySolved(true);
    }

    public long countTotal() {
        return problemRepository.count();
    }

    public long countByDifficulty(Difficulty d) {
        return problemRepository.countByDifficulty(d);
    }

    public long countSolvedByDifficulty(Difficulty d) {
        return problemRepository.countByDifficultyAndSolved(d, true);
    }

    public long countByTopic(Topic t) {
        return problemRepository.countByTopic(t);
    }

    public long countSolvedByTopic(Topic t) {
        return problemRepository.countByTopicAndSolved(t, true);
    }
}
