package com.jason.study.algorithmQuestions.class04;

/**
 * 给定一个正整数、负整数和 0 组成的 N × M  矩阵，编写代码找出元素总和最大的子矩阵。
 * <p>
 * 返回一个数组 [r1, c1, r2, c2]，其中 r1, c1 分别代表子矩阵左上角的行号和列号，r2, c2 分别代表右下角的行号和列号。若有多个满足条件的子矩阵，返回任意一个均可。
 * <p>
 * 注意：本题相对书上原题稍作改动
 * <p>
 * 示例：
 * <p>
 * 输入：
 * [
 * [-1,0],
 * [0,-1]
 * ]
 * 输出：[0,1,0,1]
 * 解释：输入中标粗的元素即为输出所表示的矩阵
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/max-submatrix-lcci
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author JasonC5
 */
public class MS_17_24 {

    public int[] getMaxMatrix(int[][] matrix) {
        //二维转一维
        int row = matrix.length;
        int col = matrix[0].length;
        int max = Integer.MIN_VALUE;
        int a = 0, b = 0, c = 0, d = 0;
        for (int i = 0; i < row; i++) {
            //严格i~j行，每一列的累加和
            int[] colSum = new int[col];
            for (int j = i; j < row; j++) {
                int curMax = 0;//这一行的最大累加和
                int start = 0;//开始列，如果到某一列的累加和小于0了，则前面的弃之不用，重置start，记录第几列
                for (int k = 0; k < col; k++) {
                    //严格i~j行，第几列的累加和到第几列的累加是最大的
                    colSum[k] += matrix[j][k];
                    curMax += colSum[k];
                    if (curMax > max) {
                        max = curMax;
                        //记录点位置
                        a = i; b = start; c = j; d = k;
                    }
                    if (curMax < 0) {
                        curMax = 0;
                        start = k + 1;
                    }
                }
            }
        }
        return new int[]{a, b, c, d};
    }

}
