package com.jason.study.algorithmQuestions.class05;

/**
 * 返回与给定前序遍历preorder 相匹配的二叉搜索树（binary search tree）的根结点。
 * <p>
 * (回想一下，二叉搜索树是二叉树的一种，其每个节点都满足以下规则，对于node.left的任何后代，值总 < node.val，而 node.right 的任何后代，值总 > node.val。此外，前序遍历首先显示节点node 的值，然后遍历 node.left，接着遍历 node.right。）
 * <p>
 * 题目保证，对于给定的测试用例，总能找到满足要求的二叉搜索树。
 * <p>
 * <p>
 * <p>
 * 示例：
 * <p>
 * 输入：[8,5,1,7,10,12]
 * 输出：[8,5,10,1,7,null,12]
 * <p>
 * <p>
 * <p>
 * 提示：
 * <p>
 * 1 <= preorder.length <= 100
 * 1 <= preorder[i]<= 10^8
 * preorder 中的值互不相同
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/construct-binary-search-tree-from-preorder-traversal
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author JasonC5
 */
public class LC1008 {
    public static class TreeNode {
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

    public static TreeNode bstFromPreorder(int[] preorder) {
        return process(preorder, 0, preorder.length - 1);
    }

    private static TreeNode process(int[] preorder, int left, int right) {
        if (left > right) {
            return null;
        }
        int rightStart = left + 1;
        while (rightStart <= right && preorder[rightStart] <= preorder[left]) {
            rightStart++;
        }
        TreeNode head = new TreeNode(preorder[left]);
        head.left = process(preorder, left + 1, rightStart - 1);
        head.right = process(preorder, rightStart, right);
        return head;
    }

    public static void main(String[] args) {
        int[] arr = new int[]{4, 2};
        TreeNode treeNode = bstFromPreorder(arr);
        System.out.println(treeNode);
    }

    /**
     * 单调栈
     * @param preorder
     * @return
     */
    public static TreeNode bstFromPreorder2(int[] preorder) {
        return process(preorder, 0, preorder.length - 1);
    }

}
