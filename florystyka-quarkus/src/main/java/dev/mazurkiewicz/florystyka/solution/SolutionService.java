package dev.mazurkiewicz.florystyka.solution;

import dev.mazurkiewicz.florystyka.answer.AnswerType;
import dev.mazurkiewicz.florystyka.exception.IncorrectResultSizeDataAccessException;
import dev.mazurkiewicz.florystyka.exception.ResourceNotFoundException;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class SolutionService {

    private final SolutionRepository repository;

    public SolutionService(SolutionRepository repository) {
        this.repository = repository;
    }

    public SolutionResponse checkSingleAnswer(SolutionRequest solution) {
        return repository
                .findById(solution.getQuestionId())
                .map(s -> new SolutionResponse(Map.of(s.getId(), s.getCorrect())))
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Question with id %d doesn't exist", solution.getQuestionId())));
    }

    public SolutionResponse checkTest(List<SolutionRequest> solutions) {
        Set<Integer> questionIds = solutions.stream().map(SolutionRequest::getQuestionId).collect(Collectors.toSet());

        Map<Integer, AnswerType> solutionMap = repository.findAllById(questionIds).stream()
                .collect(Collectors.toMap(Solution::getId, Solution::getCorrect));

        if (!solutionMap.keySet().containsAll(questionIds)) {
            throw new IncorrectResultSizeDataAccessException("Some answers are missing", questionIds.size(), solutionMap.size());
        }

        long points = solutions.stream()
                .filter(s -> s.getSelectedAnswer().equals(solutionMap.get(s.getQuestionId())))
                .count();

        return new SolutionResponse(Long.valueOf(points).intValue(), solutionMap.size(), solutionMap);
    }

}
