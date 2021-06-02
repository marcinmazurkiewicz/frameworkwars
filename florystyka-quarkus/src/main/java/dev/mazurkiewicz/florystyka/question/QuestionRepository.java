package dev.mazurkiewicz.florystyka.question;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class QuestionRepository {
    private final EntityManager em;

    public QuestionRepository(EntityManager em) {
        this.em = em;
    }

    List<Question> findAll() {
        return em.createQuery("SELECT q FROM Question q").getResultList();
    }

    public Set<Question> getRandomQuestions(int howMany) {
        return (Set<Question>) em.createNativeQuery("SELECT * FROM questions ORDER BY RANDOM() LIMIT :howMany", Question.class)
                .setParameter("howMany", howMany)
                .getResultStream()
                .collect(Collectors.toSet());
    }

    public Optional<Question> findById(Integer id) {
        return Optional.ofNullable(em.find(Question.class, id));
    }

    public long count() {
        return ((Number) em.createNativeQuery("SELECT COUNT(q) FROM questions q")
                .getSingleResult()).longValue();
    }

    public Integer getEarliestYear() {
        return (Integer) em.createQuery("SELECT MIN(q.year) FROM Question AS q").getSingleResult();
    }

    public Integer getLatestYear() {
        return (Integer) em.createQuery("SELECT MAX(q.year) FROM Question AS q").getSingleResult();
    }
}
