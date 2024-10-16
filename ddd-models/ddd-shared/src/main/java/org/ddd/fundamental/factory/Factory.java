package org.ddd.fundamental.factory;

public interface Factory<T,S> {
    T create(S s);
}

interface FactorySourceInteger<T> extends Factory<T,Integer>{

}

class IntegerFactory implements FactorySourceInteger<Integer>{

    @Override
    public Integer create(Integer integer) {
        return Integer.valueOf(integer);
    }
}

class Cat{
    private int legs;
    public Cat(int legs){
        this.legs = legs;
    }
}

class CatFactory implements FactorySourceInteger<Cat>{

    @Override
    public Cat create(Integer integer) {
        return new Cat(integer);
    }
}
