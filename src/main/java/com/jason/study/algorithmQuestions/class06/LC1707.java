package com.jason.study.algorithmQuestions.class06;

/**
 * 给你一个由非负整数组成的数组 nums 。另有一个查询数组 queries ，其中 queries[i] = [xi, mi] 。
 * <p>
 * 第 i 个查询的答案是 xi 和任何 nums 数组中不超过 mi 的元素按位异或（XOR）得到的最大值。换句话说，答案是 max(nums[j] XOR xi) ，其中所有 j 均满足 nums[j] <= mi 。如果 nums 中的所有元素都大于 mi，最终答案就是 -1 。
 * <p>
 * 返回一个整数数组 answer 作为查询的答案，其中 answer.length == queries.length 且 answer[i] 是第 i 个查询的答案。
 * <p>
 * <p>
 * <p>
 * 示例 1：
 * <p>
 * 输入：nums = [0,1,2,3,4], queries = [[3,1],[1,3],[5,6]]
 * 输出：[3,3,7]
 * 解释：
 * 1) 0 和 1 是仅有的两个不超过 1 的整数。0 XOR 3 = 3 而 1 XOR 3 = 2 。二者中的更大值是 3 。
 * 2) 1 XOR 2 = 3.
 * 3) 5 XOR 2 = 7.
 * 示例 2：
 * <p>
 * 输入：nums = [5,2,4,6,6,3], queries = [[12,4],[8,1],[6,3]]
 * 输出：[15,-1,5]
 * <p>
 * <p>
 * 提示：
 * <p>
 * 1 <= nums.length, queries.length <= 105
 * queries[i].length == 2
 * 0 <= nums[j], xi, mi <= 109
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/maximum-xor-with-an-element-from-array
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author JasonC5
 */
public class LC1707 {


    public static class Node {
        Node[] son = new Node[2];
        int min = Integer.MAX_VALUE;
    }

    //前缀树
    public static class PrefixTree {
        Node root = new Node();

        public void add(int num) {
            Node cur = root;
            cur.min = Math.min(cur.min, num);
            for (int i = 31; i >= 0; i--) {
                int next = 1 & (num >> i);
                cur.son[next] = cur.son[next] == null ? new Node() : cur.son[next];
                cur = cur.son[next];
                cur.min = Math.min(cur.min, num);
            }
        }

        public int maxXor(int x, int m) {
            //最想要的：符号位相同（异或完是正数），其他没位都不同
            if (root.min > m) {
                return -1;
            }
            int want = (x & (1 << 31)) | ((~x) & 0x7fffffff);
            int ans = 0;
            Node cur = root;
            for (int i = 31; i >= 0; i--) {
                int numi = ((x >> i) & 1);//num的第i+1位
                int curWant = (want >> i) & 1;//第i+1位，想要的
//                int curWant = i == 31 ? numi : (1 ^ numi);
                int actual = (cur.son[curWant] == null || cur.son[curWant].min > m) ? (1 ^ curWant) : curWant;//现实能得到的
                cur = cur.son[actual];//下个位置
                ans |= (numi ^ actual) << i;
            }
            return ans;
        }
    }

    public static int[] maximizeXor(int[] nums, int[][] queries) {
        //前缀和
        PrefixTree prefixTree = new PrefixTree();
        prefixTree.add(nums[0]);
        for (int i = 1; i < nums.length; i++) {
            prefixTree.add(nums[i]);
        }
        int[] ans = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            ans[i] = prefixTree.maxXor(queries[i][0], queries[i][1]);
        }
        return ans;
    }

    public static void main(String[] args) {
        int[] nums = {0, 1, 2, 3};
        int[][] queries = {{3, 1}, {1, 3}, {5, 6}};
        int[] ints = maximizeXor(nums, queries);
        System.out.println(ints);
    }
}
