package org.ddd.fundamental.workorder.domain.value;

import org.ddd.fundamental.tuple.TwoTuple;
import java.util.List;

public interface ProductOrderSplitStrategy {

    TwoTuple<Integer, List<Double>> handle(double productCount);

}
