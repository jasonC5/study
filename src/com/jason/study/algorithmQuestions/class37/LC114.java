package com.jason.study.algorithmQuestions.class37;

/**
 * 给你二叉树的根结点 root ，请你将它展开为一个单链表：
 * <p>
 * 展开后的单链表应该同样使用 TreeNode ，其中 right 子指针指向链表中下一个结点，而左子指针始终为 null 。
 * 展开后的单链表应该与二叉树 先序遍历 顺序相同。
 *  
 * <p>
 * 示例 1：
 * <p>
 * <p>
 * 输入：root = [1,2,5,3,4,null,6]
 * 输出：[1,null,2,null,3,null,4,null,5,null,6]
 * 示例 2：
 * <p>
 * 输入：root = []
 * 输出：[]
 * 示例 3：
 * <p>
 * 输入：root = [0]
 * 输出：[0]
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/flatten-binary-tree-to-linked-list
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author JasonC5
 */
public class LC114 {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    public class Info {
        TreeNode head;
        TreeNode tail;

        public Info(TreeNode head, TreeNode tail) {
            this.head = head;
            this.tail = tail;
        }
    }

    public void flatten(TreeNode root) {
        process(root);
    }

    private Info process(TreeNode node) {
        if (node == null) {
            return null;
        }
        TreeNode left = node.left;
        TreeNode right = node.right;
        node.left = null;
        Info leftInfo = process(left);
        Info rightInfo = process(right);
        TreeNode curTail = node;
        if (leftInfo != null){
            curTail.right = leftInfo.head;
            curTail = leftInfo.tail;
        }
        if (rightInfo != null){
            curTail.right = rightInfo.head;
            curTail = rightInfo.tail;
        }
        return new Info(node, curTail);
    }

}
