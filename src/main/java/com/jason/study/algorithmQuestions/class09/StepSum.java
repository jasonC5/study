package com.jason.study.algorithmQuestions.class09;

import java.util.HashMap;

/**
 * 定义StepSum
 * 比如：680,680+68+6=754,680的step sum是754
 * 给定一个整数num，判断他是不是某个数的step sum
 *
 * @author JasonC5
 */
public class StepSum {
    // 二分
    public static boolean isStepSum(int stepSum) {
        int left = 0, right = stepSum;
        while (left <= right) {
            int mid = left + ((right - left) >> 1);
            int midStepSum = stepSum(mid);
            if (stepSum == midStepSum) {
                return true;
            } else if (stepSum < midStepSum) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return false;
    }


    // 验证单调性，确认单调性就能二分
//    public static void main(String[] args) {
//        for (int i = 400; i < 500; i++) {
//            System.out.println(i + ", stepNum=" + stepSum(i));
//        }
//    }

    public static int stepSum(int n) {
        int stepSum = n;
        while (n > 0) {
            n /= 10;
            stepSum += n;
        }
        return stepSum;
    }


    // for test
    public static HashMap<Integer, Integer> generateStepSumNumberMap(int numMax) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i <= numMax; i++) {
            map.put(stepSum(i), i);
        }
        return map;
    }

    // for test
    public static void main(String[] args) {
        int max = 1000000;
        int maxStepSum = stepSum(max);
        HashMap<Integer, Integer> ans = generateStepSumNumberMap(max);
        System.out.println("test started");
        for (int i = 0; i <= maxStepSum; i++) {
            if (isStepSum(i) ^ ans.containsKey(i)) {
                System.out.println("出错了！");
            }
        }
        System.out.println("test finished");
    }
}
