package org.ddd.fundamental.conditional.beanpostprocessor.listener;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;
import org.ddd.fundamental.conditional.beanpostprocessor.annotation.Subscriber;
import org.ddd.fundamental.conditional.beanpostprocessor.model.StockTrade;

import java.util.HashSet;
import java.util.Set;

@Subscriber
public class StockTradePublisher {
    Set<StockTradeListener> stockTradeListeners = new HashSet<>();

    public void addStockTradeListener(StockTradeListener listener) {
        synchronized (this.stockTradeListeners) {
            this.stockTradeListeners.add(listener);
        }
    }

    public void removeStockTradeListener(StockTradeListener listener) {
        synchronized (this.stockTradeListeners) {
            this.stockTradeListeners.remove(listener);
        }
    }

    @Subscribe
    @AllowConcurrentEvents
    void handleNewStockTradeEvent(StockTrade trade) {
        // publish to DB, send to PubNub, ...
        Set<StockTradeListener> listeners;
        synchronized (this.stockTradeListeners) {
            listeners = new HashSet<>(this.stockTradeListeners);
        }
        listeners.forEach(li -> li.stockTradePublished(trade));
    }
}
