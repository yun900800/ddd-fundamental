package org.ddd.fundamental.material;

import org.ddd.fundamental.core.generator.Generators;


import java.util.ArrayList;
import java.util.List;

public interface SplitStrategy<T> {
    List<T> split(T object);
}


class DividedTwoStrategyByClass1<T extends Computable> implements SplitStrategy<T>{

    private Class<T> type;

    private Class<T>[] parameter;

    public DividedTwoStrategyByClass1(Class<T> type,Class<T>[] parameter){
        this.type = type;
        this.parameter = parameter;
    }


    @Override
    public List<T> split(T object) {
        List<T> result = new ArrayList<>();
        int n = 2;
        Generators.fill(result, () -> {
            T data = null;
            try {
                data = (T)type.getConstructor(parameter).newInstance(object
                );
                data.changeQty(data.getQty() / n);
            } catch (Exception e){
                e.printStackTrace();
            }
            return data;
        },n);

        return result;
    }
}
