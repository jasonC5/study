package com.jason.study.algorithm.tree;

/**
 * 判断二叉树是否完全二叉树	 递归套路
 *
 * @author JasonC5
 */
public class CheckCompleteBinaryTree2 {

    static class Info {
        boolean isFull;
        boolean isComplete;
        int height;

        public Info(boolean isFull, boolean isComplete, int height) {
            this.isFull = isFull;
            this.isComplete = isComplete;
            this.height = height;
        }
    }

    public static boolean check(BinaryTreeNode head) {
        return process(head).isComplete;
    }

    private static Info process(BinaryTreeNode node) {
        //判断当前阶段是否完全二叉树
        //1.左右子树都是满二叉树，且高度相等
        //2.左子树是满二叉树，子树是完全二叉树，且左右子树高度相等
        //3.左子树是完全二叉树，柚子树是满二叉树，且左子树比柚子树高1层
        if (node == null) {
            return new Info(true, true, 0);
        }
        Info leftInfo = process(node.left);
        Info rightInfo = process(node.right);
        Integer height = Math.max(leftInfo.height, rightInfo.height) + 1;
        boolean isFull = leftInfo.isFull && rightInfo.isFull && leftInfo.height == rightInfo.height;
        //满二叉树，肯定是完全二叉树，情况1
        boolean isComplete = isFull;
        if (!isComplete) {
            //情况2
            isComplete = leftInfo.isFull && rightInfo.isComplete && leftInfo.height == rightInfo.height;
        }
        if (!isComplete) {
            //情况3
            isComplete = leftInfo.isComplete && rightInfo.isFull && leftInfo.height - rightInfo.height == 1;
        }
        return new Info(isFull, isComplete, height);
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
        root.left.right = new BinaryTreeNode(4);
//        root.right.left = new BinaryTreeNode(5);
        root.right.right = new BinaryTreeNode(6);
//        root.left.left.left = new BinaryTreeNode(7);
        System.out.println(check(root));
    }


}
