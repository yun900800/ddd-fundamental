package org.ddd.fundamental.design.specification.model;

import org.ddd.fundamental.design.specification.constant.Color;
import org.ddd.fundamental.design.specification.constant.Movement;
import org.ddd.fundamental.design.specification.constant.Size;

public class Octopus extends AbstractCreature{
    public Octopus(){
        super("Octopus", Size.MIDDLE, Movement.RUN, Color.GREEN, new Mass(499.0));
    }
}
