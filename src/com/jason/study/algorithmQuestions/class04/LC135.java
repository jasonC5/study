package com.jason.study.algorithmQuestions.class04;

/**
 * @author JasonC5
 */
public class LC135 {

    public static int candy(int[] ratings) {
        int length = ratings.length;
        int[] left = new int[length];
        int[] right = new int[length];
        left[0] = 1;
        for (int i = 1; i < length; i++) {
            left[i] = ratings[i] > ratings[i - 1] ? left[i - 1] + 1 : 1;
        }
        right[length - 1] = 1;
        for (int i = length - 2; i >= 0; i--) {
            right[i] = ratings[i] > ratings[i + 1] ? right[i + 1] + 1 : 1;
        }
        int ans = 0;
        for (int i = 0; i < length; i++) {
            ans += Math.max(left[i], right[i]);
        }
        return ans;
    }

    //进阶：相邻的孩子间如果分数一样，分的糖果数必须一样
    public static int candy2(int[] ratings) {
        int length = ratings.length;
        int[] left = new int[length];
        int[] right = new int[length];
        left[0] = 1;
        for (int i = 1; i < length; i++) {
            left[i] = ratings[i] > ratings[i - 1] ? left[i - 1] + 1 : (ratings[i] == ratings[i - 1] ? left[i - 1] : 1);
        }
        right[length - 1] = 1;
        for (int i = length - 2; i >= 0; i--) {
            right[i] = ratings[i] > ratings[i + 1] ? right[i + 1] + 1 : (ratings[i] == ratings[i + 1] ? right[i + 1] : 1);
        }
        int ans = 0;
        for (int i = 0; i < length; i++) {
            ans += Math.max(left[i], right[i]);
        }
        return ans;
    }

    // 这是进阶问题的最优解，不要提交这个
    // 时间复杂度O(N), 额外空间复杂度O(1)
    public static int candy3(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int index = nextMinIndex3(arr, 0);
        int[] data = rightCandsAndBase(arr, 0, index++);
        int res = data[0];
        int lbase = 1;
        int same = 1;
        int next = 0;
        while (index != arr.length) {
            if (arr[index] > arr[index - 1]) {
                res += ++lbase;
                same = 1;
                index++;
            } else if (arr[index] < arr[index - 1]) {
                next = nextMinIndex3(arr, index - 1);
                data = rightCandsAndBase(arr, index - 1, next++);
                if (data[1] <= lbase) {
                    res += data[0] - data[1];
                } else {
                    res += -lbase * same + data[0] - data[1] + data[1] * same;
                }
                index = next;
                lbase = 1;
                same = 1;
            } else {
                res += lbase;
                same++;
                index++;
            }
        }
        return res;
    }

    public static int nextMinIndex3(int[] arr, int start) {
        for (int i = start; i != arr.length - 1; i++) {
            if (arr[i] < arr[i + 1]) {
                return i;
            }
        }
        return arr.length - 1;
    }

    public static int[] rightCandsAndBase(int[] arr, int left, int right) {
        int base = 1;
        int cands = 1;
        for (int i = right - 1; i >= left; i--) {
            if (arr[i] == arr[i + 1]) {
                cands += base;
            } else {
                cands += ++base;
            }
        }
        return new int[]{cands, base};
    }


    public static void main(String[] args) {
        int[] test1 = {3, 0, 5, 5, 4, 4, 0};
        System.out.println(candy2(test1));

        int[] test2 = {3, 0, 5, 5, 4, 4, 0};
        System.out.println(candy3(test2));
    }
}
