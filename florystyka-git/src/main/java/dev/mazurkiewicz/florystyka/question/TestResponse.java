package dev.mazurkiewicz.florystyka.question;

import com.fasterxml.jackson.annotation.JsonInclude;
import dev.mazurkiewicz.florystyka.utils.TestTimer;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TestResponse {

    private final TestTimer timer;
    private final List<QuestionResponse> questions;

    public TestResponse(TestTimer timer, List<QuestionResponse> questions) {
        this.timer = timer;
        this.questions = questions;
    }

    public TestTimer getTimer() {
        return timer;
    }

    public List<QuestionResponse> getQuestions() {
        return questions;
    }
}
