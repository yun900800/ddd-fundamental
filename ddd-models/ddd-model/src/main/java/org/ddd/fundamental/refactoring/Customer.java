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

    private double amountFor(Rental rental){
        double thisAmount = 0;
        switch (rental.getMovie().getPriceCode()){
            case Movie.REGULAR:
                thisAmount +=2;
                if (rental.getDaysRented() > 2)
                    thisAmount += (rental.getDaysRented()-2)*1.5;

                break;
            case Movie.NEW_RELEASE:
                thisAmount +=rental.getDaysRented()*3;
                break;
            case Movie.CHILDRENS:
                thisAmount+=1.5;
                if (rental.getDaysRented() >1)
                    thisAmount += (rental.getDaysRented()-3)*1.5;
                break;
        }
        return thisAmount;
    }


    public String statement(){
        double totalAmount = 0;
        int frequentRenterPoints = 0;
        String result = "Rental Record for "+ getName() +"\n";
        for (Rental rental: rentals) {
            double thisAmount = amountFor(rental);
//            switch (rental.getMovie().getPriceCode()){
//                case Movie.REGULAR:
//                    thisAmount +=2;
//                    if (rental.getDaysRented() > 2)
//                        thisAmount += (rental.getDaysRented()-2)*1.5;
//
//                    break;
//                    case Movie.NEW_RELEASE:
//                        thisAmount +=rental.getDaysRented()*3;
//                        break;
//                        case Movie.CHILDRENS:
//                            thisAmount+=1.5;
//                            if (rental.getDaysRented() >1)
//                                thisAmount += (rental.getDaysRented()-3)*1.5;
//                            break;
//            }
            frequentRenterPoints++;
            if ((rental.getMovie().getPriceCode() == Movie.NEW_RELEASE ) && rental.getDaysRented() >1)
                frequentRenterPoints++;
            result+= "\t" + rental.getMovie().getTitle() +"\t" + String.valueOf(thisAmount)+"\n";
            totalAmount+=thisAmount;
        }
        result +="Amount owned is " +String.valueOf(totalAmount)+"\n";
        result += "You earned "+String.valueOf(frequentRenterPoints) + " frequent renter points" ;
        return result;
    }
}
