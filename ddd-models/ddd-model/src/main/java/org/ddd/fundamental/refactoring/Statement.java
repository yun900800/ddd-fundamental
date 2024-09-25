package org.ddd.fundamental.refactoring;

import java.util.List;

public abstract class Statement {
    public String value(Customer customer){
        List<Rental> rentals = customer.getRentals();
        String result = headerString(customer);
        for (Rental rental: rentals) {
            result += eachRentalString(rental);

        }
        result += footerString(customer);
        return result;
    }
    abstract String headerString(Customer customer);
    abstract String eachRentalString(Rental rental);
    abstract String footerString(Customer customer);
}
