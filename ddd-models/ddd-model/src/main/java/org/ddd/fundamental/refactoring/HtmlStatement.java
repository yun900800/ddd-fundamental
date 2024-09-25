package org.ddd.fundamental.refactoring;

public class HtmlStatement extends Statement{

    String headerString(Customer customer){
        return "<H1>Rental Record for <EM>"+ customer.getName() +"</EM></H1><P>\n";
    }
    String eachRentalString(Rental rental){
        return rental.getMovie().getTitle() +":" + String.valueOf(rental.getCharge())+"<BR>\n";
    }

    String footerString(Customer customer){
        return "<P>Amount owned<EM> is " +String.valueOf(customer.getTotalCharge())+"</EM></P>\n" +
                "You earned <EM>"+String.valueOf(customer.getTotalFrequentRenterPoints()) + "</EM> frequent renter points</P>" ;
    }
}
