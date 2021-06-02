package dev.mazurkiewicz.florystyka.answer;

public class Answer {

    private final AnswerType value;
    private final String content;

    public Answer(AnswerType value, String content) {
        this.value = value;
        this.content = content;
    }

    public AnswerType getValue() {
        return value;
    }

    public String getContent() {
        return content;
    }
}