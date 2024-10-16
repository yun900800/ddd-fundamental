package org.ddd.fundamental.core.generator;

import java.lang.reflect.Constructor;

public class BasicGeneratorWithSource<T> extends BasicGenerator<T> implements GeneratorWithSource<T> {



    private Class<?>[] parameters;

    public BasicGeneratorWithSource(Class<T> type,Class<?>[] parameters){
        super(type);
        this.parameters = parameters;
    }

    @Override
    public T next(Object... source) {
        try {
            // Assumes type is a public class:
            Constructor<T> constructor = super.getType().getDeclaredConstructor(parameters);
            return constructor.newInstance(source);
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
}
