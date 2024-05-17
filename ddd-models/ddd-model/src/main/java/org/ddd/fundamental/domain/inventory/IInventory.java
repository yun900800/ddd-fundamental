package org.ddd.fundamental.domain.inventory;

import org.ddd.fundamental.domain.IKey;

import java.util.Set;

public interface IInventory<K,U> extends IKey<K> {
    Set<IInventoryItem<U>> inventoryItems();
    K itemKey();
    double quantity();
}
