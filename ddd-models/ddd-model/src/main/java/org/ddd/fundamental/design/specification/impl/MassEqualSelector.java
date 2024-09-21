package org.ddd.fundamental.design.specification.impl;

import org.ddd.fundamental.design.specification.AbstractSelector;
import org.ddd.fundamental.design.specification.model.Creature;
import org.ddd.fundamental.design.specification.model.Mass;

public class MassEqualSelector extends AbstractSelector<Creature> {

    private final Mass mass;

    public MassEqualSelector(double mass) {
        this.mass = new Mass(mass);
    }

    @Override
    public boolean test(Creature t) {
        return t.getMass().equal(t.getMass());
    }

}
