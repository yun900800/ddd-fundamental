package org.ddd.fundamental.algorithm.basedata.arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 数组迭代类
 */
public class ArrayIterator {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArrayIterator.class);

    private List<String> source;

    public ArrayIterator(List<String> source) {
        this.source = source;
    }

    public String forIterator() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        String actual = source.stream().collect(Collectors.joining("->"));
        sb.append(actual);
        sb.append("]");
        return sb.toString();
    }

    /**
     * 前序递归
     */
    private void recursivePreAndCollect(int index, List<String> collectors) {
        if (index == this.source.size()) {
            return;
        }
        String result = this.source.get(index);
        collectors.add(result);
        this.recursivePreAndCollect(index+1,collectors);
    }

    private void recursivePostAndCollect(int index, List<String> collectors) {
        if (index == this.source.size()) {
            return;
        }
        this.recursivePostAndCollect(index+1,collectors);
        String result = this.source.get(index);
        collectors.add(result);
    }

    public List<String> recursivePost() {
        List<String> collectors = new ArrayList<>();
        this.recursivePostAndCollect(0,collectors);
        return collectors;
    }

    public List<String> recursivePre() {
        List<String> collectors = new ArrayList<>();
        this.recursivePreAndCollect(0,collectors);
        return collectors;
    }

    public static List<String> generate(int count) {
        List<String> results = new ArrayList<>();
        for (int i = 0 ; i< count;i++) {
            String geStr = UUID.randomUUID().toString();
            results.add(geStr);
        }
        return results;
    }

    public static void searchSumK(int[] arrays, int k) {
        int[] sortArrays = fastSearch(arrays,0, arrays.length-1);
        searchSum(sortArrays,k);
    }

    public static void searchSum(int[] arrays,int k) {
        int left = 0 ;
        int right = arrays.length-1;
        while (left < right) {
            if (arrays[left] + arrays[right] > k) {
                right--;
            } else if (arrays[left] + arrays[right] < k) {
                left++;
            } else if (arrays[left] + arrays[right] == k) {
                System.out.println("number1:" + arrays[left] +" number2:"+ arrays[right]  );
                return;
            }
        }
        System.out.println("not found");
    }

    public static int[] fastSearch(int[] arrays, int begin, int end) {
        if (begin<end) {
            int index = partitionFromBegin(arrays,begin,end);
            fastSearch(arrays, begin, index-1);
            fastSearch(arrays, index+1, end);
        }

        return arrays;
    }

    public static int partition(int[] arrays, int begin, int end) {
        int pivot = arrays[end];
        int i = begin-1;
        for (int k = begin; k < end; k++) {
            //先移动指针，然后让指针出元素与第k个比较的元素交换
            if (arrays[k] < pivot) {
                i++;
                int temp = arrays[i];
                arrays[i] = arrays[k];
                arrays[k] = temp;
            }
        }
        //将第i+1个元素与选择元素进行交换
        int temp = arrays[i+1];
        arrays[i+1] = arrays[end];
        arrays[end] = temp;
        return i+1;
    }

    /**
     * 从开始选择元素，存在一个备选元素被替换的的可能,因此需要记录替换后备选元素的索引，最后和i+1替换
     * 选择最后一个元素就没有备选元素被替换的可能，因此最简单
     *
     * @param arrays
     * @param begin
     * @param end
     * @return
     */
    public static int partitionFromBegin(int[] arrays, int begin, int end) {
        int pivot = arrays[begin];
        int i = begin-1;
        int pivotIndex = begin;
        for (int k = begin+1; k <= end; k++) {
            if (arrays[k] < pivot) {
                i++;
                int temp = arrays[i];
                arrays[i] = arrays[k];
                arrays[k] = temp;
                if (pivotIndex == i) {//刚好是替换的pivot,记录下pivot替换后的索引
                    pivotIndex = k;
                }
            }
        }
        int temp = arrays[i+1];
        arrays[i+1] = arrays[pivotIndex];
        arrays[pivotIndex] = temp;
        return i+1;
    }

    public static void main(String[] args) {
        int[] arrays = new int[]{9,8,5,1,2,15,4};
        searchSumK(arrays,17);
        int[] arrays1 = new int[]{8,9,5,1,2,15,4};
        System.out.println(partitionFromBegin(arrays1,0,6));
        System.out.println(Arrays.toString(arrays1));

        int[] arrays2 = new int[]{16,9,5,1,2,15,4};
        System.out.println(partitionFromBegin(arrays2,0,6));
        System.out.println(Arrays.toString(arrays2));
    }
}
