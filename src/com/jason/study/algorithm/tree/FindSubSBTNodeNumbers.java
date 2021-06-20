package com.jason.study.algorithm.tree;

/**
 * 二叉树中整体不是搜索二叉树，但是有可能某一个子树是，找出其中最大的一颗子树，返回节点数
 *
 * @author JasonC5
 */
public class FindSubSBTNodeNumbers {
    //需要的信息：
    // 1.左右节点是否是二叉搜索树
    // 2.左右子树中的最大搜索二叉子树的节点数
    // 3.左右子树的最大最小值
    // --如果左右子树有一个不是二叉搜索树，则自己的这颗子树必定不是二叉搜索树，此时只需要取左右子树的最大二叉搜索树节点数的最大值即可

    static class Info {
        boolean isSBT;
        int maxSubSBTNodeNum;
        int max;
        int min;

        public Info(boolean isSBT, int maxSubSBTNodeNum, int max, int min) {
            this.isSBT = isSBT;
            this.maxSubSBTNodeNum = maxSubSBTNodeNum;
            this.max = max;
            this.min = min;
        }
    }

    public static int subSBTNodeNum(BinaryTreeNode<Integer> head) {
        if (head == null) {
            return 0;
        }
        return process(head).maxSubSBTNodeNum;
    }

    private static Info process(BinaryTreeNode<Integer> node) {
        if (node == null) {
            return null;
        }
        Info leftInfo = process(node.left);
        Info rightInfo = process(node.right);
        boolean isSBT;
        int maxSubSBTNodeNum;
        int max = node.value;
        int min = node.value;
        if ((leftInfo == null || leftInfo.isSBT) && (rightInfo == null || rightInfo.isSBT)) {
            isSBT = (leftInfo == null ? true : leftInfo.max < node.value) && (rightInfo == null ? true : rightInfo.min > node.value);
            maxSubSBTNodeNum = isSBT ? ((leftInfo == null ? 0 : leftInfo.maxSubSBTNodeNum) + (rightInfo == null ? 0 : rightInfo.maxSubSBTNodeNum) + 1)
                    : Math.max(leftInfo == null ? 0 : leftInfo.maxSubSBTNodeNum, rightInfo == null ? 0 : rightInfo.maxSubSBTNodeNum);
            //如果本子树是二叉查找树那么下面这个赋值就是正确的，如果不是，那么怎么赋值都无所谓了
            max = rightInfo == null ? max : rightInfo.max;
            min = leftInfo == null ? min : leftInfo.min;
        } else {
            //左右只要有一个不是二叉搜索树，那本子树就不是二叉搜索树，再往上就都不是二叉搜索树，答案基本上就固定了，这里给值就随意一些(max、min直接赋成自己了)
            isSBT = false;
            maxSubSBTNodeNum = Math.max(leftInfo == null ? 0 : leftInfo.maxSubSBTNodeNum, rightInfo == null ? 0 : rightInfo.maxSubSBTNodeNum);
        }
        return new Info(isSBT, maxSubSBTNodeNum, max, min);
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
        root.right.right = new BinaryTreeNode(18);
        System.out.println(subSBTNodeNum(root));
    }

}
