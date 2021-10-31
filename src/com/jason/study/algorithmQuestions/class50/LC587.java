package com.jason.study.algorithmQuestions.class50;

import java.util.*;

/**
 * 在一个二维的花园中，有一些用 (x, y) 坐标表示的树。由于安装费用十分昂贵，你的任务是先用最短的绳子围起所有的树。只有当所有的树都被绳子包围时，花园才能围好栅栏。你需要找到正好位于栅栏边界上的树的坐标。
 * <p>
 *  
 * <p>
 * 示例 1:
 * <p>
 * 输入: [[1,1],[2,2],[2,0],[2,4],[3,3],[4,2]]
 * 输出: [[1,1],[2,0],[4,2],[3,3],[2,4]]
 * 解释:
 * <p>
 * 示例 2:
 * <p>
 * 输入: [[1,2],[2,2],[4,2]]
 * 输出: [[1,2],[2,2],[4,2]]
 * 解释:
 * <p>
 * 即使树都在一条直线上，你也需要先用绳子包围它们。
 *  
 * <p>
 * 注意:
 * <p>
 * 所有的树应当被围在一起。你不能剪断绳子来包围树或者把树分成一组以上。
 * 输入的整数在 0 到 100 之间。
 * 花园至少有一棵树。
 * 所有树的坐标都是不同的。
 * 输入的点没有顺序。输出顺序也没有要求。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/erect-the-fence
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author JasonC5
 */
public class LC587 {
    /**
     * 凸包问题：点阵中，凸多边形边界
     */
    public int[][] outerTrees(int[][] trees) {
        int n = trees.length;
        //来回走两遍，所以最多两倍的大小
        int size = 0;
        int[][] stack = new int[n << 1][2];
        // x小的排前面，x一样的，y小的排前面
        Arrays.sort(trees, (a, b) -> a[0] != b[0] ? a[0] - b[0] : a[1] - b[1]);
        // 第一遍，正向
        for (int i = 0; i < n; i++) {
            while (size > 1 && cross(stack[size - 2], stack[size - 1], trees[i]) > 0) {
                //弹出
                size--;
            }
            //放入栈中
            stack[size++] = trees[i];
        }
        // 然后从尾往头再扫一遍
        for (int i = n - 2; i >= 0; i--) {
            while (size > 1 && cross(stack[size - 2], stack[size - 1], trees[i]) > 0) {
                //弹出
                size--;
            }
            //放入栈中
            stack[size++] = trees[i];
        }
//        // 里面可能有重复的点，去重返回
//        Set<String> exists = new HashSet<>();
//        int[][] ans = new int[size][];
//        int len = 0;
//        for (int[] ints : stack) {
//            if (!exists.contains(ints[0] + "_" + ints[1])) {
//                ans[len++] = ints;
//                exists.add(ints[0] + "_" + ints[1]);
//            }
//        }
//        return Arrays.copyOf(ans, len - 1);
        // 去重返回
        Arrays.sort(stack, 0, size, (a, b) -> b[0] == a[0] ? b[1] - a[1] : b[0] - a[0]);
        n = 1;
        for (int i = 1; i < size; i++) {
            // 如果i点，x和y，与i-1点，x和y都一样
            // i点与i-1点，在同一个位置，此时，i点不保留
            if (stack[i][0] != stack[i - 1][0] || stack[i][1] != stack[i - 1][1]) {
                stack[n++] = stack[i];
            }
        }
        return Arrays.copyOf(stack, n);
    }

    /**
     * 叉乘
     * // 假设有a、b、c三个点，并且给出每个点的(x,y)位置
     * // 从a到c的向量，在从a到b的向量的哪一侧？
     * // 如果a到c的向量，在从a到b的向量右侧，返回正数
     * // 如果a到c的向量，在从a到b的向量左侧，返回负数
     * // 如果a到c的向量，和从a到b的向量重合，返回0
     */
    public static int cross(int[] a, int[] b, int[] c) {
        return (b[1] - a[1]) * (c[0] - b[0]) - (b[0] - a[0]) * (c[1] - b[1]);
    }
}
