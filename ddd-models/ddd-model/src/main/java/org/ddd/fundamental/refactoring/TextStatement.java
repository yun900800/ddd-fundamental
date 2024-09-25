package org.ddd.fundamental.refactoring;

public class TextStatement extends Statement{

    String headerString(Customer customer){
        return "Rental Record for "+ customer.getName() +"\n";
    }

    String eachRentalString(Rental rental){
        return "\t" + rental.getMovie().getTitle() +"\t" + String.valueOf(rental.getCharge())+"\n";
    }

    String footerString(Customer customer){
        return "Amount owned is " +String.valueOf(customer.getTotalCharge())+"\n" +
                "You earned "+String.valueOf(customer.getTotalFrequentRenterPoints()) + " frequent renter points" ;
    }
}
