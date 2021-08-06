package com.jason.study.algorithm.dynamicProgramming;

/**
 * 你有无限的1*2的砖块，要铺满M*N的区域，
 * 不同的铺法有多少种?	（之前有一个2*N的区域的解法，斐波那契）
 * --递归函数：两个核心方法，一个处理第i行的过程，一个处理i行中第N列的过程（往后传递递归调用自己），列处理完之后递归（一行处理完了调用下一行）
 *
 * @author JasonC5
 */
public class SC03_PavingTile {

    // 严格位置依赖的动态规划解
    public static int ways4(int N, int M) {
        if (N < 1 || M < 1 || ((N * M) & 1) != 0) {
            return 0;
        }
        if (N == 1 || M == 1) {
            return 1;
        }
        int big = N > M ? N : M;
        int small = big == N ? M : N;
        int sn = 1 << small;
        int limit = sn - 1;
        int[] dp = new int[sn];
        dp[limit] = 1;
        int[] cur = new int[sn];
        for (int level = 0; level < big; level++) {
            for (int status = 0; status < sn; status++) {
                if (dp[status] != 0) {
                    int op = (~status) & limit;
                    dfs4(dp[status], op, 0, small - 1, cur);
                }
            }
            for (int i = 0; i < sn; i++) {
                dp[i] = 0;
            }
            int[] tmp = dp;
            dp = cur;
            cur = tmp;
        }
        return dp[limit];
    }

    public static void dfs4(int way, int op, int index, int end, int[] cur) {
        if (index == end) {
            cur[op] += way;
        } else {
            dfs4(way, op, index + 1, end, cur);
            if (((3 << index) & op) == 0) { // 11 << index 可以放砖
                dfs4(way, op | (3 << index), index + 1, end, cur);
            }
        }
    }

    public static void main(String[] args) {
        int N = 7;
        int M = 8;
        System.out.println(ways1(N, M));
        System.out.println(ways2(N, M));
        System.out.println(ways4(N, M));
    }

    //ways2,将pre、cur数组转换成int
    public static int ways2(int N, int M) {
        //M>1,N>1且总地板数偶数个
        if (N < 1 || M < 1 || ((N * M) & 1) != 0) {
            return 0;
        }
        if (N == 1 || M == 1) {
            return 1;
        }
        //只看两层，每个各自三种选择：向上的，向右的，不放
        int max = Math.max(N, M);
        int min = Math.min(N, M);
        int pre = (1 << (min)) - 1;
        //从第几0行开始，到第N-1行结束，递归去
        return process2(pre, 0, max, min);
    }

    private static int process2(int pre, int level, int N, int M) {
        if (N == level) {
            return (pre == (1 << M) - 1) ? 1 : 0;
        }
        int cur = ~pre & ((1 << M) - 1);
        return dfs2(cur, level, N, M, 0);
    }

    private static int dfs2(int cur, int level, int N, int M, int col) {
        if (col == M) {
            return process2(cur, level + 1, N, M);
        }
        int ans = 0;
        //这个格子不放
        ans += dfs2(cur, level, N, M, col + 1);
        //如果能放得下横向的砖块
        if ((cur & (1 << col)) == 0 && col + 1 < M && (cur & (1 << (col + 1))) == 0) {
            int mask = 3 << col;
            cur |= mask;
            ans += dfs2(cur, level, N, M, col + 2);
        }
        return ans;
    }

    public static int ways1(int N, int M) {
        //M>1,N>1且总地板数偶数个
        if (N < 1 || M < 1 || ((N * M) & 1) != 0) {
            return 0;
        }
        if (N == 1 || M == 1) {
            return 1;
        }
        //只看两层，每个各自三种选择：向上的，向右的，不放
        int[] pre = new int[M]; // pre代表-1行的状况
        for (int i = 0; i < M; i++) {
            pre[i] = 1;
        }
        //从第几0行开始，到第N-1行结束，递归去
        return process(pre, 0, N);
    }

    private static int process(int[] pre, int level, int total) {
        if (total == level) {
            for (int i = 0; i < pre.length; i++) {
                if (pre[i] == 0) {
                    return 0;
                }
            }
            return 1;
        }
        //上面有空缺的话，必须放一个向上的，么得选择，剩下的位置，可以自由发挥
        int[] cur = getCur(pre);
        return dfs(cur, level, total, 0);
    }

    private static int dfs(int[] cur, int level, int total, int col) {
        if (col == cur.length) {
            return process(cur, level + 1, total);
        }
        int ans = 0;
        //这个格子不放
        ans += dfs(cur, level, total, col + 1);
        //如果能放得下横向的砖块
        if (cur[col] == 0 && col + 1 < cur.length && cur[col + 1] == 0) {
            cur[col] = 1;
            cur[col + 1] = 1;
            ans += dfs(cur, level, total, col + 2);
            //恢复现场
            cur[col] = 0;
            cur[col + 1] = 0;
        }
        return ans;
    }

    private static int[] getCur(int[] pre) {
        int[] cur = new int[pre.length];
        for (int i = 0; i < pre.length; i++) {
            //1变0,0变1
            cur[i] = 1 - pre[i];
        }
        return cur;
    }
}
