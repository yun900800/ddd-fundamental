package org.ddd.fundamental.material;

import org.ddd.fundamental.core.ValueObject;
import org.ddd.fundamental.exception.NotSplitException;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface Computable extends ValueObject {
    @NotNull String getUnit();

    @NotNull double getQty();

    String getQrCode();

    Computable changeQty(double qty);

    default <T extends Computable> List<T> split(T computable,
                                                       SplitStrategy<T> strategy){
        notSplitCheck(computable);
        return strategy.split(computable);
    }

    default <T extends Computable> List<T> split(T computable){
        notSplitCheck(computable);
        SplitStrategy<T> strategy = new DividedTwoStrategyByClass1(computable.getClass(),
                new Class[]{computable.getClass()});
        return strategy.split(computable);
    }

    private static <T extends Computable> void notSplitCheck(T computable){
        if (computable.getQty() == 1) {
            throw new NotSplitException("qty is one,can't split");
        }
    }
}
