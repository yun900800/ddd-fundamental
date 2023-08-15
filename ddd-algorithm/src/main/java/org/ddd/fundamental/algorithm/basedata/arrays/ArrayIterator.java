package org.ddd.fundamental.algorithm.basedata.arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
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
}
