package dev.mazurkiewicz.florystyka.solution;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@ApplicationScoped
public class SolutionRepository {
    private final EntityManager em;

    public SolutionRepository(EntityManager em) {
        this.em = em;
    }

    public Optional<Solution> findById(int questionId) {
        return Optional.ofNullable(em.find(Solution.class, questionId));
    }

    public List<Solution> findAllById(Set<Integer> questionIds) {
        return em.createQuery("SELECT s FROM Solution s WHERE s.id IN :ids", Solution.class)
                .setParameter("ids", questionIds)
                .getResultList();
    }
}
