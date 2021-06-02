package dev.mazurkiewicz.florystyka.question;

import dev.mazurkiewicz.florystyka.exception.EmptyResultDataAccessException;
import dev.mazurkiewicz.florystyka.exception.IncorrectResultSizeDataAccessException;
import dev.mazurkiewicz.florystyka.exception.PdfRenderException;
import dev.mazurkiewicz.florystyka.exception.ResourceNotFoundException;
import dev.mazurkiewicz.florystyka.pdf.PdfGenerator;
import dev.mazurkiewicz.florystyka.utils.TestTimer;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class QuestionService {

    private final QuestionRepository repository;
    private final QuestionMapper mapper;
    private final PdfGenerator pdfGenerator;
    private final int questionToTest;

    public QuestionService(QuestionRepository repository, QuestionMapper mapper, PdfGenerator pdfGenerator,
                           @ConfigProperty(name = "dev.mazurkiewicz.florystyka.testQuestionsNumber") int questionToTest) {
        this.repository = repository;
        this.mapper = mapper;
        this.pdfGenerator = pdfGenerator;
        this.questionToTest = questionToTest;
    }

    public List<QuestionResponse> getAllQuestions() {
        return repository.findAll()
                .stream()
                .map(mapper::mapEntityToResponse)
                .collect(Collectors.toList());
    }

    public QuestionResponse getRandomQuestion() {
        Set<Question> questionSet = repository.getRandomQuestions(1);
        if (questionSet.isEmpty())
            throw new EmptyResultDataAccessException("It looks like there is no questions in the database", 1);

        Question question = questionSet.iterator().next();
        return mapper.mapEntityToResponse(question);
    }

    public QuestionResponse getQuestionById(Integer id) {
        return repository
                .findById(id)
                .map(mapper::mapEntityToResponse)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Question with id %d doesn't exist", id)));
    }

    public TestResponse getQuestionsToTest() {
        Set<Question> result = repository.getRandomQuestions(questionToTest);
        if (result.size() != questionToTest)
            throw new IncorrectResultSizeDataAccessException("Incorrect questions number", questionToTest, result.size());
        List<QuestionResponse> questions = result.stream()
                .map(mapper::mapEntityToResponse)
                .collect(Collectors.toList());
        return new TestResponse(new TestTimer(60, 0), questions);
    }

    public QuestionNumberResponse countQuestions() {
        long questionNumber = repository.count();
        Integer earliestQuestionYear = repository.getEarliestYear();
        Integer latestQuestionYear = repository.getLatestYear();
        return new QuestionNumberResponse(questionNumber, earliestQuestionYear, latestQuestionYear);
    }

    public byte[] getPdfTest() throws PdfRenderException {
        Set<Question> questions = repository.getRandomQuestions(questionToTest);
        return pdfGenerator.generateTest(questions);
    }


}
