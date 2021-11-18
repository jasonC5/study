package com.jason.study.algorithm.monotonousStack;

/**
 * https://leetcode-cn.com/problems/sum-of-subarray-minimums/
 *
 * 给定一个整数数组 arr，找到 min(b)'的总和，其中 b 的范围为 arr 的每个（连续）子数组。
 *
 * 由于答案可能很大，因此 返回答案模 10^9 + 7 。
 *
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/sum-of-subarray-minimums
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author JasonC5
 */
public class DDZ_006 {

//    public static void main(String[] args) {
//        int[] arr = {4,3,2,5};
//        int i1 = sumSubarrayMins(arr);
//        int i2 = sumSubarrayMins2(arr);
//        System.out.println(i1);
//        System.out.println(i2);
//    }

    public static int[] randomArray(int len, int maxValue) {
        int[] ans = new int[len];
        for (int i = 0; i < len; i++) {
            ans[i] = (int) (Math.random() * maxValue) + 1;
        }
        return ans;
    }

    public static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int maxLen = 100;
        int maxValue = 5000;
        int testTime = 100000;
        System.out.println("start");
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * maxLen);
            int[] arr = randomArray(len, maxValue);
            int ans1 = sumSubarrayMins(arr);
            int ans2 = sumSubarrayMins2(arr);
            if (ans1 != ans2) {
                printArray(arr);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println("ops !");
                break;
            }
        }
        System.out.println("end");
    }

    public static int sumSubarrayMins(int[] arr) {
        //根据每一个元素，以当前元素为最小值的子数组的数量：，做累加和
        //碰到相等的时候：从左边，算到相等的，把上一个弹出去，自己出来
        //左边小于arr[i]的位置，右边小于arr[i]的位置
        int length = arr.length;
//        Stack<Integer> stack = new Stack<>();
        int[] stack = new int[length];
        int point = -1;
        long ans = 0;
        //先遍历数组
        for (int i = 0; i < length; i++) {
            //把大于当前元素的，都弹出来，获得统计值
            while (point != -1 && arr[stack[point]] >= arr[i]) {
                Integer idx = stack[point--];
                //谁把它弹出来了，就是它右边小于它的最近值
                int right = i;
                //左边
                int left = point == -1 ? -1 : stack[point];
                ans += arr[idx] * (long)(right - idx) * (idx - left);
                ans %= 1000000007;
            }
//            stack.push(i);
            stack[++point] = i;
        }
        //再把栈弹空
        while (point != -1) {
            Integer idx = stack[point--];
            int right = length;
            //左边
            int left = point == -1 ? -1 : stack[point];
            ans += arr[idx] * (right - idx) * (idx - left);
            ans %= 1000000007;
        }
        return (int) ans;
    }


    public static int sumSubarrayMins2(int[] arr) {
        int ans = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i; j < arr.length; j++) {
                int min = arr[i];
                for (int k = i + 1; k <= j; k++) {
                    min = Math.min(min, arr[k]);
                }
                ans += min;
            }
        }
        return ans;
    }


}
