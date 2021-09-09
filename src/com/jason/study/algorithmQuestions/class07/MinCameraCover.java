package com.jason.study.algorithmQuestions.class07;

import java.util.Arrays;

/**
 * 相机最小覆盖问题
 * 给定一个二叉树，我们在树的节点上安装摄像头。
 * <p>
 * 节点上的每个摄影头都可以监视其父对象、自身及其直接子对象。
 * <p>
 * 计算监控树的所有节点所需的最小摄像头数量。
 * <p>
 * <p>
 * 示例 1：
 * <p>
 * <p>
 * <p>
 * 输入：[0,0,null,0,0]
 * 输出：1
 * 解释：如图所示，一台摄像头足以监控所有节点。
 * 示例 2：
 * <p>
 * <p>
 * <p>
 * 输入：[0,0,null,0,null,0,null,null,0]
 * 输出：2
 * 解释：需要至少两个摄像头来监视树的所有节点。 上图显示了摄像头放置的有效位置之一。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/binary-tree-cameras
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author JasonC5
 */
public class MinCameraCover {
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

    // 二叉树的后续遍历套路：：每个节点有三种情况：1.相机在子节点上，2.相机在自己节点上，3.相机在父节点上(自己没有被覆盖)
    public static int minCameraCover1(TreeNode root) {
        int[] info = process(root);
        return Math.min(info[0], Math.min(info[1], info[2] + 1));
    }

    // 返回三个数据1.相机在子节点上，2.相机在自己节点上，3.相机在父节点上(自己没有被覆盖) 的时候分别有几个相机
    public static int[] process(TreeNode node) {
        if (node == null) {
            return new int[]{0, Integer.MAX_VALUE, Integer.MAX_VALUE};
        }
        int[] leftInfo = process(node.left);
        int[] rightInfo = process(node.right);
        int p1, p2, p3;
        //自己的三种情况：
        //1.相机在子节点上
        p1 = Math.min(
                // 灯在左子节点上，右子节点管好自己（上面没灯）
                leftInfo[1] == Integer.MAX_VALUE ? Integer.MAX_VALUE : (leftInfo[1] + Math.min(rightInfo[0], rightInfo[1]))
                ,
                // 灯在右子节点上，左子节点管好自己（上面没灯）
                rightInfo[1] == Integer.MAX_VALUE ? Integer.MAX_VALUE : (rightInfo[1] + Math.min(leftInfo[0], leftInfo[1]))
        );
        //2.相机在自己上，自己有灯了，孩子节点上灯可有可无
        p2 = 1 + Math.min(leftInfo[0], Math.min(leftInfo[1], leftInfo[2])) + Math.min(rightInfo[0], Math.min(rightInfo[1], rightInfo[2]));
        //3.相机在父节点上，我这没灯，孩子节点管好自己
        p3 = Math.min(leftInfo[0], leftInfo[1]) + Math.min(rightInfo[0], rightInfo[1]);
        return new int[]{p1, p2, p3};
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode();
        root.left = new TreeNode();
        root.left.left = new TreeNode();
        root.left.right = new TreeNode();
        System.out.println(minCameraCover(root));
    }

    //贪心：
    public static int minCameraCover(TreeNode root) {
        int[] info = process2(root);
        // 没被覆盖的情况下，要加一个相机
        return info[1] + (info[0] == 2 ? 1 : 0);
    }

    //返回两个值，一个是自己属于那种情况，一个是自己节点下面最少相机数
    // 贪心：1.叶子节点自己不放相机，给上面的放，2.下面的节点有相机，自己不放相机给父节点放
    // 定义三个情况放在int[0]上：0相机在子节点上，1相机在自己这，2相机在父节点上
    private static int[] process2(TreeNode node) {
        // 空节点，baseCase
        if (node == null) {
            return new int[]{0, 0};
        }
        int[] leftInfo = process2(node.left);
        int[] rightInfo = process2(node.right);
        if (leftInfo[0] == 2 || rightInfo[0] == 2) {
            // 孩子节点上有一个没有被覆盖的情况下，自己这个节点必须要有相机
            return new int[]{1, leftInfo[1] + rightInfo[1] + 1};
        } else if (leftInfo[0] == 1 || rightInfo[0] == 1) {
            // 孩子节点都被覆盖了，而且至少有一个孩子上面有相机，那么我就被覆盖了
            return new int[]{0, leftInfo[1] + rightInfo[1]};
        } else {
            // 孩子节点都被覆盖了，但是都没有相机，我自己没有被覆盖，不放灯了，给父亲节点，让他放相机去【贪心】
            return new int[]{2, leftInfo[1] + rightInfo[1]};
        }
    }

}
