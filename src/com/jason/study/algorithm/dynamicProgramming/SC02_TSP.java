package com.jason.study.algorithm.dynamicProgramming;

import java.util.ArrayList;
import java.util.List;

/**
 * TSP问题 有N个城市，任何两个城市之间的都有距离
 * ，任何一座城市到自己的距离都为0。
 * 所有点到点的距 离都存在一个N*N的二维数组matrix里，
 * 也就是整张图由邻接矩阵表示。
 * 现要求一旅行商从k城市 出发必须经过每一个城市且只在一个城市逗留一次，最后回到出发的k城，
 * 返回总距离最短的路的 距离。参数给定一个matrix，给定k。
 * <p>
 * --无向图
 * --暴力递归
 * --int 位标记，记忆化搜索
 *
 * @author JasonC5
 */
public class SC02_TSP {

    public static int t1(int[][] matrix) {
        int num = matrix.length;
        List<Integer> mark = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            mark.add(1);
        }
        return func1(matrix, mark, 0);
    }

    private static int func1(int[][] matrix, List<Integer> mark, int from) {
        int cityNum = 0;
        for (int i = 0; i < mark.size(); i++) {
            if (mark.get(i) != null) {
                cityNum++;
            }
        }
        if (cityNum == 1) {
            return matrix[from][0];
        }
        mark.set(from, null);
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < mark.size(); i++) {
            if (mark.get(i) != null) {
                min = Math.min(min, matrix[from][i] + func1(matrix, mark, i));
            }
        }
        mark.set(from, 1);
        return min;
    }

    public static int t2(int[][] matrix) {
        int num = matrix.length;
        int mark = (1 << num) - 1;
        return func2(matrix, mark, 0);
    }

    private static int func2(int[][] matrix, int mark, int from) {
        if (mark == (mark & (~mark + 1))) {
            return matrix[from][0];
        }
        mark &= ~(1 << from);
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < matrix.length; i++) {
            if ((1 & (mark >> i)) == 1) {
                min = Math.min(min, matrix[from][i] + func2(matrix, mark, i));
            }
        }
//        mark |= (1 << from);
        return min;
    }

    public static int t3(int[][] matrix) {
        int num = matrix.length;
        int mark = (1 << num) - 1;
        //mark  from
        int[][] dp = new int[1 << num][num];
        for (int i = 0; i < (1 << num); i++) {
            for (int j = 0; j < num; j++) {
                dp[i][j] = -1;
            }
        }
        return func3(matrix, mark, 0, dp);
    }

    private static int func3(int[][] matrix, int mark, int from, int[][] dp) {
        if (dp[mark][from] != -1) {
            return dp[mark][from];
        }
        if (mark == (mark & (~mark + 1))) {
            dp[mark][from] = matrix[from][0];
            return dp[mark][from];
        }
        mark &= ~(1 << from);
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < matrix.length; i++) {
            if ((1 & (mark >> i)) == 1) {
                min = Math.min(min, matrix[from][i] + func2(matrix, mark, i));
            }
        }
        mark |= (1 << from);
        dp[mark][from] = min;
        return dp[mark][from];
    }


    public static void main(String[] args) {
        int len = 10;
        int value = 100;
        System.out.println("start");
        for (int i = 0; i < 20000; i++) {
            int[][] matrix = generateGraph(len, value);
            int origin = (int) (Math.random() * matrix.length);
            int ans1 = t1(matrix);
            int ans2 = t2(matrix);
            int ans3 = t3(matrix);
            if (ans1 != ans2 || ans1 != ans3) {
                System.out.println("failed");
            }
        }
        System.out.println("end");
//        System.out.println("功能测试结束");
//
//        len = 22;
//        System.out.println("性能测试开始，数据规模 : " + len);
//        int[][] matrix = new int[len][len];
//        for (int i = 0; i < len; i++) {
//            for (int j = 0; j < len; j++) {
//                matrix[i][j] = (int) (Math.random() * value) + 1;
//            }
//        }
//        for (int i = 0; i < len; i++) {
//            matrix[i][i] = 0;
//        }
//        long start;
//        long end;
//        start = System.currentTimeMillis();
//        t4(matrix);
//        end = System.currentTimeMillis();
//        System.out.println("运行时间 : " + (end - start) + " 毫秒");
//        System.out.println("性能测试结束");

    }

    public static int[][] generateGraph(int maxSize, int maxValue) {
        int len = (int) (Math.random() * maxSize) + 1;
        int[][] matrix = new int[len][len];
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                matrix[i][j] = (int) (Math.random() * maxValue) + 1;
            }
        }
        for (int i = 0; i < len; i++) {
            matrix[i][i] = 0;
        }
        return matrix;
    }

}
