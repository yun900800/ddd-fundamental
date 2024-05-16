package org.ddd.fundamental.domain.inventory;

import org.ddd.fundamental.domain.IKey;

public interface IInventory<K> extends IKey<K> {

    K itemKey();

    double quantity();
}
