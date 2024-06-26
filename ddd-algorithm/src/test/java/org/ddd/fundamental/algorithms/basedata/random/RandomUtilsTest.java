package org.ddd.fundamental.algorithms.basedata.random;

import org.ddd.fundamental.algorithm.basedata.random.Procedure;
import org.ddd.fundamental.algorithm.basedata.random.RandomUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.Set;

public class RandomUtilsTest {

    public static final int NUMBER_COUNT = 100000;

    @Test
    public void testGenerateRandom(){
        Set<Integer> integerSet = RandomUtils.generateRandom(NUMBER_COUNT);
        Assert.assertEquals(integerSet.size(),NUMBER_COUNT);
    }

    @Test
    public void testFnExecCostTime(){
        Procedure fn = () ->RandomUtils.generateRandom(NUMBER_COUNT);
        RandomUtils.fnExecCostTime(fn);
    }

    @Test
    public void testSetToArrayInt(){
        Set<Integer> integerSet = RandomUtils.generateRandom(NUMBER_COUNT);
        Procedure fn = () ->{
            int[] data = RandomUtils.setToArrayInt(NUMBER_COUNT,integerSet);
            Assert.assertEquals(data.length,NUMBER_COUNT);
        };
        RandomUtils.fnExecCostTime(fn);

    }
}
