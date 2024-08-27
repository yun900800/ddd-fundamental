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

    private double getTotalCharge(){
        double result =  0;
        for (Rental rental: rentals) {
            result+=rental.getCharge();
        }
        return result;
    }

    private int  getTotalFrequentRenterPoints(){
        int frequentRenterPoints =  0;
        for (Rental rental: rentals) {
            frequentRenterPoints = rental.getFrequentRenterPoints();
        }
        return frequentRenterPoints;
    }


    public String statement(){
        String result = "Rental Record for "+ getName() +"\n";
        for (Rental rental: rentals) {
            result+= "\t" + rental.getMovie().getTitle() +"\t" + String.valueOf(rental.getCharge())+"\n";

        }
        result +="Amount owned is " +String.valueOf(getTotalCharge())+"\n";
        result += "You earned "+String.valueOf(getTotalFrequentRenterPoints()) + " frequent renter points" ;
        return result;
    }

    public String htmlStatement(){
        String result = "<H1>Rental Record for <EM>"+ getName() +"</EM></H1><P>\n";
        for (Rental rental: rentals) {
            result+=rental.getMovie().getTitle() +":" + String.valueOf(rental.getCharge())+"<BR>\n";

        }
        result +="<P>Amount owned<EM> is " +String.valueOf(getTotalCharge())+"</EM></P>\n";
        result += "You earned <EM>"+String.valueOf(getTotalFrequentRenterPoints()) + "</EM> frequent renter points</P>" ;
        return result;
    }
}
