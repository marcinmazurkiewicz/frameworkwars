package dev.mazurkiewicz.florystyka.solution;

import dev.mazurkiewicz.florystyka.answer.AnswerType;

import javax.validation.constraints.Min;

public class SolutionRequest {

    @Min(1)
    private final int questionId;
    private final AnswerType selectedAnswer;

    public SolutionRequest(int questionId, AnswerType selectedAnswer) {
        this.questionId = questionId;
        this.selectedAnswer = selectedAnswer;
    }

    public int getQuestionId() {
        return questionId;
    }

    public AnswerType getSelectedAnswer() {
        return selectedAnswer;
    }
}
