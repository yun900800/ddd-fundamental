package org.ddd.fundamental.app.listener;

public class OrderConsumer {
    public void receiveOrder(String message) {
        System.out.printf("Order received: %s%n", message);
        //throw new RuntimeException("error msg");
    }

    public void receiveOrder1(String message) {
        System.out.printf("Order received1: %s%n", message);
    }

    public void receiveOrder2(String message) {
        System.out.printf("Order received2: %s%n", message);
    }
}
