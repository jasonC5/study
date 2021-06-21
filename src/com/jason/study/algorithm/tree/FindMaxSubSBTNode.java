package com.jason.study.algorithm.tree;

/**
 * 给出一颗二叉树的头节点head，返回这颗二叉树中最大的二叉搜索子树的头节点
 *
 * @author JasonC5
 */
public class FindMaxSubSBTNode {

    static class Info {
        boolean isSBT;
        int maxSubSBTNodeNum;
        BinaryTreeNode subHead;
        int max;
        int min;

        public Info(boolean isSBT, int maxSubSBTNodeNum, int max, int min, BinaryTreeNode subHead) {
            this.isSBT = isSBT;
            this.maxSubSBTNodeNum = maxSubSBTNodeNum;
            this.max = max;
            this.min = min;
            this.subHead = subHead;
        }

        public static BinaryTreeNode findMaxSubSBTHead(BinaryTreeNode<Integer> head) {
            return process(head).subHead;
        }

        private static Info process(BinaryTreeNode<Integer> node) {
            if (node == null) {
                return null;
            }
            Info leftInfo = process(node.left);
            Info rightInfo = process(node.right);
            //左右子树都是搜索二叉树，且当前节点大于左子树的最大值，小于右子树的最小值
            boolean isSBT = (leftInfo == null || leftInfo.isSBT) && (rightInfo == null || rightInfo.isSBT)
                    && (leftInfo == null || node.value > leftInfo.max) && (rightInfo == null || node.value < rightInfo.min);
            int leftSubNum = leftInfo == null ? 0 : leftInfo.maxSubSBTNodeNum;
            int rightSubNum = rightInfo == null ? 0 : rightInfo.maxSubSBTNodeNum;
            int maxSubSBTNodeNum = isSBT ? leftSubNum + rightSubNum + 1 : Math.max(leftSubNum, rightSubNum);
            BinaryTreeNode subHead = isSBT ? node : (maxSubSBTNodeNum == leftSubNum) ? leftInfo.subHead : rightInfo.subHead;
            //最大最小值
            int max = rightInfo == null ? node.value : rightInfo.max;
            int min = leftInfo == null ? node.value : leftInfo.min;
            return new Info(isSBT, maxSubSBTNodeNum, max, min, subHead);
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
            root.left.left = new BinaryTreeNode(6);
//            root.left.right = new BinaryTreeNode(8);
            root.right.left = new BinaryTreeNode(15);
            root.right.right = new BinaryTreeNode(26);
            System.out.println(findMaxSubSBTHead(root).value);
        }

    }
}
