package org.ddd.fundamental.core.generator.model;

public class CoffeeWithParam {

    private String name;

    private String color;

    public CoffeeWithParam(String name){
        this(name, "default");
    }

    public CoffeeWithParam(String name, String color){
        this.name = name;
        this.color = color;
    }

    public String toString() {
        return getClass().getSimpleName() + " " + name;
    }

}
