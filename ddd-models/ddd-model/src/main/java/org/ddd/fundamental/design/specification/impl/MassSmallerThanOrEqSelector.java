package org.ddd.fundamental.design.specification.impl;

import org.ddd.fundamental.design.specification.AbstractSelector;
import org.ddd.fundamental.design.specification.model.Creature;
import org.ddd.fundamental.design.specification.model.Mass;

public class MassSmallerThanOrEqSelector extends AbstractSelector<Creature> {

    private final Mass mass;

    public MassSmallerThanOrEqSelector(double mass) {
        this.mass = new Mass(mass);
    }

    @Override
    public boolean test(Creature creature) {
        return !creature.getMass().greaterThan(mass);
    }
}
