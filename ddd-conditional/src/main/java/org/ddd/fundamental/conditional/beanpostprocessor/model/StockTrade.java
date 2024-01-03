package org.ddd.fundamental.conditional.beanpostprocessor.model;

import java.util.Date;

public class StockTrade {
    private String symbol;
    private int quantity;
    private double price;

    public StockTrade(){

    }
    public StockTrade(String symbol, int quantity, double price, Date tradeDate) {
        this.symbol = symbol;
        this.quantity = quantity;
        this.price = price;
        this.tradeDate = tradeDate;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(Date tradeDate) {
        this.tradeDate = tradeDate;
    }

    private Date tradeDate;
}
