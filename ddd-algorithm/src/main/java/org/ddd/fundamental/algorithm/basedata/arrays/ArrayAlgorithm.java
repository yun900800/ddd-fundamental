package org.ddd.fundamental.algorithm.basedata.arrays;

import java.util.Random;

public class ArrayAlgorithm {

    private static final int ARRAY_COUNT = 11;

    private static int[] generate() {
        int[] arrays = new int[ARRAY_COUNT];
        for (int i = 0 ; i< ARRAY_COUNT-1;i++) {
            arrays[i] = i+1;
        }
        return arrays;
    }

    private static int randomInt() {
        int ret = new Random().nextInt(ARRAY_COUNT);
        return ret;
    }

    private static void swap(int i, int j,int[] arrays) {
        int temp = arrays[i];
        arrays[i] = arrays[j];
        arrays[j] = temp;
    }

    private static int[] prepareData() {
        int[] data = generate();
        data[ARRAY_COUNT-1] = randomInt();
        System.out.println("这个数字是重复数字:"+ data[ARRAY_COUNT-1]);
        int index = randomInt();
        swap(index,ARRAY_COUNT-1,data);
        return data;
    }

    public static int removeDuplicate() {
        int[] data = prepareData();
        int x = 0;
        for (int i = 1 ; i < data.length; i++) {
            x^=i;
        }
        int y = 0;
        for (int i = 0 ; i< data.length; i++) {
            y^=data[i];
        }
        int ret = x^y;
        System.out.println("duplicate number:"+ret);
        return ret;
    }

    public static void main(String[] args) {
        removeDuplicate();
    }
}
