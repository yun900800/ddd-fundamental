package org.ddd.fundamental.algorithms.basedata.generic.typecapture;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Array;

public class GenericArrayWithTypeTokenTest<T> {



    @Test
    public void testArrayGeneric() {
        GenericArrayWithTypeToken<Integer> genericArrayWithTypeTokenTest
                = new GenericArrayWithTypeToken<>(Integer.class,10);
        Integer[] rep = genericArrayWithTypeTokenTest.rep();
        Assert.assertEquals(10,rep.length,0);
    }
}

class GenericArrayWithTypeToken<T> {
    private T[] array;

    public GenericArrayWithTypeToken(){
    }

    public GenericArrayWithTypeToken(Class<T> clazz,int size) {
        this.array = (T[])Array.newInstance(clazz,size);
    }

    public void put(int index,T data){
        this.array[index] = data;
    }

    public T get(int index) {
        return this.array[index];
    }

    public T[] rep() {
        return array;
    }
}
