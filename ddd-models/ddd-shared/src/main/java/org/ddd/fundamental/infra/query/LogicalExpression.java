package org.ddd.fundamental.infra.query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class LogicalExpression implements Criterion {
    private Criterion[] criterion;
    private Operator operator;

    public LogicalExpression(Criterion[] criterions, Operator operator) {
        this.criterion = criterions;
        this.operator = operator;
    }

    @Override
    public Predicate toPredicate(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();
        for(int i=0;i<this.criterion.length;i++){
            predicates.add(this.criterion[i].toPredicate(root, query, builder));
        }

        if(null != operator && operator.equals(Criterion.Operator.OR)) {
            return builder.or(predicates.toArray(new Predicate[predicates.size()]));
        }

        return null;
    }
}
