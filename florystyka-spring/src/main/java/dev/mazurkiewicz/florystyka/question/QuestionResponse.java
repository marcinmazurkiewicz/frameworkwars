package dev.mazurkiewicz.florystyka.question;

import com.fasterxml.jackson.annotation.JsonInclude;
import dev.mazurkiewicz.florystyka.answer.Answer;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuestionResponse {

    private final Integer id;
    private final String content;
    private final List<Answer> answers;
    private final String img;

    public QuestionResponse(Integer id, String content, List<Answer> answers, String img) {
        this.id = id;
        this.content = content;
        this.answers = answers;
        this.img = img;
    }

    public Integer getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public String getImg() {
        return img;
    }
}
