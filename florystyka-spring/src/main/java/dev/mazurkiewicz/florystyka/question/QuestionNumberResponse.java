package dev.mazurkiewicz.florystyka.question;

public class QuestionNumberResponse {

    private final  long questionNumber;
    private final  Integer earliestQuestionYear;
    private final  Integer latestQuestionYear;

    public QuestionNumberResponse(long questionNumber, Integer earliestQuestionYear, Integer latestQuestionYear) {
        this.questionNumber = questionNumber;
        this.earliestQuestionYear = earliestQuestionYear;
        this.latestQuestionYear = latestQuestionYear;
    }

    public long getQuestionNumber() {
        return questionNumber;
    }

    public Integer getEarliestQuestionYear() {
        return earliestQuestionYear;
    }

    public Integer getLatestQuestionYear() {
        return latestQuestionYear;
    }
}
