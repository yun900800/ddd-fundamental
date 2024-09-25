package org.ddd.fundamental.refactoring;

import java.util.ArrayList;
import java.util.List;

public class Customer {

    private String name;

    private List<Rental> rentals = new ArrayList<>();

    public Customer(String name){
        this.name = name;
    }

    public Customer addRental(Rental rental){
        this.rentals.add(rental);
        return this;
    }

    public List<Rental> getRentals() {
        return new ArrayList<>(rentals);
    }

    public String getName() {
        return name;
    }

    private double amountFor(Rental aRental){
        return aRental.getCharge();
    }

    public double getTotalCharge(){
        double result =  0;
        for (Rental rental: rentals) {
            result+=rental.getCharge();
        }
        return result;
    }

    public int  getTotalFrequentRenterPoints(){
        int frequentRenterPoints =  0;
        for (Rental rental: rentals) {
            frequentRenterPoints = rental.getFrequentRenterPoints();
        }
        return frequentRenterPoints;
    }


    public String statement(){
        Statement statement = new TextStatement();
        return statement.value(this);
    }

    public String htmlStatement(){
        Statement statement = new HtmlStatement();
        return statement.value(this);
    }
}
