package com.jason.study.algorithm.arrayList;

/**
 * 螺旋打印，中间隔行，打印*
 *
 * @author JasonC5
 */
public class PrintStar {
    public static void main(String[] args) {
        printStar(8);
    }

    private static void printStar(int n) {
        //初始化
        char[][] matrix = new char[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = ' ';
            }
        }
        int left = 0, right = n - 1, top = 0, bottom = n - 1;
        int row = 0, col = 0;
        while (top <= bottom && left <= right) {
            while (col <= right) {
                matrix[top][col++] = '*';
            }
            top += 1;
            left += 1;
            row++;
            while (row < bottom) {
                matrix[row++][right] = '*';
            }
            right -= 1;
            top += 1;
            col--;
            while (col >= left) {
                matrix[bottom][col--] = '*';
            }
            bottom -= 1;
            right -= 1;
            row--;
            while (row >= top) {
                matrix[row--][left] = '*';
            }
            left += 1;
            bottom-=1;
            col++;
        }
        for (char[] chars : matrix) {
            for (char aChar : chars) {
                System.out.print(aChar + " ");
            }
            System.out.println();
        }
    }

}
