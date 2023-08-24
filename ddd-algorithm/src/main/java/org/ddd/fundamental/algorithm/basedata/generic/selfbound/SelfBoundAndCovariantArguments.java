package org.ddd.fundamental.algorithm.basedata.generic.selfbound;

public class SelfBoundAndCovariantArguments {
    void testA(Setter s1, Setter s2, SelfBoundSetter sbs) {
        s1.set(s2);
        //这里子类的方法对应的参数会更具体,子类继承的方法类型可以变化
        //s1.set(sbs);  // 编译错误
    }
}

interface SelfBoundSetter<T extends SelfBoundSetter<T>> {
    void set(T args);
}

interface Setter extends SelfBoundSetter<Setter> {}
