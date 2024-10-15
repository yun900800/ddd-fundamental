package org.ddd.fundamental.generics;

import org.ddd.fundamental.generics.example.CountedObject;
import org.ddd.fundamental.generics.interfaces.Generator;

public class BasicGenerator<T> implements Generator<T> {

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

    public static <T> Generator<T> create(Class<T> type) {
        return new BasicGenerator<T>(type);
    }

    public static void main(String[] args) {
        Generator<CountedObject> gen =
                BasicGenerator.create(CountedObject.class);
        for(int i = 0; i < 5; i++)
            System.out.println(gen.next());

        Generator<CountedObject> gen1 = CountedObject::new;
        for (int i = 0; i < 10; i++) {
            System.out.println(gen1.next());
        }
    }
}
