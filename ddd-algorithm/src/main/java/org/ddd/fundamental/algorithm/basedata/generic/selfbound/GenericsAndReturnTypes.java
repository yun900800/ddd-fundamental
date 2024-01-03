package org.ddd.fundamental.algorithm.basedata.generic.selfbound;

public class GenericsAndReturnTypes {
    void test(Getter g) {
        /**
         * 自限定的类型能够在模板中返回更具体的类型
         */
        Getter result = g.get();
        GenericsGetter genericsGetter = g.get();
    }
}

/**
 * 这里是自限定的类型
 * @param <T>
 */
interface GenericsGetter<T extends GenericsGetter<T>> {
    T get();
}

interface Getter extends GenericsGetter<Getter> {}
