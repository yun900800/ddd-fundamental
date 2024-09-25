package org.ddd.fundamental.refactoring.extractsubclass;

public class JobItem {

    private int unitPrice;

    private int quantity;

    protected JobItem(int unitPrice, int quantity){
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }


    public int getTotalPrice(){
        return getUnitPrice() * getQuantity();
    }

    public int getUnitPrice(){
        return unitPrice;
    }

    public int getQuantity(){
        return quantity;
    }

}

class Employee{
    private int rate;
    public Employee(int rate){
        this.rate = rate;
    }

    public int getRate() {
        return rate;
    }
}
