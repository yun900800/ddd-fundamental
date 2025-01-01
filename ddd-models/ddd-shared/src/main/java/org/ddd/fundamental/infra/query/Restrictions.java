package org.ddd.fundamental.infra.query;

import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.Collection;

public class Restrictions {
    private Restrictions() {
    }

    public static SimpleExpression eq(String fieldName, Object value, boolean ignoreNull) {
        if (ignoreNull && (ObjectUtils.isEmpty(value)))
            return null;
        return new SimpleExpression(fieldName, value, Criterion.Operator.EQ);
    }

    public static SimpleExpression ne(String fieldName, Object value, boolean ignoreNull) {
        if (ignoreNull && (ObjectUtils.isEmpty(value)))
            return null;
        return new SimpleExpression(fieldName, value,  Criterion.Operator.NE);
    }

    public static SimpleExpression like(String fieldName, String value, boolean ignoreNull) {
        if (ignoreNull && (ObjectUtils.isEmpty(value)))
            return null;
        return new SimpleExpression(fieldName, value.toUpperCase(),  Criterion.Operator.LIKE);
    }

    public static SimpleExpression gt(String fieldName, Object value, boolean ignoreNull) {
        if (ignoreNull && (ObjectUtils.isEmpty(value)))
            return null;
        return new SimpleExpression(fieldName, value,  Criterion.Operator.GT);
    }

    public static SimpleExpression lt(String fieldName, Object value, boolean ignoreNull) {
        if (ignoreNull && (ObjectUtils.isEmpty(value)))
            return null;
        return new SimpleExpression(fieldName, value,  Criterion.Operator.LT);
    }

    public static SimpleExpression gte(String fieldName, Object value, boolean ignoreNull) {
        if (ignoreNull && (ObjectUtils.isEmpty(value)))
            return null;
        return new SimpleExpression(fieldName, value,  Criterion.Operator.GTE);
    }

    public static SimpleExpression lte(String fieldName, Object value, boolean ignoreNull) {
        if (ignoreNull && (ObjectUtils.isEmpty(value)))
            return null;
        return new SimpleExpression(fieldName, value,  Criterion.Operator.LTE);
    }

    public static SimpleExpression isNull(String fieldName, boolean ignoreNull) {
        if (ignoreNull)
            return null;
        return new SimpleExpression(fieldName, null,  Criterion.Operator.ISNULL);
    }

    public static LogicalExpression and(Criterion... criterions) {
        return new LogicalExpression(criterions,  Criterion.Operator.AND);
    }

    public static LogicalExpression or(Criterion... criterions) {
        return new LogicalExpression(criterions,  Criterion.Operator.OR);
    }

    public static <E> LogicalExpression in(String fieldName, Collection<E> value, boolean ignoreNull) {
        if (ignoreNull && CollectionUtils.isEmpty(value))
            return null;

        SimpleExpression[] ses = new SimpleExpression[value.size()];
        int i = 0;
        for (Object obj : value) {
            if(obj instanceof String) {
                ses[i] = new SimpleExpression(fieldName, String.valueOf(obj),  Criterion.Operator.IGNORECASEEQ);
            } else {
                ses[i] = new SimpleExpression(fieldName, obj,  Criterion.Operator.EQ);
            }
            i++;
        }
        return new LogicalExpression(ses,  Criterion.Operator.OR);
    }

    public static Long convertToLong(Object o) {
        String stringToConvert = String.valueOf(o);
        if (!"null".equals(stringToConvert)) {
            return Long.parseLong(stringToConvert);
        } else {
            return Long.valueOf(0);
        }
    }
}
