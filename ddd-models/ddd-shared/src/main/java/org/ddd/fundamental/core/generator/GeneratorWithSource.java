package org.ddd.fundamental.core.generator;

public interface GeneratorWithSource<T> {
    T next(Object... source);
}
