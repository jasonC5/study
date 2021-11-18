package com.jason.study.algorithm.tree;

/**
 * 判断是否满二叉树
 *
 * @author JasonC5
 */
public class CheckFullBT {
    //需要信息：左右子节点是否满二叉树，左右子节点的高度

    static class Info {
        boolean isFullBT;
        int height;

        public Info(boolean isFullBT, int height) {
            this.isFullBT = isFullBT;
            this.height = height;
        }
    }

    public static boolean check(BinaryTreeNode head) {
        return process(head).isFullBT;
    }

    private static Info process(BinaryTreeNode node) {
        if (node == null) {
            return new Info(true, 0);
        }
        Info leftInfo = process(node.left);
        Info rightInfo = process(node.right);
        int height = Math.max(leftInfo.height, rightInfo.height) + 1;
        boolean isFullBT = leftInfo.isFullBT && rightInfo.isFullBT && leftInfo.height == rightInfo.height;
        return new Info(isFullBT, height);
    }

    public static void main(String[] args) {
        BinaryTreeNode root = new BinaryTreeNode(0);
        root.left = new BinaryTreeNode(1);
        root.right = new BinaryTreeNode(2);
        root.left.left = new BinaryTreeNode(3);
        root.left.right = new BinaryTreeNode(4);
        root.right.left = new BinaryTreeNode(5);
//        root.right.right = new BinaryTreeNode(6);
        System.out.println(check(root));
    }

}
