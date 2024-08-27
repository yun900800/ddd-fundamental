package org.ddd.fundamental.refactoring;

import org.junit.Test;

public class CustomerTest {

    @Test
    public void testStatement(){
        Rental rental0 = new Rental(new Movie("harlipoter", Movie.NEW_RELEASE),5);
        Rental rental1 = new Rental(new Movie("harlipoter", Movie.CHILDRENS),3);
        Rental rental2 = new Rental(new Movie("harlipoter", Movie.REGULAR),2);
        Customer customer = new Customer("hekai");
        customer.addRental(rental0).addRental(rental1).addRental(rental2);
        System.out.println(customer.statement());
    }

}
