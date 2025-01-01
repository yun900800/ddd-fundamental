package org.ddd.fundamental.infra.query;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class Criteria<T> implements Specification<T> {
    private static final long serialVersionUID = 1L;

    private transient List<Criterion> criterions = new ArrayList<>();

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if (!criterions.isEmpty()) {
            List<Predicate> predicates = new ArrayList<>();
            for (Criterion c : criterions) {
                predicates.add(c.toPredicate(root, query, builder));
            }

            if (!predicates.isEmpty()) {
                return builder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        }
        return builder.conjunction();
    }

    public void add(Criterion criterion) {
        if (criterion != null) {
            criterions.add(criterion);
        }
    }
}
