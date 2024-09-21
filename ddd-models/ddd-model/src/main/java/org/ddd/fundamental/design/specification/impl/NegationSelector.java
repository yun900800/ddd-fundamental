package org.ddd.fundamental.design.specification.impl;

import org.ddd.fundamental.design.specification.AbstractSelector;

public class NegationSelector<T> extends AbstractSelector<T> {

    private AbstractSelector<T> selector;

    public NegationSelector(AbstractSelector<T> selector){
        this.selector = selector;
    }
    @Override
    public boolean test(T t) {
        return !selector.test(t);
    }
}
