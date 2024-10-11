package org.ddd.fundamental.generics.instances;

public class FactoryConstraint {
    public static void main(String[] args) {
        new Foo2<>(new IntegerFactory());
        new Foo2<>(new Widget.Factory());
    }
}

interface FactoryI<T, S> {
    T create(S s);
}

class Foo2<T> {
    private T x;

    public <F extends FactoryI<T, Void>> Foo2(F factory) {
        x = factory.create(null);
    }
}

class IntegerFactory implements FactoryI<Integer, Void> {
    @Override
    public Integer create(Void aVoid) {
        return 0;
    }
}

class Widget {
    public static class Factory implements FactoryI<Widget, Void> {
        @Override
        public Widget create(Void aVoid) {
            return new Widget();
        }
    }
}
