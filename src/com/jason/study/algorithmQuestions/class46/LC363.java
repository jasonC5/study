package com.jason.study.algorithmQuestions.class46;

import java.util.TreeSet;

public class LC363 {

    // 方法1 暴力方法
    public int maxSumSubmatrix1(int[][] matrix, int k) {
        //获取矩形区域，就需要两个点圈起来一个矩形，这两个店从左上角移到右下角，完成遍历
        //最小值
        Integer maxVal = null;
        final int n = matrix.length;
        final int m = matrix[0].length;
        int[][] dp = new int[n][m];//左上角到当前位置形成的矩形的和
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                //自己+左边的数+上边的数+左上角的数
                dp[i][j] += matrix[i][j];
                if (i > 0) {
                    dp[i][j] += dp[i - 1][j];
                }
                if (j > 0) {
                    dp[i][j] += dp[i][j - 1];
                }
                if (i > 0 && j > 0) {//加了两遍了，减掉
                    dp[i][j] -= dp[i - 1][j - 1];
                }
            }
        }
        //获得一个结果矩阵：0,0到本位制的矩形结果和
        //点1和点2，在矩阵内部
        for (int x1 = 0; x1 < n; x1++) {
            for (int y1 = 0; y1 < m; y1++) {
                for (int x2 = x1; x2 < n; x2++) {
                    for (int y2 = y1; y2 < m; y2++) {
                        //得到两个点：[x1,y1],[x2,y2]
                        int sum = dp[x2][y2];
                        if (x1 > 0) {
                            sum -= dp[x1 - 1][y2];
                        }
                        if (y1 > 0) {
                            sum -= dp[x2][y1 - 1];
                        }
                        if (x1 > 0 && y1 > 0) {
                            sum += dp[x1 - 1][y1 - 1];
                        }
                        if (sum == k) {
                            return k;
                        }
                        if (sum < k && (maxVal == null || maxVal < sum)) {
                            maxVal = sum;
                        }
                    }
                }
            }
        }
        return maxVal;
    }

    // 方法2，前缀和+有序表
    public static int maxSumSubmatrix(int[][] matrix, int k) {
        if (matrix == null || matrix[0] == null) {
            return 0;
        }
        if (matrix.length > matrix[0].length) {
            //如果行多，列少则翻转，--减少时间复杂度
            matrix = rotate(matrix);
        }
        final int row_len = matrix.length;
        final int col_len = matrix[0].length;
        Integer ans = Integer.MIN_VALUE;
        TreeSet<Integer> preSumMap = new TreeSet<>();
        //每一行开始，到每一行结束
        for (int startRow = 0; startRow < row_len; startRow++) {
            int[] colSum = new int[col_len];
            for (int endRow = startRow; endRow < row_len; endRow++) {
                preSumMap.add(0);
                int rowSum = 0;
                //每一列结束，找最合适的开始列
                for (int col = 0; col < col_len; col++) {
                    colSum[col] += matrix[endRow][col];
                    rowSum += colSum[col];
                    Integer ceiling = preSumMap.ceiling(rowSum - k);
                    if (ceiling != null) {
                        ans = Math.max(ans, rowSum - ceiling);
                    }
                    preSumMap.add(rowSum);
                }
                preSumMap.clear();
            }
        }
        return ans;
    }

    public static int[][] rotate(int[][] matrix) {
        int N = matrix.length;
        int M = matrix[0].length;
        int[][] r = new int[M][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                r[j][i] = matrix[i][j];
            }
        }
        return r;
    }


    public static void main(String[] args) {
//        int[][] matrix = {{2, 2, -1}};
//        int k = 0;
        int[][] matrix =
                {
                        {5, -4, -3, 4},
                        {-3, -4, 4, 5},
                        {5, 1, 5, -4}
                };
        int k = 8;
        System.out.println(maxSumSubmatrix(matrix, k));
    }
}
