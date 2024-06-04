package org.ddd.fundamental.algorithms.recursive;

import java.util.function.Consumer;

public class TreeNodeUtils {

    public static void preOrderTraversal(TreeNode root) {
        if (null == root) {
            return;
        }
        System.out.println(root.getVal());
        preOrderTraversal(root.getLeft());
        preOrderTraversal(root.getRight());
    }

//    我们现在把每次递归调用都作为代码的最后一次操作，
//    把接下来的操作使用Continuation包装起来，这样就实现了尾递归，
//    避免了堆栈数据的堆积。
//    可见，虽然使用Continuation是一个略有些“诡异”的使用方式，但是在某些时候它也是必不可少的使用技巧。
    public static void preOrderTailTraversal(TreeNode root, Consumer<TreeNode> consumer){
        if (null == root) {
            consumer.accept(root);
            return;
        }
        System.out.println(root.getVal());
        preOrderTailTraversal(root.getLeft(), left -> preOrderTailTraversal(root.getRight(), right->{
            consumer.accept(right);
        }));
    }
}
