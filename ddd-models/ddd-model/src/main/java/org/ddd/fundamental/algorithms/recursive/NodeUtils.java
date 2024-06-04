package org.ddd.fundamental.algorithms.recursive;

import java.util.function.Function;

public class NodeUtils {

    public static int getLengthRecursive(Node head) {
        if (null == head) {
            return 0;
        }
        return 1 + getLengthRecursive(head.getNext());
    }

    public static int getLengthRailRecursive(Node head, int acc) {
        if (null == head) {
            return acc;
        }
        return getLengthRailRecursive(head.getNext(), acc+1);
    }

    public static int fibonacciRecursively(int n) {
        if (n < 2) return 1;
        return fibonacciRecursively(n-1) + fibonacciRecursively(n-2);
    }

    /**
     * 这里是否有这样的规律呢？有多少个递归调用，就需要多少个累加器来搜集值
     * @param n
     * @param acc1
     * @param acc2
     * @return
     */
    public static int fibonacciTailRecursively(int n, int acc1, int acc2) {

        if (n == 0) {
            return acc2;
        }
        return fibonacciTailRecursively(n-1, acc2, acc1+acc2);
    }

    public static int factorialRecursively(int n ) {
        if ( n== 0) {
            return 1;
        }
        //这里显然不是最后的操作
        return factorialRecursively(n - 1) * n;
    }

    public static int factorialTailRecursively(int n) {
//        这里改造成最后一步操作
        return factorialContinuation(n-1, r->n*r);
    }

    //FactorialContinuation方法的实现可以这样表述：“计算n的阶乘，并将结果传入continuation方法并返回”，
    // 也就是“计算n - 1的阶乘，并将结果与n相乘，再调用continuation方法”。
    // 为了实现“并将结果与n相乘，再调用continuation方法”这个逻辑，
    // 代码又构造了一个匿名方法，再次传入FactorialContinuation方法。
    // 当然，我们还需要为它补充递归的出口条件：
    public static int factorialContinuation(int n, Function<Integer, Integer> continuation) {
        if (n == 0) {
            return continuation.apply(1);
        }
        return factorialContinuation(n-1, r-> continuation.apply(n*r));
    }

    public static int fibonacciContinuation(int n, Function<Integer, Integer> continuation) {
        if(n <= 2) {
            return continuation.apply(n);
        }
        return fibonacciContinuation(
                n-1,
                    r1->fibonacciContinuation(
                            n-2,
                            r2->continuation.apply(r1+r2)
                    )
                );
    }

}
