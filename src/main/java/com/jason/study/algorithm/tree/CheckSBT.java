package com.jason.study.algorithm.tree;

/**
 * 判断一个二叉树是搜索二叉树（左树都比头小，右树都比头大）
 * 技巧：二叉树递归套路 二叉树dp
 *
 * @author JasonC5
 */
public class CheckSBT {
    //需要左右子树提供的信息：1.所有子树是否搜索二叉树，2.左子树的最小值，3.右子树的最大值
    public static class Info {
        public boolean isSBT;
        public int max;
        public int min;

        public Info(boolean isSBT, int max, int min) {
            this.isSBT = isSBT;
            this.max = max;
            this.min = min;
        }
    }

    public static boolean check(BinaryTreeNode<Integer> head) {
        if (head == null) {
            return true;
        }
        return process(head).isSBT;
    }

    private static Info process(BinaryTreeNode<Integer> node) {
        if (node == null) {
            return null;
        }
        Info leftInfo = process(node.left);
        Info rightInfo = process(node.right);
        boolean isSBT;
        int max = node.value;
        int min = node.value;
        if (leftInfo != null) {
            max = Math.max(leftInfo.max, max);
            min = Math.min(leftInfo.min, min);
        }
        if (rightInfo != null) {
            max = Math.max(rightInfo.max, max);
            min = Math.min(rightInfo.min, min);
        }
        isSBT = (leftInfo == null ? true : (leftInfo.isSBT && leftInfo.max < node.value))
                && (rightInfo == null ? true : rightInfo.isSBT && rightInfo.min > node.value);
        return new Info(isSBT, max, min);
    }

    public static void main(String[] args) {
        /**
         *           0                    10
         *          1 2                5    20
         *         34 56            3   8  15   26
         */
        BinaryTreeNode root = new BinaryTreeNode(10);
        root.left = new BinaryTreeNode(5);
        root.right = new BinaryTreeNode(20);
        root.left.left = new BinaryTreeNode(3);
        root.left.right = new BinaryTreeNode(8);
        root.right.left = new BinaryTreeNode(15);
        root.right.right = new BinaryTreeNode(26);
        System.out.println(check(root));
    }


}
