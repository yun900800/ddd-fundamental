package org.ddd.fundamental.algorithm.basedata.random;


import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * 随机数生成工具类,一些算法需要大数据进行测试,因此需要这个工具类
 *
 */
public final class RandomUtils {

    public static Set<Integer> generateRandom(int n) {
        Set<Integer> resultSet = new HashSet<>();
        Random random = new Random();
        int range = 10 * n;
        while (resultSet.size() < n){
            Integer temp = random.nextInt(range);
            resultSet.add(temp);
        }
        return resultSet;
    }

    public static void fnExecCostTime(Procedure procedure){
        long start = System.nanoTime();
        procedure.run();
        long end = System.nanoTime();
        System.out.println((end-start)/(1000*1000)+"ms");
    }

    public static int[] setToArrayInt(int n,Set<Integer> sets){
        int[] arrays = new int[n];
        int index = 0;
        for(Integer temp: sets){
            arrays[index++] = temp;
        }
        return arrays;
    }

}


