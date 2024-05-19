package org.ddd.fundamental.design.stream;

@FunctionalInterface
public interface Supplier<T> {

    /**
     * 提供初始值
     * @return 初始化的值
     * */
    T get();
}
