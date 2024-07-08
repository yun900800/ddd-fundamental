package org.ddd.fundamental.domain.revenue;

import java.util.Date;

public class RevenueRecognition {

    private double amount;

    private Date date;

    public RevenueRecognition(double amount,Date date){
        this.amount = amount;
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public boolean isRecognizableBy(Date asOf){
        return asOf.after(date) || asOf.equals(date);
    }
}
