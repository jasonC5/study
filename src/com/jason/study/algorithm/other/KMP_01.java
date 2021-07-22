package com.jason.study.algorithm.other;

/**
 * KMP 算法实现 -- 字符串匹配
 *
 * @author JasonC5
 */
public class KMP_01 {


    /**
     * 查找字s1字符串中，与s2字符串匹配的字串的起始位置
     *
     * @param s1
     * @param s2
     * @return
     */
    public static int find(String s1, String s2) {
        if (s1 == null || s2 == null || s2.length() == 0 || s1.length() < s2.length()) {
            return -1;
        }
        char[] arr1 = s1.toCharArray();
        char[] arr2 = s2.toCharArray();
        int[] next = getNextArr(s2);
        int pointer1 = 0;
        int pointer2 = 0;
        while (pointer1 < s1.length() && pointer2 < s2.length()) {
            if (arr1[pointer1] == arr2[pointer2]) {
                pointer1++;
                pointer2++;
            } else if (pointer2 == 0) {
                //跑到第一位了，也不相等，需要跑S1的下一位了
                pointer1++;
            } else {
                //pointer2跑到前缀后面的位置，继续对比
                pointer2 = next[pointer2];
            }
        }
        return pointer2 == s2.length() ? pointer1 - pointer2 : -1;
    }

    public static int[] getNextArr(String str) {
        if (str.length() == 1) {
            return new int[]{-1};
        }
        char[] chars = str.toCharArray();
        int[] next = new int[str.length()];
        next[0] = -1;
        next[1] = 0;
        //我能想到的实现
//        int cn = 0;// 当前是哪个位置的值再和i-1位置的字符比较
//        for (int i = 2; i < str.length(); i++) {
//            while (true) {
//                if (chars[i - 1] == chars[cn]) {
//                    next[i] = ++cn;
//                    break;
//                } else if (cn == 0) {
//                    next[i] = 0;
//                    break;
//                } else {
//                    //不等，找next[cn]下面的
//                    cn = next[cn];
//                }
//            }
//        }
        //老师的实现
        int i = 2; // 目前在哪个位置上求next数组的值
        int cn = 0; // 当前是哪个位置的值再和i-1位置的字符比较
        while (i < next.length) {
            if (chars[i - 1] == chars[cn]) { // 配成功的时候
                next[i++] = ++cn;
            } else if (cn > 0) {
                cn = next[cn];
            } else {
                next[i++] = 0;
            }
        }
        return next;
    }


//    public static void main(String[] args) {
//        String s1 = "cbbebaacdaddddbe";
//        String s2 = "daddb";
//        System.out.println(find(s1, s2));
//        System.out.println(s1.indexOf(s2));
//    }

    // for test
    public static String getRandomString(int possibilities, int size) {
        char[] ans = new char[(int) (Math.random() * size) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = (char) ((int) (Math.random() * possibilities) + 'a');
        }
        return String.valueOf(ans);
    }

    public static void main(String[] args) {
        int possibilities = 5;
        int strSize = 20;
        int matchSize = 5;
        int testTimes = 5000000;
        System.out.println("test begin");
        for (int i = 0; i < testTimes; i++) {
            String str = getRandomString(possibilities, strSize);
            String match = getRandomString(possibilities, matchSize);
            if (find(str, match) != str.indexOf(match)) {
                System.out.println("Oops!");
                System.out.println("str=" + str + ",match=" + match);

            }
        }
        System.out.println("test finish");
    }

}
