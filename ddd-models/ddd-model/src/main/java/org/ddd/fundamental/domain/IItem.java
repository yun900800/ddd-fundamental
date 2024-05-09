package org.ddd.fundamental.domain;

import org.ddd.fundamental.constants.ItemType;

public interface IItem<K> {

    K key();

    void setKey(K key);

    String name();

    ItemType type();

    double quantity();

    IItem<K> changeQuantity(double quantity);
}
