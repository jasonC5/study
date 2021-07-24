package com.jason.study.algorithm.other;

import com.jason.study.algorithm.tree.BinaryTreeNode;
import com.jason.study.algorithm.tree.CheckSBT;

/**
 * 判断是否是搜索二叉树【使用Morris遍历】
 * --中序遍历打印语句替换成比对语句即可
 * --注意点：不能直接return false需要跑完Morris遍历把树的指针恢复回去
 *
 * @author JasonC5
 */
public class Morris_02 {


    public static boolean check(BinaryTreeNode<Integer> head) {
        if (head == null) {
            return true;
        }
        return process(head).isSBT;
    }

    private static CheckSBT.Info process(BinaryTreeNode<Integer> node) {
        if (node == null) {
            return null;
        }
        CheckSBT.Info leftInfo = process(node.left);
        CheckSBT.Info rightInfo = process(node.right);
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
        return new CheckSBT.Info(isSBT, max, min);
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
        System.out.println(checkByMorris(root));
    }

    private static boolean checkByMorris(BinaryTreeNode<Integer> root) {
        //莫里斯遍历
        BinaryTreeNode<Integer> reader = root;
        BinaryTreeNode<Integer> pre = null;
        while (reader != null) {
            if (reader.left != null) {
                BinaryTreeNode<Integer> rightest = findRightest(reader, reader.left);
                if (rightest.right == null) {
                    rightest.right = reader;
                    //第一次来到节点的时候不更新cur，不和左边的数做比较--【--平衡二叉树中序遍历递增，此时不是打印的时机，所以不是比较的时机，也不是更新pre的时机】
//                    pre = reader;
                    reader = reader.left;
                } else {// if (rightest.right == reader){
                    rightest.right = null;
                    if (pre != null && pre.value > reader.value) {
                        return false;
                    }
                    pre = reader;
                    reader = reader.right;
                }
            } else {
                if (pre != null && pre.value > reader.value) {
                    return false;
                }
                pre = reader;
                reader = reader.right;
            }
        }
        return true;
    }

    private static BinaryTreeNode<Integer> findRightest(BinaryTreeNode node, BinaryTreeNode left) {
        BinaryTreeNode cur = left;
        while (cur.right != null && cur.right != node) {
            cur = cur.right;
        }
        return cur;
    }

}
