package org.ddd.fundamental.design.specification.model;


import org.ddd.fundamental.design.specification.constant.Color;
import org.ddd.fundamental.design.specification.constant.Movement;
import org.ddd.fundamental.design.specification.constant.Size;

public class Dragon extends AbstractCreature{
    public Dragon() {
        super("Dragon", Size.LARGE, Movement.FLYING, Color.RED, new Mass(39300.0));
    }
}
