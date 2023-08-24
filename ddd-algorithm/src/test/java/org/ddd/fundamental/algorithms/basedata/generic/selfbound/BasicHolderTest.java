package org.ddd.fundamental.algorithms.basedata.generic.selfbound;

import org.ddd.fundamental.algorithm.basedata.generic.selfbound.SubType;
import org.junit.Test;

public class BasicHolderTest {

    /**
     * 自限定类型这种语法定义了一个基类，这个基类能够使用子类作为其参数、返回类型、作用域。
     * 比如这里的setElement将子类作为参数,而getElement将子类作为返回类型
     * 因此,这里达到了:基类用子类代替其参数
     * 泛型基类变成了一种其所有子类的公共功能模版，但是在所产生的类中将使用确切类型而不是基类型
     * 这里起到了基类作为模板,子类个性化
     */
    @Test
    public void testCRGWithBasicHolder() {
        SubType subType1 = new SubType();
        SubType subType2 = new SubType();
        subType1.setElement(subType2);
        subType1.getElement();
        subType1.printElement();
    }
}
