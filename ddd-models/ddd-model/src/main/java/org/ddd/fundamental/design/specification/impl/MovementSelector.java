package org.ddd.fundamental.design.specification.impl;

import org.ddd.fundamental.design.specification.AbstractSelector;
import org.ddd.fundamental.design.specification.constant.Movement;
import org.ddd.fundamental.design.specification.model.Creature;


public class MovementSelector extends AbstractSelector<Creature> {

    private final Movement movement;

    public MovementSelector(Movement m) {
        this.movement = m;
    }

    @Override
    public boolean test(Creature t) {
        return t.getMovement().equals(movement);
    }


}
