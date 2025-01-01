package org.ddd.fundamental.infra.query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public interface Criterion {
    public enum Operator {
        EQ, IGNORECASEEQ, NE, LIKE, GT, LT, GTE, LTE, AND, OR, ISNULL
    }

    public Predicate toPredicate(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder builder);
}
