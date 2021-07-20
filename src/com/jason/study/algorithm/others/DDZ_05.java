package com.jason.study.algorithm.others;

/**
 * https://leetcode-cn.com/problems/count-submatrices-with-all-ones
 * <p>
 * 给定一个二维数组matrix，其中的值不是0就是1，
 * 返回全部由1组成的子矩形数量
 *
 * @author JasonC5
 */
public class DDZ_05 {
    public static void main(String[] args) {
        int[][] matrixA = {{1, 0, 1, 0, 0}, {1, 0, 1, 1, 1}, {1, 1, 1, 1, 1}, {1, 0, 0, 1, 0}};
        int i1 = numSubmat(matrixA);
        int i2 = numSubmat2(matrixA);
        System.out.println(i1);
        System.out.println(i2);
    }

    //压缩矩阵
    public static int numSubmat(int[][] matrix) {
        int row = matrix.length;
        if (row == 0) {
            return 0;
        }
        int col = matrix[0].length;
        if (col == 0) {
            return 0;
        }
        int[] arr = new int[col];
        int count = 0;
        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                arr[c] = matrix[r][c] == 0 ? 0 : arr[c] + 1;
            }
            count += subCount(arr);
        }
        return count;
    }

    //单调栈
    public static int subCount(int[] arr) {
        int count = 0;
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
                if (arr[idx] == arr[i]) {
                    //相等的时候不处理，等最后一个弹出的合并处理
                    break;
                }
                //谁把它弹出来了，就是它右边小于它的最近值
                int left = point == -1 ? -1 : stack[point];
//                max = Math.max(max, (right - left + 1) * arr[idx]);
                int n = i - left - 1;
                count += (arr[idx] - Math.max(left == -1 ? 0 : arr[left], arr[i])) * (n * (n + 1) >> 1);
            }
//            stack.push(i);
            stack[++point] = i;
        }
        //再把栈弹空
        while (point != -1) {
            Integer idx = stack[point--];
            int right = arr.length;
            int left = point == -1 ? -1 : stack[point];
//                max = Math.max(max, (right - left + 1) * arr[idx]);
            int n = right - left - 1;
            count += (arr[idx] - (left == -1 ? 0 : arr[left])) * n * (n + 1) / 2;
        }
        return count;
    }



    //对数器
    public static int numSubmat2(int[][] mat) {
        if (mat == null || mat.length == 0 || mat[0].length == 0) {
            return 0;
        }
        int nums = 0;
        int[] height = new int[mat[0].length];
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[0].length; j++) {
                height[j] = mat[i][j] == 0 ? 0 : height[j] + 1;
            }
            nums += countFromBottom(height);
        }
        return nums;

    }

    public static int countFromBottom(int[] height) {
        if (height == null || height.length == 0) {
            return 0;
        }
        int nums = 0;
        int[] stack = new int[height.length];
        int si = -1;
        for (int i = 0; i < height.length; i++) {
            while (si != -1 && height[stack[si]] >= height[i]) {
                int cur = stack[si--];
                if (height[cur] > height[i]) {
                    int left = si == -1 ? -1 : stack[si];
                    int n = i - left - 1;
                    int down = Math.max(left == -1 ? 0 : height[left], height[i]);
                    nums += (height[cur] - down) * num(n);
                }

            }
            stack[++si] = i;
        }
        while (si != -1) {
            int cur = stack[si--];
            int left = si == -1  ? -1 : stack[si];
            int n = height.length - left - 1;
            int down = left == -1 ? 0 : height[left];
            nums += (height[cur] - down) * num(n);
        }
        return nums;
    }

    public static int num(int n) {
        return ((n * (1 + n)) >> 1);
    }
}
