package org.ddd.fundamental.domain.order;

import org.ddd.fundamental.constants.ItemType;
import org.ddd.fundamental.domain.IKey;

public interface IItem<K>  extends IKey<K> {

    String name();

    ItemType type();

    double quantity();

    IItem<K> changeQuantity(double quantity);
}
