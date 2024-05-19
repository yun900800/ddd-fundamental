package org.ddd.fundamental.design.stream;

@FunctionalInterface
public interface Function<R,T> {

    /**
     * 函数式接口
     * 类似于 y = F(x)
     * */
    R apply(T t);
}
