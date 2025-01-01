package org.ddd.fundamental.infra.query;

import org.apache.commons.lang3.StringUtils;

import javax.persistence.criteria.*;

public class SimpleExpression implements Criterion {
    private String fieldName;
    private Object value;
    private Operator operator;

    protected SimpleExpression(String fieldName, Object value, Operator operator) {
        this.fieldName = fieldName;
        this.value = value;
        this.operator = operator;
    }

    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Predicate toPredicate(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        Path expression = null;
        if (fieldName.contains(".")) {
            String[] names = StringUtils.split(fieldName, ".");
            if(names!=null && names.length>0) {
                expression = root.get(names[0]);
                for (int i = 1; i < names.length; i++) {
                    expression = expression.get(names[i]);
                }
            }
        } else {
            expression = root.get(fieldName);
        }

        switch (operator) {
            case EQ:
                return builder.equal(expression, value);
            case IGNORECASEEQ:
                return builder.equal(builder.upper(expression), value.toString().toUpperCase());
            case NE:
                return builder.notEqual(expression, value);
            case LIKE:
                return builder.like(builder.upper(expression), value.toString().toUpperCase() + "%");
            case LT:
                return builder.lessThan(expression, (Comparable) value);
            case GT:
                return builder.greaterThan(expression, (Comparable) value);
            case LTE:
                return builder.lessThanOrEqualTo(expression, (Comparable) value);
            case GTE:
                return builder.greaterThanOrEqualTo(expression, (Comparable) value);
            case ISNULL:
                return builder.isNull(expression);
            default:
                return null;
        }
    }
}
