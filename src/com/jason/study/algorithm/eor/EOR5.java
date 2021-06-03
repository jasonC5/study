package com.jason.study.algorithm.eor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * 一个数组中有一种数出现了k次，其他所有数都出现了M次，找出这个出现了K次的数
 * 【1<K<M】
 */
public class EOR5 {

    public static void main(String[] args) {
//        int[] arr2 = {23,-28,23,23};
//        int tmp = findKTimesNum(arr2,1,3);
        int kinds = 3;//数字种数
        int range = 100;
        int random = 4;//每个数字出现的次数
        int times = 1000;//试验次数
        for (int i = 0; i < times; i++) {
            int a = ((int) (Math.random() * random) + 1);//1~9
            int b = ((int) (Math.random() * random) + 1);//1~9
            int k = Math.min(a, b);
            int m = Math.max(a, b);
            if (m == k) {
                m++;
            }
//            System.out.println("k="+k+",m="+m);
            int[] arr = randomArray(kinds, range, k, m);
//            Arrays.stream(arr).boxed().forEach(num->System.out.print(num+","));
//            System.out.println();
            int ans = findKTimesNum(arr, k, m);
            //对数器
            int ans2 = hashTest(arr, k, m);
//            System.out.println(ans+","+ans2);
            if (ans != ans2) {
                System.out.println("error……");
                System.out.println("k=" + k + ",m=" + m);
                Arrays.stream(arr).boxed().forEach(num -> System.out.print(num + ","));
                System.out.println();
                System.out.println(ans + "," + ans2);
                return;
            }
        }
        System.out.println("complete!!");

    }

    //哈希表算法
    private static int hashTest(int[] arr, int k, int m) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : arr) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }
        for (Integer num : map.keySet()) {
            if (map.get(num).equals(k)) {
                return num;
            }
        }
        return Integer.MIN_VALUE;
    }

    private static int findKTimesNum(int[] arr, int k, int m) {
        int[] counter = new int[32];
        //counter[o]
        loop:
        for (int num : arr) {
            for (int i = 0; i < 32; i++) {
                if (num == 0) {//之前用的<=0，在存在负数的时候就会有问题
                    continue loop;
                }
                counter[i] += (num & 1);
                num >>>= 1;
            }
        }
        int ans = 0;
        for (int i = 0; i < 32; i++) {
            if (counter[i] % m != 0) {
                ans |= 1 << i;//+= 也行，  |=  效率更高
            }
        }
        return ans;
    }

    /**
     * @param kinds 一共几种数字
     * @param range 每个数范围
     * @param k     一个数字出现k次
     * @param m     其他的数字出现m次
     * @return
     */
    public static int[] randomArray(int kinds, int range, int k, int m) {
        if (2 * range + 1 < kinds) {
            return null;
        }
        //本次随机数组中的数字种数
        int thisKind = (int) (Math.random() * kinds) + 2;
        if (thisKind > kinds) {
            thisKind = kinds;
        }
        int[] arr = new int[k + (thisKind - 1) * m];
        //先拿答案；
        int ans = randomNum(range);
        HashSet set = new HashSet();
        set.add(ans);
        int seek = 0;
        for (; seek < k; seek++) {
            arr[seek] = ans;
        }
        thisKind--;
        while (thisKind-- > 0) {
            //找到不重复的数字，拼到数组中【有隐藏bug，2 * range+1 < kinds  的时候，最后会死循环，数字不够,在上面做校验】
            int num;
            do {
                num = randomNum(range);
            } while (set.contains(num));
            for (int i = 0; i < m; i++) {
                arr[seek++] = num;
            }
        }
        //这样就填好了
        //打乱顺序
        for (int i = 0; i < arr.length; i++) {
            int j = (int) (Math.random() * arr.length);
            if (i != j) {
                arr[i] = arr[i] ^ arr[j];
                arr[j] = arr[i] ^ arr[j];
                arr[i] = arr[i] ^ arr[j];
            }
//            int tmp = arr[i];
//            arr[i] = arr[j];
//            arr[j] = tmp;
        }
        return arr;
    }

    // 随机获取 -range，range的数字
    private static int randomNum(int range) {
        return ((int) (Math.random() * range) + 1) - ((int) (Math.random() * range) + 1);
    }


}
