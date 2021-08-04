package com.jason.study.algorithm.arrayList;

/**
 * 给定一个正方形矩阵matrix，原地调整成顺时针90度转动的样子
 * a  b  c			g  d  a
 * d  e  f			h  e  b
 * g  h  i			i  f  c
 */
public class RotateMatrix {

    public static void main(String[] args) {
        int[][] matrix = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}};
        spiralOrderPrint(matrix);
        for (int[] ints : matrix) {
            for (int i : ints) {
                System.out.print(i + "\t");
            }
            System.out.println();
        }
    }

    private static void spiralOrderPrint(int[][] matrix) {
        //按层处理，一共有几层
        int length = matrix.length;
        int level = length / 2;
        for (int i = 0; i < level; i++) {
            for (int j = i; j < length - 1 - i; j++) {
                int tmp = matrix[i][j];
//                matrix[i][j] = matrix[j][length - 1 - i];
//                matrix[j][length - 1 - i] = matrix[length - 1 - i][length - 1 - j];
//                matrix[length - 1 - i][length - 1 - j] = matrix[length - 1 - j][i];
//                matrix[length - 1 - j][i] = tmp;
                matrix[i][j] = matrix[length - 1 - j][i];
                matrix[length - 1 - j][i] = matrix[length - 1 - i][length - 1 - j];
                matrix[length - 1 - i][length - 1 - j] = matrix[j][length - 1 - i];
                matrix[j][length - 1 - i] = tmp;
            }
        }
    }

}
