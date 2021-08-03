package com.jason.study.algorithm.arrayList;

/**
 * 给定一个整数组成的无序数组arr，值可能正、可能负、可能0，给定一个整数值K
 * 找到arr的所有子数组里，哪个子数组的累加和<=K，并且是长度最大的
 * 返回其长度
 * <p>
 * --两个辅助数组：minSum、minSumEnd，从右往左遍历一遍生成好两个子数组
 * --窗口，
 * --舍弃可能性
 *
 * @author JasonC5
 */
public class LongestLessSumSubArrayLength {

    // for test
    public static int[] generateRandomArray(int len, int maxValue) {
        int[] res = new int[len];
        for (int i = 0; i != res.length; i++) {
            res[i] = (int) (Math.random() * maxValue) - (maxValue / 3);
        }
        return res;
    }

    public static void main(String[] args) {
        System.out.println("test begin");
        for (int i = 0; i < 10000000; i++) {
            int[] arr = generateRandomArray(10, 20);
            int k = (int) (Math.random() * 20) - 5;
            if (maxLengthAwesome(arr, k) != maxLength(arr, k)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test finish");
    }

    public static int getLessIndex(int[] arr, int num) {
        int low = 0;
        int high = arr.length - 1;
        int mid = 0;
        int res = -1;
        while (low <= high) {
            mid = (low + high) / 2;
            if (arr[mid] >= num) {
                res = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return res;
    }

    //前缀和--到x位置为右边界时，若全追he为sum， 则找到<= sum - k 的最小前缀，作为左边界，中间的就是以x为终点的最长子数组，将x从0-length-1依次推高

    public static int maxLength(int[] arr, int k) {
        int[] h = new int[arr.length + 1];
        int sum = 0;
        h[0] = sum;
        //i位置的最大前缀
        for (int i = 0; i != arr.length; i++) {
            sum += arr[i];
            h[i + 1] = Math.max(sum, h[i]);
        }
        sum = 0;
        int res = 0;
        int pre = 0;
        int len = 0;
        for (int i = 0; i != arr.length; i++) {
            sum += arr[i];
            pre = getLessIndex(h, sum - k);
            len = pre == -1 ? 0 : i - pre + 1;
            res = Math.max(res, len);
        }
        return res;
    }

    public static int maxLengthAwesome(int[] arr, int k) {
//两个辅助数组：minSum【必须以I开头的子数组，最小的子数组的累加和】、minSumEnd【上一个累加和的结束位置】，从右往左遍历一遍生成好两个子数组
//窗口
//舍弃可能性
        //必须以I开头的子数组，最小的子数组的累加和
        int length = arr.length;
        int[] minSum = new int[length];
        //上一个累加和的结束位置
        int[] minSumEnd = new int[length];

        minSum[length - 1] = arr[length - 1];
        minSumEnd[length - 1] = length - 1;
        //先整好两个辅助数组  从后往前填
        for (int i = length - 2; i >= 0; i--) {
            minSum[i] = minSum[i + 1] < 0 ? minSum[i + 1] + arr[i] : arr[i];
            minSumEnd[i] = minSum[i + 1] < 0 ? minSumEnd[i + 1] : i;
        }
//* 给定一个整数组成的无序数组arr，值可能正、可能负、可能0，给定一个整数值K
//* 找到arr的所有子数组里，哪个子数组的累加和<=K，并且是长度最大的
//* 返回其长度
        //窗口
        int right = 0, sum = 0, len = 0;
        for (int i = 0; i < length; i++) {
            //往右边推高到不能再推的位置【超出边界，或者累加和大于k】
            while (right < length && sum + minSum[right] <= k) {//舍弃的是哪里的可能性？当找到i~j满足的时候，i+1~j不满足，但是不去纠结i+1~j内部是否有满足的了，不重要，舍弃调了这些可能性
                sum += minSum[right];
                right = minSumEnd[right] + 1;
            }
            len = Math.max(len, right - i);
            //收缩左边界，为下一个循环开始做准备
            //这里right会碰到一种情况，当前的数字大于K怎么办，无论如何right都过不去，手动放过去
            if (right > i) {
                sum -= arr[i];
            } else {
//                sum = 0;
                right = i + 1;//下一个循环的时候需要的位置
            }
        }
        return len;
    }
}
