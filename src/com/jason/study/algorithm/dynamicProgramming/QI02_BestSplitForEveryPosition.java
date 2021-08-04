package com.jason.study.algorithm.dynamicProgramming;

/**
 * 把题目一中提到的，
 * min{左部分累加和，右部分累加和}，定义为S(N-1)，也就是说：
 * S(N-1)：在arr[0…N-1]范围上，做最优划分所得到的min{左部分累加和，右部分累加和}的最大值
 * 现在要求返回一个长度为N的s数组，
 * s[i] =在arr[0…i]范围上，做最优划分所得到的min{左部分累加和，右部分累加和}的最大值
 * 得到整个s数组的过程，做到时间复杂度O(N)
 * --分开位置往后推，不会退
 *
 * @author JasonC5
 */
public class QI02_BestSplitForEveryPosition {


    public static int[] randomArray(int len, int max) {
        int[] ans = new int[len];
        for (int i = 0; i < len; i++) {
            ans[i] = (int) (Math.random() * max);
        }
        return ans;
    }

    public static boolean isSameArray(int[] arr1, int[] arr2) {
        if (arr1.length != arr2.length) {
            return false;
        }
        int N = arr1.length;
        for (int i = 0; i < N; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int N = 20;
        int max = 30;
        int testTime = 1000000;
        System.out.println("start");
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * N);
            int[] arr = randomArray(len, max);
            int[] ans1 = bestSplit1(arr);
            int[] ans2 = bestSplit2(arr);
            int[] ans3 = bestSplit3(arr);
            if (!isSameArray(ans1, ans2)  || !isSameArray(ans1, ans3)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("end");
    }

    private static int[] bestSplit3(int[] arr) {
        if (arr == null || arr.length == 0) {
            return new int[0];
        }
        int length = arr.length;
        int[] sum = new int[length + 1];
        for (int i = 0; i < length; i++) {
            sum[i + 1] = sum[i] + arr[i];
        }
        int[] ans = new int[length];
        //上一次的划分位置，全是正数，只可能往后，不可能往前
        int best = 0;
        ans[0] = 0;
        for (int i = 1; i < length; i++) {
            while (best + 1 < i) {
//                int before = Math.max(ans[i], Math.min(sum[best + 1], sum[i + 1] - sum[best + 1]));
//                int after = Math.max(ans[i], Math.min(sum[best + 2], sum[i + 1] - sum[best + 2]));
                int before = Math.min(sum[best + 1], sum[i + 1] - sum[best + 1]);
                int after = Math.min(sum[best + 2], sum[i + 1] - sum[best + 2]);
                //这里的 >= 还是 > 用对数器试就行
                if (after >= before) {
                    best++;
                } else {
                    break;
                }
            }
//            ans[i] = Math.max(ans[i], Math.min(sum[best + 1], sum[i + 1] - sum[best + 1]));
            ans[i] = Math.min(sum[best + 1], sum[i + 1] - sum[best + 1]);
        }
        return ans;
    }

        private static int[] bestSplit2(int[] arr) {
        if (arr == null || arr.length == 0) {
            return new int[0];
        }
        int length = arr.length;
        int[] sum = new int[length + 1];
        for (int i = 0; i < length; i++) {
            sum[i + 1] = sum[i] + arr[i];
        }
        int[] ans = new int[length];
        ans[0] = 0;
        for (int i = 1; i < length; i++) {
            for (int s = 0; s < i; s++) {
                int sumL = sum[s + 1];
                int sumR = sum[i + 1] - sumL;
                ans[i] = Math.max(ans[i], Math.min(sumL, sumR));
            }
        }
        return ans;
    }


    private static int[] bestSplit1(int[] arr) {
        if (arr == null || arr.length == 0) {
            return new int[0];
        }
        int length = arr.length;
        int[] ans = new int[length];
        ans[0] = 0;
        for (int i = 1; i < length; i++) {
            for (int s = 0; s < i; s++) {
                int sumL = 0;
                for (int L = 0; L <= s; L++) {
                    sumL += arr[L];
                }
                int sumR = 0;
                for (int R = s + 1; R <= i; R++) {
                    sumR += arr[R];
                }
                ans[i] = Math.max(ans[i], Math.min(sumL, sumR));
            }
        }
        return ans;
    }
}
