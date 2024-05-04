package org.ddd.fundamental.model;

import org.ddd.fundamental.constants.ItemType;

public interface IItem<K> {

    K key();

    String name();

    ItemType type();
}
