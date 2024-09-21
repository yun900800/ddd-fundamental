package org.ddd.fundamental.design.specification.impl;

import org.ddd.fundamental.design.specification.AbstractSelector;

public class DisjunctionSelector<T> extends AbstractSelector<T> {


    private AbstractSelector<T> selector;

    private AbstractSelector<T> other;

    public DisjunctionSelector(AbstractSelector<T> selector, AbstractSelector<T> other){
        this.selector = selector;
        this.other = other;
    }


    @Override
    public boolean test(T t) {
        return selector.test(t) || other.test(t);
    }
}
