package org.ddd.fundamental.domain.inventory;

import org.ddd.fundamental.domain.IKey;

public interface IDistributionLocation<K> extends IKey<K> {

    K inventoryKey();

    K locationKey();

    double quantity();
}
