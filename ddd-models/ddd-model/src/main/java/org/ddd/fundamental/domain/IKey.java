package org.ddd.fundamental.domain;

import java.io.Serializable;

public interface IKey<K> extends Serializable {

    K key();

    void setKey(K key);
}
