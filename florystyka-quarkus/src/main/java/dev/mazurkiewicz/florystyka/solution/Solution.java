package dev.mazurkiewicz.florystyka.solution;

import dev.mazurkiewicz.florystyka.answer.AnswerType;

import javax.persistence.*;

@Entity
@Table(name = "solutions")
public class Solution {

    @Id
    Integer id;
    @Enumerated(EnumType.STRING)
    private AnswerType correct;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public AnswerType getCorrect() {
        return correct;
    }

    public void setCorrect(AnswerType correct) {
        this.correct = correct;
    }
}
