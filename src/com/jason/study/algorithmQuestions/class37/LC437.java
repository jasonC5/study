package com.jason.study.algorithmQuestions.class37;

import java.util.HashMap;
import java.util.Map;

public class LC437 {


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

    public int pathSum(TreeNode root, int targetSum) {
        Map<Integer, Integer> preSumCount = new HashMap<>();
        //空前缀和，有一个
        preSumCount.put(0, 1);
        return process(root, 0, targetSum, preSumCount);
    }

    private int process(TreeNode node, int preSum, int targetSum, Map<Integer, Integer> preSumCount) {
        if (node == null){
            return 0;
        }
        //当前点的前缀和
        int sum = preSum + node.val;
        //要减去的点的前缀和
        int sur = sum - targetSum;
        Integer ans = preSumCount.getOrDefault(sur, 0);
        Integer preExist = preSumCount.getOrDefault(sum, 0);
        preSumCount.put(sum, preExist + 1);
        ans += process(node.left, sum, targetSum, preSumCount);
        ans += process(node.right, sum, targetSum, preSumCount);
        if (preSumCount.get(sum) == 1) {
            preSumCount.remove(sum);
        } else {
            preSumCount.put(sum, preSumCount.get(sum) - 1);
        }
        return ans;
    }
}
