package org.ddd.fundamental.design.specification;

import org.ddd.fundamental.design.specification.impl.ConjunctionSelector;
import org.ddd.fundamental.design.specification.impl.DisjunctionSelector;
import org.ddd.fundamental.design.specification.impl.NegationSelector;

import java.util.function.Predicate;

public abstract class AbstractSelector<T> implements Predicate<T> {
    public AbstractSelector<T> and(AbstractSelector<T> other) {
        return new ConjunctionSelector(this, other);
    }

    public AbstractSelector<T> not() {
        return new NegationSelector(this);
    }

    public AbstractSelector<T> or(AbstractSelector<T> other) {
        return new DisjunctionSelector(this,other);
    }
}
