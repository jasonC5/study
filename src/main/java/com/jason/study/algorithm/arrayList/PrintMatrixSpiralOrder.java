package com.jason.study.algorithm.arrayList;

/**
 * 给定一个长方形矩阵matrix，实现转圈打印
 * a  b  c  d
 * e  f   g  h
 * i   j   k   L
 * 打印顺序：a b c d h L k j I e f g
 *
 * @author JasonC5
 */
public class PrintMatrixSpiralOrder {
    public static void main(String[] args) {
        int[][] matrix = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}
        };
        spiralOrderPrint(matrix);
    }

    private static void spiralOrderPrint(int[][] matrix) {
        int left = 0, right = matrix[0].length - 1, top = 0, bottom = matrix.length - 1;
        int row = 0, col = 0;
        while (row <= bottom && row >= top && col >= left && col <= right) {
            while (col <= right) {
                System.out.print(matrix[top][col++] + " ");
            }
            top++;
            row++;
            while (row < bottom) {
                System.out.print(matrix[row++][right] + " ");
            }
            right--;
            col--;
            while (col >= left) {
                System.out.print(matrix[bottom][col--] + " ");
            }
            bottom--;
            row--;
            while (row >= top) {
                System.out.print(matrix[row--][left] + " ");
            }
            left++;
            col++;
        }
    }


}
