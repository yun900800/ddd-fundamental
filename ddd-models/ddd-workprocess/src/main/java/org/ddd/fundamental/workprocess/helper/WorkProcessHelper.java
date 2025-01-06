package org.ddd.fundamental.workprocess.helper;

import org.ddd.fundamental.workprocess.enums.BatchManagable;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class WorkProcessHelper {

    public static List<Integer> numberRange(int start, int end){
        return IntStream.range(start,end).boxed().collect(Collectors.toList());
    }

    public static List<Boolean> trueOrFalse(){
        return Arrays.asList(true,false);
    }

    public static List<BatchManagable> batchManagables() {
        return Arrays.asList(BatchManagable.values());
    }

    public static List<Double> doubleList(){
        return Arrays.asList(95.2,99.5,99.6,96.4,98.8,97.6);
    }

}
