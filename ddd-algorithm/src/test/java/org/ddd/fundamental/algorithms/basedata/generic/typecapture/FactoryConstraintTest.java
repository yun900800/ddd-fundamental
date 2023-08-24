package org.ddd.fundamental.algorithms.basedata.generic.typecapture;


import org.junit.Assert;
import org.junit.Test;

public class FactoryConstraintTest {

    @Test
    public void testFactoryConstraint() {
        GenericFactory genericFactoryInteger = new GenericFactory(new IntegerFactory());
        Assert.assertTrue(genericFactoryInteger.getElement() instanceof Integer);
        GenericFactory genericFactoryWidget = new GenericFactory(new WidgetFactory.FactoryImpl());
        Assert.assertTrue(genericFactoryWidget.getElement() instanceof WidgetFactory);

    }
}

interface Factory<T> {
    T create();
}

class GenericFactory<T> {
    T element;

    /**
     * 构造函数包含一个泛型参数F,并且这个泛型参数是Factory<T>的子类
     * @param factory
     * @param <F>
     */
    public <F extends Factory<T>> GenericFactory(F factory) {
        this.element = factory.create();
    }

    public T getElement() {
        return element;
    }
}

class IntegerFactory implements Factory<Integer> {

    @Override
    public Integer create() {
        return Integer.valueOf(0);
    }
}

class WidgetFactory {
    public static class FactoryImpl implements  Factory<WidgetFactory> {

        @Override
        public WidgetFactory create() {
            return new WidgetFactory();
        }
    }
}
