package com.jason.study.algorithm.other;

/**
 * Manacher 查找最长回文子串
 *
 * @author Jasonc5
 */
public class Manacher_01 {

    // for test
    public static String getrandomString(int possibilities, int size) {
        char[] ans = new char[(int) (Math.random() * size) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = (char) ((int) (Math.random() * possibilities) + 'a');
        }
        return String.valueOf(ans);
    }

    // for test
    public static int right(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        char[] str = manacherString(s);
        int max = 0;
        for (int i = 0; i < str.length; i++) {
            int l = i - 1, r = i + 1;
            while (l >= 0 && r < str.length && str[l] == str[r]) {
                l--;
                r++;
            }
            max = Math.max(max, r - l - 1);
        }
        return max / 2;
    }

    private static char[] manacherString(String str) {
        char[] charArr = str.toCharArray();
        char[] res = new char[str.length() * 2 + 1];
        int index = 0;
        for (int i = 0; i != res.length; i++) {
            res[i] = (i & 1) == 0 ? '#' : charArr[index++];
        }
        return res;
    }

    public static void main(String[] args) {
        int possibilities = 5;
        int strSize = 20;
        int testTimes = 5000000;
        System.out.println("test begin");
        for (int i = 0; i < testTimes; i++) {
            String str = getrandomString(possibilities, strSize);
            int manacher = manacher(str);
            int right = right(str);
            if (manacher != right) {
                System.out.println("Oops!");
                System.out.println(str);
                System.out.println("right=" + right);
                System.out.println("manacher=" + manacher);
                return;
            }
        }
        System.out.println("test finish");
    }

//    public static void main(String[] args) {
//        System.out.println(right("dcbbca"));
//        System.out.println(manacher("dcbbca"));
//    }

    private static int manacher(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        char[] str = manacherString(s);
        int max = 0;
        //回文半径
        int[] arr = new int[str.length];
        //最大右边界(不包括自己)，和对应的中心点
        int c = -1, r = -1;
        for (int i = 0; i < str.length; i++) {
//            arr[i] = r > i ? Math.min(arr[2 * c - i], r - i) : 1;
            //i在r外，只能一个个的试，没有加速
            //i在r内，有可能加速：找镜像
            //三种情况:i回文的边界在r内，i回文的边界在r外，i回文的边界与r重合
            //我能想到的实现
//            if (i < r) {
//                //找对称点
//                int j = 2 * c - i;//i - 2 * (i - c)
//                int rj = arr[j];//对称点的回文半径
//                if (j - rj < 2 * c - r) {
//                    arr[i] = j - (2 * c - r);
//                } else if (j - rj > 2 * c - r) {
//                    arr[i] = arr[j];
//                } else {
//                    //边界重合的时候，有可能，扩
//                    int left = 2 * i - r, right = r;
//                    int count = r - i;
//                    while (left >= 0 && right < str.length && str[left] == str[right]) {
//                        left--;
//                        right++;
//                        count++;
//                    }
//                    arr[i] = count;
//                    r = right;
//                    c = i;
//                }
//            } else {
//                //以i为中心，扩
//                int left = i - 1, right = i + 1;
//                int count = 1;
//                while (left >= 0 && right < str.length && str[left] == str[right]) {
//                    left--;
//                    right++;
//                    //扩了几次，半径就是几
//                    count++;
//                }
//                arr[i] = count;
//                r = right;
//                c = i;
//            }
//            max = Math.max(max, arr[i]);
            //老师优化的版本
            int len = r > i ? Math.min(arr[2 * c - i], r - i) : 1;
            while (i + len < str.length && i - len > -1 && str[i + len] == str[i - len]) {
                len++;
            }
            arr[i] = len;
            if (i + arr[i] > r) {
                r = i + arr[i];
                c = i;
            }
            max = Math.max(max, arr[i]);
        }
        return max - 1;
    }


}
