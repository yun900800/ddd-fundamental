package org.ddd.fundamental.domain.inventory;

import org.ddd.fundamental.domain.IKey;

public interface ILocation<K> extends IKey<K> {

    /**
     * 具体位置编号
     * @return
     */
    String number();

}
