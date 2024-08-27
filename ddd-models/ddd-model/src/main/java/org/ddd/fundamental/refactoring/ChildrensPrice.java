package org.ddd.fundamental.refactoring;

public class ChildrensPrice extends Price{
    @Override
    int getPriceCode() {
        return Movie.CHILDRENS;
    }

    public double getCharge(int daysRented){
        double result = 1.5;
        if (daysRented >1)
            result += (daysRented-3)*1.5;
        return result;
    }
}
