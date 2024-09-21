package org.ddd.fundamental.design.specification.model;

import org.ddd.fundamental.design.specification.constant.Color;
import org.ddd.fundamental.design.specification.constant.Movement;
import org.ddd.fundamental.design.specification.constant.Size;

public abstract class AbstractCreature implements Creature{

    private String name;

    private Size size;

    private Movement movement;

    private Color color;

    private Mass mass;

    public AbstractCreature(String name, Size size, Movement movement, Color color,Mass mass){
        this.name = name;
        this.size = size;
        this.movement = movement;
        this.color = color;
        this.mass = mass;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Size getSize() {
        return size;
    }

    @Override
    public Movement getMovement() {
        return movement;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public Mass getMass() {
        return mass;
    }
}
