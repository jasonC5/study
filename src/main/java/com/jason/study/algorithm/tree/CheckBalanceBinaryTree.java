package com.jason.study.algorithm.tree;

/**
 * 判断是否平衡二叉树 --二叉树递归套路【二叉树dp】
 *
 * @author JasonC5
 */
public class CheckBalanceBinaryTree {
    //平衡二叉树需要的信息：左右子树是否平衡二叉树，左右子树的高度，
    static class Info {
        boolean isBbt;
        int height;

        public Info(boolean isBbt, int height) {
            this.isBbt = isBbt;
            this.height = height;
        }
    }

    public static boolean check(BinaryTreeNode head) {
        return process(head).isBbt;
    }

    private static Info process(BinaryTreeNode node) {
        if (node == null) {
            return new Info(true, 0);
        }
        Info leftInfo = process(node.left);
        Info rightInfo = process(node.right);
        boolean check = leftInfo.isBbt && rightInfo.isBbt && Math.abs(leftInfo.height - rightInfo.height) <= 1;
        int height = Math.max(leftInfo.height, rightInfo.height) + 1;
        return new Info(check, height);
    }

    public static void main(String[] args) {
        /**
         *           0
         *          1 2
         *         34 56
         */
        BinaryTreeNode root = new BinaryTreeNode(0);
        root.left = new BinaryTreeNode(1);
        root.right = new BinaryTreeNode(2);
        root.left.left = new BinaryTreeNode(3);
//        root.left.right = new BinaryTreeNode(4);
        root.right.left = new BinaryTreeNode(5);
        root.right.right = new BinaryTreeNode(6);
        root.left.left.left = new BinaryTreeNode(7);
        System.out.println(check(root));
    }
}
