package org.ddd.fundamental.conditional.beanpostprocessor.listener;

import org.ddd.fundamental.conditional.beanpostprocessor.model.StockTrade;

@FunctionalInterface
public interface StockTradeListener {
    void stockTradePublished(StockTrade trade);
}
