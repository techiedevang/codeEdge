package com.placement.codeedge.service;

import com.placement.codeedge.model.Problem;
import com.placement.codeedge.model.SolvedProblem;
import com.placement.codeedge.model.User;
import com.placement.codeedge.model.enums.Difficulty;
import com.placement.codeedge.model.enums.Topic;
import com.placement.codeedge.repository.ProblemRepository;
import com.placement.codeedge.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@SuppressWarnings("null")
public class ProblemService {

    private final ProblemRepository problemRepository;
    private final UserRepository userRepository;

    private void populateTransientFields(Problem p, User user) {
        if (p == null || user == null) return;
        user.getSolvedProblems().stream()
                .filter(sp -> sp.getProblemId().equals(p.getId()))
                .findFirst()
                .ifPresentOrElse(sp -> {
                    p.setSolved(true);
                    p.setNotes(sp.getNotes());
                    p.setTimeTakenMinutes(sp.getTimeTakenMinutes());
                    p.setSolvedAt(sp.getSolvedAt());
                }, () -> {
                    p.setSolved(false);
                    p.setNotes(null);
                    p.setTimeTakenMinutes(null);
                    p.setSolvedAt(null);
                });
    }

    private void populateTransientFields(List<Problem> problems, User user) {
        if (problems == null || user == null) return;
        for (Problem p : problems) {
            populateTransientFields(p, user);
        }
    }

    public List<Problem> getAll(User user) {
        List<Problem> list = problemRepository.findAll();
        populateTransientFields(list, user);
        return list;
    }

    public List<Problem> getFiltered(User user, String topic, String difficulty, String solved, String search) {
        List<Problem> list;
        if (search != null && !search.isBlank()) {
            list = problemRepository.findByTitleContainingIgnoreCase(search.trim());
        } else {
            Topic t = (topic != null && !topic.isBlank() && !topic.equals("ALL"))
                    ? Topic.valueOf(topic) : null;
            Difficulty d = (difficulty != null && !difficulty.isBlank() && !difficulty.equals("ALL"))
                    ? Difficulty.valueOf(difficulty) : null;

            if (t != null && d != null) list = problemRepository.findByTopicAndDifficulty(t, d);
            else if (t != null)         list = problemRepository.findByTopic(t);
            else if (d != null)         list = problemRepository.findByDifficulty(d);
            else                        list = problemRepository.findAll();
        }

        populateTransientFields(list, user);

        if (solved != null && !solved.isBlank() && !solved.equals("ALL")) {
            boolean expectedSolved = Boolean.parseBoolean(solved);
            list = list.stream().filter(p -> p.isSolved() == expectedSolved).toList();
        }

        return list;
    }

    public Optional<Problem> getById(String id, User user) {
        Optional<Problem> opt = problemRepository.findById(id);
        opt.ifPresent(p -> populateTransientFields(p, user));
        return opt;
    }

    public Problem markSolved(User user, String id, Integer timeTaken, String notes) {
        Problem p = problemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Problem not found: " + id));

        user.getSolvedProblems().removeIf(sp -> sp.getProblemId().equals(id));

        SolvedProblem sp = SolvedProblem.builder()
                .problemId(id)
                .notes(notes)
                .timeTakenMinutes(timeTaken)
                .solvedAt(LocalDateTime.now())
                .build();
        user.getSolvedProblems().add(sp);
        userRepository.save(user);

        populateTransientFields(p, user);
        return p;
    }

    public Problem markUnsolved(User user, String id) {
        Problem p = problemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Problem not found: " + id));

        user.getSolvedProblems().removeIf(sp -> sp.getProblemId().equals(id));
        userRepository.save(user);

        populateTransientFields(p, user);
        return p;
    }

    public Problem updateNotes(User user, String id, String notes) {
        Problem p = problemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Problem not found: " + id));

        user.getSolvedProblems().stream()
                .filter(sp -> sp.getProblemId().equals(id))
                .findFirst()
                .ifPresent(sp -> {
                    sp.setNotes(notes);
                    userRepository.save(user);
                });

        populateTransientFields(p, user);
        return p;
    }

    public List<Problem> getByCompany(User user, String company) {
        List<Problem> list = problemRepository.findByCompaniesContainingIgnoreCase(company);
        populateTransientFields(list, user);
        return list;
    }

    public long countSolved(User user) {
        return user.getSolvedProblems().size();
    }

    public long countTotal() {
        return problemRepository.count();
    }

    public long countByDifficulty(Difficulty d) {
        return problemRepository.countByDifficulty(d);
    }

    public long countSolvedByDifficulty(User user, Difficulty d) {
        List<String> solvedIds = user.getSolvedProblems().stream().map(SolvedProblem::getProblemId).toList();
        return problemRepository.findAllById(solvedIds).stream()
                .filter(p -> p.getDifficulty() == d)
                .count();
    }

    public long countByTopic(Topic t) {
        return problemRepository.countByTopic(t);
    }

    public long countSolvedByTopic(User user, Topic t) {
        List<String> solvedIds = user.getSolvedProblems().stream().map(SolvedProblem::getProblemId).toList();
        return problemRepository.findAllById(solvedIds).stream()
                .filter(p -> p.getTopic() == t)
                .count();
    }
}
