package org.ddd.fundamental.generics.example;

import org.ddd.fundamental.generics.Generators;
import org.ddd.fundamental.generics.interfaces.Generator;

import java.util.ArrayList;

public class CargoShip extends ArrayList<Container> {
    public CargoShip(int containerNumber, int productNumber) {
        for (int i = 0; i < containerNumber; i++) {
            add(new Container(productNumber));
        }
    }

    public static void main(String[] args) {
        new CargoShip(10, 20);
    }
}

class Product1 {
    private final int id;
    private String description;
    private double price;

    public Product1(int id, String description, double price) {
        this.id = id;
        this.description = description;
        this.price = price;
        System.out.println(toString());
    }

    public String toString() {
        return id + ": " + description + ", price: $" + price;
    }

    public void priceChange(double change) {
        price += change;
    }

    public static Generator<Product1> generator = () -> new Product1((int) (Math.random() * 1000),
            "Test", Math.random() * 1000.0);
}

class Container extends ArrayList<Product1> {
    public Container(int productNumber) {
        Generators.fill(this, Product1.generator, productNumber);
    }
}
