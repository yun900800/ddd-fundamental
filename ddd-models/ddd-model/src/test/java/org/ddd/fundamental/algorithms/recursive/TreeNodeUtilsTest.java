package org.ddd.fundamental.algorithms.recursive;

import org.junit.Test;


public class TreeNodeUtilsTest {

    public static TreeNode createTree() {
        TreeNode left1 = new TreeNode(4, null,null);
        TreeNode left2 = new TreeNode(7, null,null);
        TreeNode left = new TreeNode(6, left1,left2);
        TreeNode right1 = new TreeNode(13, null,null);
        TreeNode right2 = new TreeNode(20, null,null);
        TreeNode right = new TreeNode(15, right1,right2);
        TreeNode root = new TreeNode(8,left, right);
        return root;
    }

    @Test
    public void testPreOrderTraversal() {
        TreeNodeUtils.preOrderTraversal(createTree());
    }

    @Test
    public void testPreOrderTailTraversal() {
        TreeNodeUtils.preOrderTailTraversal(createTree(),x->System.out.println(x));
    }

    @Test
    public void testPreOrderTailOptTraversal() {
        TreeNodeUtils.preOrderTailOptTraversal(createTree(),x->System.out.println(x));
    }
}
