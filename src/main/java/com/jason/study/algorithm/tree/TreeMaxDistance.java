package com.jason.study.algorithm.tree;

/**
 * 给定一个二叉树的口节点head，任何两个节点都有距离，返回整棵树的最大距离
 *
 * @author JasonC5
 */
public class TreeMaxDistance {

    //两种情况：
    // 1.最大距离不过本节点，则为左右子树的最大距离中较大的那个数字
    // 2.最大距离过本节点，=左子树高低+1+右子树高度

    //需要提供的信息：
    //左右子树内部的最大距离，左右子树的高度

    static class Info {
        int distance;
        int height;

        public Info(int distance, int height) {
            this.distance = distance;
            this.height = height;
        }
    }

    static int treeDistance(BinaryTreeNode head) {
        if (head == null) {
            return 0;
        }
        return process(head).distance;

    }

    private static Info process(BinaryTreeNode node) {
        if (node == null) {
            return new Info(0, 0);
        }
        Info leftInfo = process(node.left);
        Info rightInfo = process(node.right);
        int height = Math.max(leftInfo.distance, rightInfo.distance) + 1;
        int distance = Math.max(Math.max(leftInfo.distance, rightInfo.distance), leftInfo.height + rightInfo.height + 1);
        return new Info(distance,height);
    }

    public static void main(String[] args) {
        /**
         *           0
         *          1 2
         *         34 56
         */
        BinaryTreeNode root = new BinaryTreeNode(0);
        root.left = new BinaryTreeNode(1);
//        root.right = new BinaryTreeNode(2);
        root.left.left = new BinaryTreeNode(3);
        root.left.right = new BinaryTreeNode(4);
//        root.right.left = new BinaryTreeNode(5);
//        root.right.right = new BinaryTreeNode(6);
        root.left.left.left = new BinaryTreeNode(7);
        root.left.left.left.left = new BinaryTreeNode(8);
        root.left.right.left = new BinaryTreeNode(9);
        System.out.println(treeDistance(root));
    }
}
