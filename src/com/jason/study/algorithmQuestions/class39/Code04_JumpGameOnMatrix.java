package com.jason.study.algorithmQuestions.class39;

/**
 * // 来自京东
 * // 给定一个二维数组matrix，matrix[i][j] = k代表:
 * // 从(i,j)位置可以随意往右跳<=k步，或者从(i,j)位置可以随意往下跳<=k步
 * // 如果matrix[i][j] = 0，代表来到(i,j)位置必须停止
 * // 返回从matrix左上角到右下角，至少要跳几次
 * // 已知matrix中行数n <= 5000, 列数m <= 5000
 * // matrix中的值，<= 5000
 * // 最弟弟的技巧也过了。最优解 -> dp+枚举优化(线段树，体系学习班)
 *
 * @author JasonC5
 */
public class Code04_JumpGameOnMatrix {

    // 为了测试
    public static int[][] randomMatrix(int n, int m, int v) {
        int[][] ans = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                ans[i][j] = (int) (Math.random() * v);
            }
        }
        return ans;
    }

    // 为了测试
    public static void main(String[] args) {
//        // 先展示一下线段树的用法，假设N=100
//        // 初始化时，1~100所有位置的值都是系统最大
//        System.out.println("线段树展示开始");
//        int N = 100;
//        SegmentTree st = new SegmentTree(N);
//        // 查询8~19范围上的最小值
//        System.out.println(st.query(8, 19, 1, N, 1));
//        // 把6~14范围上对应的值都修改成56
//        st.update(6, 14, 56, 1, N, 1);
//        // 查询8~19范围上的最小值
//        System.out.println(st.query(8, 19, 1, N, 1));
//        // 以上是线段树的用法，你可以随意使用update和query方法
//        // 线段树的详解请看体系学习班
//        System.out.println("线段树展示结束");

        // 以下为正式测试
        int len = 10;
        int value = 8;
        int testTimes = 10000;
        System.out.println("start");
        for (int i = 0; i < testTimes; i++) {
            int n = (int) (Math.random() * len) + 1;
            int m = (int) (Math.random() * len) + 1;
            int[][] map = randomMatrix(n, m, value);
            int ans1 = jump1(map);
            int ans2 = jump2(map);
            if (ans1 != ans2) {
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println("error!");
                return;
            }
        }
        System.out.println("end");
    }

    // 暴力方法，仅仅是做对数器
    // 如果无法到达会返回系统最大值
    public static int jump1(int[][] map) {
        return process(map, 0, 0);
    }

    // 当前来到的位置是(row,col)
    // 目标：右下角
    // 当前最大能跳多远，map[row][col]值决定，只能向右、或者向下
    // 返回，到达右下角，最小跳几次？
    // 5000 * 5000 = 25000000 -> 2 * (10 ^ 7)
    public static int process(int[][] map, int row, int col) {
        if (row == map.length - 1 && col == map[0].length - 1) {
            return 0;
        }
        // 如果没到右下角
        if (map[row][col] == 0) {
            return Integer.MAX_VALUE;
        }
        // 当前位置，可以去很多的位置，next含义：
        // 在所有能去的位置里，哪个位置最后到达右下角，跳的次数最少，就是next
        int next = Integer.MAX_VALUE;
        // 往下能到达的位置，全试一遍
        for (int down = row + 1; down < map.length && (down - row) <= map[row][col]; down++) {
            next = Math.min(next, process(map, down, col));
        }
        // 往右能到达的位置，全试一遍
        for (int right = col + 1; right < map[0].length && (right - col) <= map[row][col]; right++) {
            next = Math.min(next, process(map, row, right));
        }
        // 如果所有下一步的位置，没有一个能到右下角，next = 系统最大！
        // 返回系统最大！
        // next != 系统最大 7 + 1
        return next != Integer.MAX_VALUE ? (next + 1) : next;
    }

    //TODO 可以用线段树优化
    public static int jump2(int[][] arr) {
        //给定一个二维数组matrix，matrix[i][j] = k代表:
        //从(i,j)位置可以随意往右跳<=k步，或者从(i,j)位置可以随意往下跳<=k步
        //如果matrix[i][j] = 0，代表来到(i,j)位置必须停止
        //返回从matrix左上角到右下角，至少要跳几次
        int rowLen = arr.length;
        int colLen = arr[0].length;
        int[][] dp = new int[rowLen][colLen];

        for (int row = 0; row < rowLen; row++) {
            for (int col = 0; col < colLen; col++) {
                dp[row][col] = Integer.MAX_VALUE;
            }
        }
        // 最后一行
        dp[rowLen - 1][colLen - 1] = 0;
        for (int col = colLen - 2; col >= 0; col--) {
            int num = arr[rowLen - 1][col];
            //TODO 范围上的最小值，线段树优化
            for (int i = col; i <= Math.min(colLen - 1, col + num); i++) {
                int count = dp[rowLen - 1][i];
                dp[rowLen - 1][col] = Math.min(dp[rowLen - 1][col], count == Integer.MAX_VALUE ? Integer.MAX_VALUE : (count + 1));
            }
        }
        // 最后一列
        for (int row = rowLen - 2; row >= 0; row--) {
            int num = arr[row][colLen - 1];
            //TODO 范围上的最小值，线段树优化
            for (int i = row; i <= Math.min(rowLen - 1, row + num); i++) {
                int count = dp[i][colLen - 1];
                dp[row][colLen - 1] = Math.min(dp[row][colLen - 1], count == Integer.MAX_VALUE ? Integer.MAX_VALUE : (count + 1));
            }
        }
        // 普遍位置
        for (int col = colLen - 2; col >= 0; col--) {
            for (int row = rowLen - 2; row >= 0; row--) {
                int num = arr[row][col];
                // 往同一列跳
                //TODO 范围上的最小值，线段树优化
                for (int i = col; i <= Math.min(colLen - 1, col + num); i++) {
                    int count = dp[row][i];
                    dp[row][col] = Math.min(dp[row][col], count == Integer.MAX_VALUE ? Integer.MAX_VALUE : (count + 1));
                }
                // 往同一行跳
                //TODO 范围上的最小值，线段树优化
                for (int i = row; i <= Math.min(rowLen - 1, row + num); i++) {
                    int count = dp[i][col];
                    dp[row][col] = Math.min(dp[row][col], count == Integer.MAX_VALUE ? Integer.MAX_VALUE : (count + 1));
                }
            }
        }
        return dp[0][0];
    }
}
