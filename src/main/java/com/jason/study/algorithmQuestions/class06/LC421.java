package com.jason.study.algorithmQuestions.class06;

/**
 * 给你一个整数数组 nums ，返回 nums[i] XOR nums[j] 的最大运算结果，其中 0 ≤ i ≤ j < n 。
 * <p>
 * 进阶：你可以在 O(n) 的时间解决这个问题吗？
 * <p>
 *  
 * <p>
 * 示例 1：
 * <p>
 * 输入：nums = [3,10,5,25,2,8]
 * 输出：28
 * 解释：最大运算结果是 5 XOR 25 = 28.
 * 示例 2：
 * <p>
 * 输入：nums = [0]
 * 输出：0
 * 示例 3：
 * <p>
 * 输入：nums = [2,4]
 * 输出：6
 * 示例 4：
 * <p>
 * 输入：nums = [8,10,2]
 * 输出：10
 * 示例 5：
 * <p>
 * 输入：nums = [14,70,53,83,49,91,36,80,92,51,66,70]
 * 输出：127
 *  
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/maximum-xor-of-two-numbers-in-an-array
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author JasonC5
 */
public class LC421 {

    public static class Node {
        Node[] son = new Node[2];
    }

    //前缀树
    public static class PrefixTree {
        Node root = new Node();

        public void add(int num) {
            Node cur = root;
            for (int i = 31; i >= 0; i--) {
                int next = 1 & (num >> i);
                cur.son[next] = cur.son[next] == null ? new Node() : cur.son[next];
                cur = cur.son[next];
            }
        }

        public int maxXor(int num) {
            //最想要的：符号位相同（异或完是正数），其他没位都不同
            int want = (num & (1 << 31)) | ((~num) & 0x7fffffff);
            int ans = 0;
            Node cur = root;
            for (int i = 31; i >= 0; i--) {
                int numi = ((num >> i) & 1);//num的第i+1位
                int curWant = (want >> i) & 1;//第i+1位，想要的
//                int curWant = i == 31 ? numi : (1 ^ numi);
                int actual = cur.son[curWant] == null ? (1 ^ curWant) : curWant;//现实能得到的
                cur = cur.son[actual];//下个位置
                ans |= (numi ^ actual) << i;
            }
            return ans;
        }
    }


    public static int findMaximumXOR(int[] arr) {
        //前缀和
        if (arr == null || arr.length == 0 || arr.length == 1) {
            return 0;
        }
        PrefixTree prefixTree = new PrefixTree();
        prefixTree.add(arr[0]);
        int ans = Integer.MIN_VALUE;
        for (int i = 1; i < arr.length; i++) {
            ans = Math.max(ans, prefixTree.maxXor(arr[i]));
            prefixTree.add(arr[i]);
        }
        return ans;
    }
}
