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

    public String getName() {
        return name;
    }

    private double amountFor(Rental aRental){
        return aRental.getCharge();
    }


    public String statement(){
        double totalAmount = 0;
        int frequentRenterPoints = 0;
        String result = "Rental Record for "+ getName() +"\n";
        for (Rental rental: rentals) {
            frequentRenterPoints = rental.getFrequentRenterPoints();
            result+= "\t" + rental.getMovie().getTitle() +"\t" + String.valueOf(rental.getCharge())+"\n";
            totalAmount+=rental.getCharge();
        }
        result +="Amount owned is " +String.valueOf(totalAmount)+"\n";
        result += "You earned "+String.valueOf(frequentRenterPoints) + " frequent renter points" ;
        return result;
    }
}
