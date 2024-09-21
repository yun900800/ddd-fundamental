package org.ddd.fundamental.design.specification.model;

import org.ddd.fundamental.design.specification.constant.Color;
import org.ddd.fundamental.design.specification.constant.Movement;
import org.ddd.fundamental.design.specification.constant.Size;

public class Shark extends AbstractCreature{
    public Shark(){
        super("Shark", Size.LARGE, Movement.RUN, Color.BLUE, new Mass(59999.0));
    }
}
