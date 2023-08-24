package org.ddd.fundamental.user.repository.redis.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.HashMap;
import java.util.Map;

@RedisHash
public class CardShopping {

    @Id
    private String userId;

    private Map<String,Integer> items;

    public CardShopping(){
        this.items = new HashMap<>();
    }

    public CardShopping(String userId) {
        this();
        this.userId = userId;
    }

    public CardShopping addItem(String name,Integer count) {
        this.items.put(name,count);
        return this;
    }

    public CardShopping increment(String name, Integer incrementCount){
        Integer oldCount = this.items.get(name);
        if (null == oldCount) {
            this.addItem(name,incrementCount);
        } else {
            this.items.put(name,(oldCount+ incrementCount));
        }
        return this;
    }

    public CardShopping removeItem(String name) {
        this.items.remove(name);
        return this;
    }

    public CardShopping decrement(String name, Integer incrementCount){
        Integer oldCount = this.items.get(name);
        if (oldCount <= incrementCount) {
            this.items.remove(name);
        } else {
            this.items.put(name,(oldCount - incrementCount));
        }
        return this;
    }

}
