package org.ddd.fundamental.domain.inventory;

import org.ddd.fundamental.constants.ItemType;
import org.ddd.fundamental.domain.IKey;

public interface IStoreHouse<K> extends IKey<K> {

    /**
     * 仓库类型
     * @return
     */
    ItemType storeType();

    /**
     * 仓库名字
     * @return
     */
    String name();

}
