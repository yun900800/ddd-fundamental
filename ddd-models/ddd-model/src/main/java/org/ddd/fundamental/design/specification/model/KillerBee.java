package org.ddd.fundamental.design.specification.model;

import org.ddd.fundamental.design.specification.constant.Color;
import org.ddd.fundamental.design.specification.constant.Movement;
import org.ddd.fundamental.design.specification.constant.Size;

public class KillerBee extends AbstractCreature{
    public KillerBee() {
        super("KillerBee", Size.SMALL, Movement.CRAWL, Color.RED, new Mass(20.0));
    }
}
