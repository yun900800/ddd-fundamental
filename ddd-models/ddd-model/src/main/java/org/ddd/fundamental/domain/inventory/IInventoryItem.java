package org.ddd.fundamental.domain.inventory;

import org.ddd.fundamental.domain.order.IItem;

public interface IInventoryItem<K> extends IItem<K> {

    String materialNumber();

    K storeHouseKey();

}
