package dev.mazurkiewicz.florystyka.solution;

import com.fasterxml.jackson.annotation.JsonInclude;
import dev.mazurkiewicz.florystyka.answer.AnswerType;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SolutionResponse {

    private final Integer points;
    private final Integer total;
    private final Map<Integer, AnswerType> solutions;

    public SolutionResponse(Integer points, Integer total, Map<Integer, AnswerType> solutions) {
        this.points = points;
        this.total = total;
        this.solutions = solutions;
    }

    public SolutionResponse(Map<Integer, AnswerType> solutions) {
        this(null, null, solutions);
    }

}
