package org.ddd.fundamental.design.stream;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class CollectUtils {

    public static void main(String[] args) {
        Integer sum = IntegerStreamGenerator.getIntegerStream(1,10)
                .filter(item-> item%2 == 0) // 过滤出偶数
                .map(item-> item * item)    // 映射为平方
                .limit(2)                   // 截取前两个
                .reduce(0,(i1,i2)-> i1+i2); // 最终结果累加求和(初始值为0)

        System.out.println(sum); // 20
    }
    /**
     * stream 转换为 List
     * */
    public static <T> Collector<T, List<T>, List<T>> toList(){
        return new Collector<T, List<T>, List<T>>() {
            @Override
            public Supplier<List<T>> supplier() {
                return ArrayList::new;
            }

            @Override
            public BiFunction<List<T>, List<T>, T> accumulator() {
                return (list, item) -> {
                    list.add(item);
                    return list;
                };
            }

            @Override
            public Function<List<T>, List<T>> finisher() {
                return list -> list;
            }
        };
    }

    /**
     * stream 转换为 Set
     * */
    public static <T> Collector<T, Set<T>, Set<T>> toSet(){
        return new Collector<T, Set<T>, Set<T>>() {
            @Override
            public Supplier<Set<T>> supplier() {
                return HashSet::new;
            }

            @Override
            public BiFunction<Set<T>, Set<T>, T> accumulator() {
                return (set, item) -> {
                    set.add(item);
                    return set;
                };
            }

            @Override
            public Function<Set<T>, Set<T>> finisher() {
                return set -> set;
            }
        };
    }
}
