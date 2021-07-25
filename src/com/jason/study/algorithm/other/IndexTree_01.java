package com.jason.study.algorithm.other;

/**
 * IndexTree-实现
 * <p>
 * 关联：线段树
 * 解决问题：快速求范围累加和，单点更新
 * 可扩展为二维
 * <p>
 * 每个index，能管住的数【多少数的和】：
 * 最后一个1挪到最后，到自己（奇数就是自己）
 * <p>
 * index前缀和：
 * 从自己开始，然后从右往左依次抹掉最后一个1得到的数字的下标下的数字，直到0，所有的下标上的数字的和
 * <p>
 * 每个位置变动，会影响的index：
 * index的二进制，拿到最右侧的1代表的数字，加到自己上面，得到的数字，依次递推，直到超出范围
 *
 * @author JasonC5
 */
public class IndexTree_01 {
    public static void main(String[] args) {
        int N = 100;
        int V = 100;
        int testTime = 2000000;
        IndexTree tree = new IndexTree(N);
        Right test = new Right(N);
        System.out.println("test begin");
        for (int i = 0; i < testTime; i++) {
            int index = (int) (Math.random() * N) + 1;
            if (Math.random() <= 0.5) {
                int add = (int) (Math.random() * V);
                tree.add(index, add);
                test.add(index, add);
            } else {
                if (tree.sum(index) != test.sum(index)) {
                    System.out.println("Oops!");
                }
            }
        }
        System.out.println("test finish");
    }

    //暴力对数器
    public static class Right {
        private int[] nums;
        private int N;

        public Right(int size) {
            N = size + 1;
            nums = new int[N + 1];
        }

        public int sum(int index) {
            int ret = 0;
            for (int i = 1; i <= index; i++) {
                ret += nums[i];
            }
            return ret;
        }

        public void add(int index, int d) {
            nums[index] += d;
        }
    }

    //IndexTree
    public static class IndexTree {
        private int[] nums;
        int length;

        public IndexTree(int length) {
            this.length = length + 1;
            nums = new int[length + 1];
        }

        public void add(int index, int add) {

            while (index <= length) {
                nums[index] += add;
                index += index & -index;
            }

        }

        public int sum(int index) {
            int ans = 0;
            while (index > 0) {
                ans += nums[index];
                index -= index & -index;
            }
            return ans;
        }
    }


}
