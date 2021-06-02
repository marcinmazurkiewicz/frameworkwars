package dev.mazurkiewicz.florystyka.answer;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum AnswerType {
    A, B, C, D, EMPTY;

    @JsonCreator
    public static AnswerType parse(String value) {
        AnswerType result;
        try {
            result = AnswerType.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            result = AnswerType.EMPTY;
        }
        return result;
    }
}
