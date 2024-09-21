package org.ddd.fundamental.design.specification.impl;

import org.ddd.fundamental.design.specification.AbstractSelector;
import org.ddd.fundamental.design.specification.constant.Color;
import org.ddd.fundamental.design.specification.model.Creature;

public class ColorSelector extends AbstractSelector<Creature> {
    private final Color color;

    public ColorSelector(Color color) {
        super();
        this.color = color;
    }

    @Override
    public boolean test(Creature t) {
        return color.equals(t.getColor());
    }
}
