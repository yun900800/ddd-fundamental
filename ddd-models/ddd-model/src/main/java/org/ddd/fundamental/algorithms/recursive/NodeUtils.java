package org.ddd.fundamental.algorithms.recursive;

import java.util.function.Function;

/**
 * 这个类的作用是理解递归以及尾递归的一些特点(比如什么是APS?什么是CPS?)
 */
public class NodeUtils {

    /**
     * 这个递归的特点是如果要算出最后的值,一定要回到递归的第一个调用,然后相加以后得到最后的值
     * 也就是说编译器需要维持之前的递归调用
     * @param head
     * @return
     */
    public static int getLengthRecursive(Node head) {
        if (null == head) {
            return 0;
        }
        return 1 + getLengthRecursive(head.getNext());
    }

    /**
     * 这里的递归和上面最大的不同是要得到最后的值，不需要维护之前的递归调用,
     * 直接从最后一个调用就可以拿到值;
     * 除了以上特点外,还有一个就是递归调用在函数的最后,并且没有额外的计算
     * 有以上特点的就是尾递归
     * @param head
     * @param acc
     * @return
     */
    public static int getLengthRailRecursive(Node head, int acc) {
        if (null == head) {
            return acc;
        }
        return getLengthRailRecursive(head.getNext(), acc+1);
    }

    /**
     * 这个就不是尾递归啦,因为最后的调用涉及到了两次函数调用
     * @param n
     * @return
     */
    public static int fibonacciRecursively(int n) {
        if (n < 2) return 1;
        return fibonacciRecursively(n-1) + fibonacciRecursively(n-2);
    }

    /**
     * 可以用过改造成一个累积器来改造成尾递归
     * 其实这个模式就是APS
     *
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

    /**
     * 另外一种思考方式
     * @param n
     * @return
     */
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
        return factorialContinuation(n-1, r->{
            System.out.println("r:"+r + " n:"+ n);
            return continuation.apply(n*r);
        });
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
