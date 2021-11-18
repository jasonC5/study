package com.jason.study.algorithmQuestions.class06;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 数组中所有数都异或起来的结果，叫做异或和
 * 给定一个数组arr，可以任意切分成若干个不相交的子数组
 * 其中一定存在一种最优方案，使得切出异或和为0的子数组最多
 * 返回这个最多数量
 * --暴力解：每个位置断或者不断、深度优先遍历+恢复现场、前缀异或和数组	O(2^n)
 * --dp，从左往右尝试模型
 * --异或和重复出现的位置
 * --Max（dp[i-1] , dp[j--上一次出现的相同异或和的位置] + 1）
 * --假设答案法
 *
 * @author JasonC5
 */
public class MostXorZero {

    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random());
        }
        return arr;
    }

    // for test
    public static void printArray(int[] arr) {
        if (arr == null) {
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    // for test
    public static void main(String[] args) {
        int testTime = 150000;
        int maxSize = 12;
        int maxValue = 5;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxSize, maxValue);
            int res = mostXor(arr);
            int comp = comparator(arr);
            if (res != comp) {
                succeed = false;
                printArray(arr);
                System.out.println(res);
                System.out.println(comp);
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Fucking fucked!");
    }

    private static int comparator(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        //段数
        int length = arr.length;
        //异或和
        int[] eor = new int[length];
        eor[0] = arr[0];
        for (int i = 1; i < length; i++) {
            eor[i] = eor[i - 1] ^ arr[i];
        }
        return process(eor, 1, new ArrayList<>());
    }

    /**
     * @param eor   前缀异或数组
     * @param index 当前位置
     * @param parts 分段标记
     * @return
     */
    private static int process(int[] eor, int index, ArrayList<Integer> parts) {
        int ans = 0;
        if (index == eor.length) {
            parts.add(eor.length);
            ans = eorZeroParts(eor, parts);
            parts.remove(parts.size() - 1);
        } else {
            //不分
            int p1 = process(eor, index + 1, parts);
            //分
            parts.add(index);
            int p2 = process(eor, index + 1, parts);
            //恢复现场
            parts.remove(parts.size() - 1);
            ans = Math.max(p1, p2);
        }
        return ans;
    }

    public static int eorZeroParts(int[] eor, ArrayList<Integer> parts) {
        int L = 0;
        int ans = 0;
        for (Integer end : parts) {
            if ((eor[end - 1] ^ (L == 0 ? 0 : eor[L - 1])) == 0) {
                ans++;
            }
            L = end;
        }
        return ans;
    }


    //动态规划，从左到右
    private static int mostXor(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int length = arr.length;
        int dp[] = new int[length];
        dp[0] = arr[0] == 0 ? 1 : 0;
        Map<Integer, Integer> indexMap = new HashMap<>();
        indexMap.put(0, -1);
        //先处理第0位，for中不用处理
        int preXor = arr[0];
        indexMap.put(preXor, 0);
        for (int i = 1; i < length; i++) {
            preXor ^= arr[i];
            //当前位置不分段
            dp[i] = dp[i - 1];
            //当前位置可以分段
            if (indexMap.containsKey(preXor)) {
                Integer pre = indexMap.get(preXor);
                dp[i] = Math.max(dp[i], pre == -1 ? 1 : (dp[pre] + 1));
            }
            indexMap.put(preXor, i);
        }
        return dp[length - 1];
    }

}
