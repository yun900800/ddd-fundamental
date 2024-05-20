package org.ddd.fundamental.design.stream;

@FunctionalInterface
public interface EvalFunction<T> {

    /**
     * 其实这里是为了满足闭包性质
     *
     * stream流的强制求值方法
     * @return 求值返回一个新的stream
     *
     */
    MyStream<T> apply();
}
