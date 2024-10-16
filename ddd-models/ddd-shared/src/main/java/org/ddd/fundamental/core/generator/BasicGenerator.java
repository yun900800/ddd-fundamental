package org.ddd.fundamental.core.generator;

public class BasicGenerator<T> implements Generator<T>{
    private Class<T> type;

    public BasicGenerator(Class<T> type){
        this.type = type;
    }
    @Override
    public T next() {
        try {
            // Assumes type is a public class:
            return type.getConstructor().newInstance();
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Class<T> getType() {
        return type;
    }

    public static <T> Generator<T> create(Class<T> type) {
        return new BasicGenerator<T>(type);
    }
}
