package com.jason.study.algorithm.arrayList;

/**
 * 给定一个正方形或者长方形矩阵matrix，实现zigzag打印
 * 0 1 2
 * 3 4 5
 * 6 7 8
 * 打印: 0 1 3 6 4 2 5 7 8
 *
 * @author JasonC5
 */
public class ZigZagPrintMatrix {
    public static void main(String[] args) {
        int[][] matrix = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12}
        };
        printMatrixZigZag(matrix);
    }

    private static void printMatrixZigZag(int[][] matrix) {
        //两个点：上面的点：先往左再往右，下面的点，先往下再往右，然后每一次点变动的时候，换方向，打印
        //初始位置(0,0) (0,0)
        int x1 = 0, y1 = 0, x2 = 0, y2 = 0;
        boolean flag = false;//从上往下打印
        while (x1 < matrix.length) {
            print(matrix, x1, y1, x2, y2, flag);
            if (y1 != matrix[0].length - 1) {
                y1++;
            } else {
                x1++;
            }
            if (x2 != matrix.length - 1) {
                x2++;
            } else {
                y2++;
            }
            flag = !flag;
        }
    }

    private static void print(int[][] matrix, int x1, int y1, int x2, int y2, boolean flag) {
        while (x1 <= x2) {
            if (flag) {
                System.out.print(matrix[x1++][y1--] +" ");
            } else {
                System.out.print(matrix[x2--][y2++] +" ");
            }
        }
    }


}
