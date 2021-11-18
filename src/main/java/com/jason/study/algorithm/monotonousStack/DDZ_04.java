package com.jason.study.algorithm.monotonousStack;

/**
 * https://leetcode-cn.com/problems/maximal-rectangle/
 *
 * 给定一个二维数组matrix，其中的值不是0就是1，
 * 返回全部由1组成的最大子矩形，内部有多少个1
 *
 * 输入：matrix = [['1','0','1','0','0'],['1','0','1','1','1'],['1','1','1','1','1'],['1','0','0','1','0']]
 * 输出：6
 * 解释：最大矩形如上图所示。
 *
 * @author JasonC5
 */
public class DDZ_04 {

    public static void main(String[] args) {
        char[][] matrix = {{'1','0','1','0','0'},{'1','0','1','1','1'},{'1','1','1','1','1'},{'1','0','0','1','0'}};
        int i = maximalRectangle(matrix);
        System.out.println(i);
    }
    //压缩矩阵
    public static int maximalRectangle(char[][] matrix) {
        int row = matrix.length;
        if (row == 0){
            return 0;
        }
        int col = matrix[0].length;
        if (col == 0){
            return 0;
        }
        int[] arr = new int[col];
        int max = -1;
        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                arr[c] = matrix[r][c] == '0' ? 0 : arr[c] + 1;
            }
            max = Math.max(max, largestRectangleArea(arr));
        }
        return max;
    }
    //单调栈
    public static int largestRectangleArea(int[] arr) {
        int max = -1;
        //左边小于arr[i]的位置，右边小于arr[i]的位置
        int length = arr.length;
//        Stack<Integer> stack = new Stack<>();
        int[] stack = new int[length];
        int point = -1;
        //先遍历数组,找到针对每个元素作为最小值时候的答案
        for (int i = 0; i < length; i++) {
            //把小于当前元素的，都弹出来，获得统计值
            while (point != -1 && arr[stack[point]] >= arr[i]) {
                Integer idx = stack[point--];
                //谁把它弹出来了，就是它右边小于它的最近值
                int right = i - 1;
                int left = point == -1 ? 0 : stack[point] + 1;
                max = Math.max(max, (right - left + 1) * arr[idx]);
            }
//            stack.push(i);
            stack[++point] = i;
        }
        //再把栈弹空
        while (point != -1) {
            Integer idx = stack[point--];
            int right = length - 1;
            int left = point == -1 ? 0 : stack[point] + 1;
            max = Math.max(max, (right - left + 1) * arr[idx]);
        }
        return max;
    }

}
