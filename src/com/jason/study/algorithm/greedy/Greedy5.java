package com.jason.study.algorithm.greedy;

import com.sun.deploy.util.StringUtils;

import java.util.HashSet;

/**
 * 5.有一个字符串只有'X'和 '.' 两种字符串组成，X代表灯，可以照亮也可以不照，
 * '.'表示街道一定要亮，一盏灯能照亮3个单位的位置，且只能放在'.'上，任意给一个字符串，请问要多少盏灯能照亮
 *
 * @author JasonC5
 */
public class Greedy5 {

    // for test
    public static String randomString(int len) {
        char[] res = new char[(int) (Math.random() * len) + 1];
        for (int i = 0; i < res.length; i++) {
            res[i] = Math.random() < 0.5 ? 'X' : '.';
        }
        return String.valueOf(res);
    }
    public static void main(String[] args) {
        int len = 20;
        int testTime = 100000;
        for (int i = 0; i < testTime; i++) {
            String test = randomString(len);
            int ans1 = minLight1(test);
            int ans2 = minLight2(test);
            if (ans1 != ans2) {
                System.out.println("oops!");
            }
        }
        System.out.println("finish!");
    }

    public static int minLight2(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        char[] chars = str.toCharArray();
        int index = 0;
        int lightNum = 0;
        while (index < chars.length) {
            if (chars[index] == 'X') {
                index++;
            } else {
                lightNum++;
                if (index + 1 == chars.length) {
                    return lightNum;
                }
                if (chars[index+1] == 'X') {
                    index += 2;
                } else {
                    index += 3;
                }
            }
        }
        return lightNum;
    }


    //暴力解
    public static int minLight1(String road) {
        if (road == null || road.length() == 0) {
            return 0;
        }
        return process(road.toCharArray(), 0, new HashSet<>());
    }

    // str[index....]位置，自由选择放灯还是不放灯
    // str[0..index-1]位置呢？已经做完决定了，那些放了灯的位置，存在lights里
    // 要求选出能照亮所有.的方案，并且在这些有效的方案中，返回最少需要几个灯
    public static int process(char[] str, int index, HashSet<Integer> lights) {
        if (index == str.length) { // 结束的时候
            for (int i = 0; i < str.length; i++) {
                if (str[i] != 'X') { // 当前位置是点的话
                    if (!lights.contains(i - 1) && !lights.contains(i) && !lights.contains(i + 1)) {
                        return Integer.MAX_VALUE;
                    }
                }
            }
            return lights.size();
        } else { // str还没结束
            // i X .
            int no = process(str, index + 1, lights);
            int yes = Integer.MAX_VALUE;
            if (str[index] == '.') {
                lights.add(index);
                yes = process(str, index + 1, lights);
                lights.remove(index);
            }
            return Math.min(no, yes);
        }
    }

}
