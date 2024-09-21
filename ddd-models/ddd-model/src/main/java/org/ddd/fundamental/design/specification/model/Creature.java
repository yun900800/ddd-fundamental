package org.ddd.fundamental.design.specification.model;

import org.ddd.fundamental.design.specification.constant.Color;
import org.ddd.fundamental.design.specification.constant.Movement;
import org.ddd.fundamental.design.specification.constant.Size;

public interface Creature {
    String getName();

    Size getSize();

    Movement getMovement();

    Color getColor();

    Mass getMass();
}

