package org.ddd.fundamental.material;

import org.ddd.fundamental.core.generator.Generator;
import org.ddd.fundamental.core.generator.Generators;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

public interface SplitStrategy<T> {
    List<T> split(T object);
}

class DividedTwoStrategy<T extends ComputableObject<T>> implements SplitStrategy<T>{

    @Override
    public List<T> split(T object) {
        List<T> result = new ArrayList<>();
        int n = 2;
        Generators.fill(result, () -> (T) new ComputableObject<T>(object.getUnit(),
                object.getQty()/n,QRCode.randomId(QRCode.class).toUUID()),n);
        return result;
    }
}

class DividedTwoStrategyByClass<T extends ComputableObject<T>> implements SplitStrategy<T>{

    private Class<?> type;

    public DividedTwoStrategyByClass(Class<?> type){
        this.type = type;
    }


    @Override
    public List<T> split(T object) {
        List<T> result = new ArrayList<>();
        int n = 2;
        try{
//            T data = (T)type.getConstructor(String.class,double.class).newInstance(
//                    object.getUnit(), object.getQty()/ n
//            );
            Generators.fill(result, () -> {
                T data = null;
                try {
                    data = (T)type.getConstructor(String.class,double.class).newInstance(
                            object.getUnit(), object.getQty()/ n
                    );
                } catch (Exception e){
                    e.printStackTrace();
                }
                return data;
            },n);
        } catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }
}
