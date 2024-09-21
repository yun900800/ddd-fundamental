package org.ddd.fundamental.design.specification.impl;


import org.ddd.fundamental.design.specification.AbstractSelector;

public class ConjunctionSelector<T> extends AbstractSelector<T> {

    private AbstractSelector<T> selector;

    private AbstractSelector<T> other;

    public ConjunctionSelector(AbstractSelector<T> selector, AbstractSelector<T> other){
        this.selector = selector;
        this.other = other;
    }


    @Override
    public boolean test(T t) {
        return selector.test(t) && other.test(t);
    }
}
