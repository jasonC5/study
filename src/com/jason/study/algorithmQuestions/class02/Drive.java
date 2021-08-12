package com.jason.study.algorithmQuestions.class02;

/**
 * 现有司机N*2人，调度中心会将所有司机平分给A、B两个区域
 * 第 i 个司机去A可得收入为income[i][0]，
 * 第 i 个司机去B可得收入为income[i][1]，
 * 返回所有调度方案中能使所有司机总收入最高的方案，是多少钱
 * --动态规划
 *
 * @author JasonC5
 */
public class Drive {

    // income -> N * 2 的矩阵 N是偶数！
    // 0 [9, 13]
    // 1 [45,60]
    public static int maxMoney1(int[][] income) {
        if (income == null || income.length < 2 || (income.length & 1) == 1) {
            return 0;
        }
        return process(income, 0, income.length >> 1);
    }

    //从左往右，当index前去A或者去B，还剩rest个去A的名额
    private static int process(int[][] income, int index, int rest) {
        int length = income.length;
        if (index == length) {
            return 0;
        }
        if (length - index == rest) {
            //从此开始全都要去A
            return income[index][0] + process(income, index + 1, rest - 1);
        }
        if (rest == 0) {
            //从此开始全都要去B
            return income[index][1] + process(income, index + 1, rest);
        }
        //两个做比较
        int pa = income[index][0] + process(income, index + 1, rest - 1);
        int pb = income[index][1] + process(income, index + 1, rest);
        return Math.max(pa, pb);
    }

    //dp
    public static int maxMoney2(int[][] income) {
        if (income == null || income.length < 2 || (income.length & 1) == 1) {
            return 0;
        }
        int length = income.length;
        int goA = income.length >> 1;
        int[][] dp = new int[length + 1][goA + 1];
        for (int index = length - 1; index >= 0; index--) {
            for (int rest = 0; rest <= goA; rest++) {
                int ans;
                if (length - index == rest) {
                    //从此开始全都要去A
                    ans = income[index][0] + dp[index + 1][rest - 1];
                } else if (rest == 0) {
                    //从此开始全都要去B
                    ans = income[index][1] + dp[index + 1][rest];
                } else {//两个做比较
                    int pa = income[index][0] + dp[index + 1][rest - 1];
                    int pb = income[index][1] + dp[index + 1][rest];
                    ans = Math.max(pa, pb);
                }
                dp[index][rest] = ans;
            }
        }
        return dp[0][goA];
    }



    // 返回随机len*2大小的正数矩阵
    // 值在0~value-1之间
    public static int[][] randomMatrix(int len, int value) {
        int[][] ans = new int[len << 1][2];
        for (int i = 0; i < ans.length; i++) {
            ans[i][0] = (int) (Math.random() * value);
            ans[i][1] = (int) (Math.random() * value);
        }
        return ans;
    }

    public static void main(String[] args) {
        int N = 10;
        int value = 100;
        int testTime = 500;
        System.out.println("start");
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * N) + 1;
            int[][] matrix = randomMatrix(len, value);
            int ans1 = maxMoney1(matrix);
            int ans2 = maxMoney2(matrix);
//            int ans3 = maxMoney3(matrix);
            if (ans1 != ans2 /*|| ans1 != ans3*/) {
                System.out.println(ans1);
                System.out.println(ans2);
//                System.out.println(ans3);
                System.out.println("Oops!");
                return;
            }
        }
        System.out.println("finished");
    }
}
