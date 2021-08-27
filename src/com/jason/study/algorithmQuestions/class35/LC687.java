package com.jason.study.algorithmQuestions.class35;


/**
 * 给定一个二叉树，找到最长的路径，这个路径中的每个节点具有相同值。 这条路径可以经过也可以不经过根节点。
 * <p>
 * 注意：两个节点之间的路径长度由它们之间的边数表示。
 * <p>
 * 示例 1:
 * <p>
 * 输入:
 * <p>
 * 5
 * / \
 * 4   5
 * / \   \
 * 1   1   5
 * 输出:
 * <p>
 * 2
 * 示例 2:
 * <p>
 * 输入:
 * <p>
 * 1
 * / \
 * 4   5
 * / \   \
 * 4   4   5
 * 输出:
 * <p>
 * 2
 * 注意: 给定的二叉树不超过10000个结点。 树的高度不超过1000。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/longest-univalue-path
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author JasonC5
 */
public class LC687 {

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


    class Info {
        int max;
        int height;

        public Info(int max, int height) {
            this.max = max;
            this.height = height;
        }
    }

    public int longestUnivaluePath(TreeNode root) {
        if (root == null) {
            return 0;
        }
        //算出来的是点，-1为线
        return process(root).max - 1;
    }

    public Info process(TreeNode node) {
        if (node == null) {
            return new Info(0, 0);
        }
        TreeNode left = node.left;
        TreeNode right = node.right;
        Info lInfo = process(left);
        Info rInfo = process(right);
//        int leftHeight = (left != null && node.val == left.val) ? lInfo.height : 0;
//        int rightHeight = (right != null && node.val == right.val) ? rInfo.height : 0;
//        int height = Math.max(leftHeight, rightHeight) + 1;
        int height = 1;
        if (left != null && left.val == node.val) {
            height = lInfo.height + 1;
        }
        if (right != null && right.val == node.val) {
            height = Math.max(height, rInfo.height + 1);
        }
        int max = Math.max(height, Math.max(lInfo.max, rInfo.max));
        if (left != null && right != null && left.val == node.val && right.val == node.val) {
            max = Math.max(max, lInfo.height + rInfo.height + 1);
        }
        return new Info(max, height);
    }

}
