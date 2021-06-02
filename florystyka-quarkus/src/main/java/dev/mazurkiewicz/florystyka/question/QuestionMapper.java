package dev.mazurkiewicz.florystyka.question;

import dev.mazurkiewicz.florystyka.answer.Answer;
import dev.mazurkiewicz.florystyka.answer.AnswerType;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class QuestionMapper {

    public QuestionResponse mapEntityToResponse(Question entity) {
        if (entity == null)
            throw new IllegalStateException("Question cannot be empty");

        List<Answer> answers = new ArrayList<>();
        answers.add(new Answer(AnswerType.A, entity.getAnswerA()));
        answers.add(new Answer(AnswerType.B, entity.getAnswerB()));
        answers.add(new Answer(AnswerType.C, entity.getAnswerC()));
        answers.add(new Answer(AnswerType.D, entity.getAnswerD()));
        String img = null;
        if (entity.getImg() != null && !entity.getImg().isEmpty()) {
            img = String.format("/resources/img/%s", entity.getImg());
        }
        return new QuestionResponse(entity.getId(), entity.getContent(), answers, img);
    }
}

