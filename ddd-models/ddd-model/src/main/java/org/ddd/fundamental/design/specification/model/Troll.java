package org.ddd.fundamental.design.specification.model;

import org.ddd.fundamental.design.specification.constant.Color;
import org.ddd.fundamental.design.specification.constant.Movement;
import org.ddd.fundamental.design.specification.constant.Size;

public class Troll extends AbstractCreature{
    public Troll(){
        super("Troll", Size.SMALL, Movement.CRAWL, Color.RED, new Mass(866.0));
    }
}
