package org.ddd.fundamental.workorder.domain.value.impl;

import org.ddd.fundamental.tuple.Tuple;
import org.ddd.fundamental.tuple.TwoTuple;
import org.ddd.fundamental.workorder.domain.value.ProductOrderSplitStrategy;

import java.util.ArrayList;
import java.util.List;

public class AverageSplitStrategy implements ProductOrderSplitStrategy {

    private double workOrderCount;

    public AverageSplitStrategy(double workOrderCount){
        this.workOrderCount = workOrderCount;
    }

    @Override
    public TwoTuple<Integer, List<Double>> handle(double productCount) {
        double rest = productCount % workOrderCount;
        int count = (int)(productCount / workOrderCount);
        List<Double> result = new ArrayList<>();
        if (rest == 0) {
            for (int i = 0 ; i< count; i++) {
                result.add(workOrderCount);
            }
        } else {
            for (int i = 0 ; i< count-1; i++) {
                result.add(workOrderCount);
            }
            result.add(workOrderCount+rest);
        }

        return Tuple.tuple(count,result);
    }
}
