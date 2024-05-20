package org.ddd.fundamental.design.stream;

public interface Collector<T, A, R> {

    /**
     * 收集时，提供初始化的值
     * */
    Supplier<A> supplier();

    /**
     * A = A + T
     * 累加器，收集时的累加过程
     * */
    BiFunction<A, A, T> accumulator();

    /**
     * 收集完成之后的收尾操作
     * */
    Function<R, A> finisher();
}
