package org.ddd.fundamental.design.specification.model;

import org.ddd.fundamental.design.specification.constant.Color;
import org.ddd.fundamental.design.specification.constant.Movement;
import org.ddd.fundamental.design.specification.constant.Size;

public class Goblin extends AbstractCreature{
    public Goblin() {
        super("Goblin", Size.SMALL, Movement.CRAWL, Color.BLUE, new Mass(5000.0));
    }
}
