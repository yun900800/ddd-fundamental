package org.ddd.fundamental.algorithm.basedata.generic.selfbound;

public class CovariantReturnTypes {

    public static void f(DerivedGetter derivedGetter) {
//        这里返回了具体的子类型
        Derived derived = derivedGetter.get();
    }
}

class Base {}
class Derived extends Base {}

interface OrdinaryGetter {
    Base get();
}

/**
 * 通过方法重写返回了更具体的子类型
 */
interface DerivedGetter extends OrdinaryGetter {
    Derived get();
}

