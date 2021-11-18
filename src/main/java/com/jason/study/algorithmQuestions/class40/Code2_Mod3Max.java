package com.jason.study.algorithmQuestions.class40;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

// 来自去哪儿网
// 给定一个arr，里面的数字都是0~9
// 你可以随意使用arr中的数字，哪怕打乱顺序也行
// 请拼出一个能被3整除的，最大的数字，用str形式返回
public class Code2_Mod3Max {


    // 为了测试
    public static int[] randomArray(int len) {
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = (int) (Math.random() * 10);
        }
        return arr;
    }

    // 为了测试
    public static int[] copyArray(int[] arr) {
        int[] ans = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            ans[i] = arr[i];
        }
        return ans;
    }

    // 为了测试
    public static void main(String[] args) {
        int N = 10;
        int testTimes = 10000;
        System.out.println("start");
        for (int i = 0; i < testTimes; i++) {
            int len = (int) (Math.random() * N);
            int[] arr1 = randomArray(len);
            int[] arr2 = copyArray(arr1);
            int[] arr3 = copyArray(arr1);
            String ans1 = max1(arr1);
            String ans2 = max2(arr2);
            String ans3 = max3(arr3);
            if (!ans1.equals(ans2) || !ans1.equals(ans3)) {
                System.out.println("error!");
                for (int num : arr3) {
                    System.out.print(num + " ");
                }
                System.out.println();
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println(ans3);
                break;
            }
        }
        System.out.println("finished");

    }

    // 暴力解
    public static String max1(int[] arr) {
        Arrays.sort(arr);
        //转换，从大到小排序
        for (int l = 0, r = arr.length - 1; l < r; l++, r--) {
            int tmp = arr[l];
            arr[l] = arr[r];
            arr[r] = tmp;
        }
        StringBuilder builder = new StringBuilder();
        TreeSet<String> set = new TreeSet<>((a, b) -> Integer.valueOf(b).compareTo(Integer.valueOf(a)));
        // 全排列
        process1(arr, 0, builder, set);
        return set.isEmpty() ? "" : set.first();
    }

    public static void process1(int[] arr, int index, StringBuilder builder, TreeSet<String> set) {
        if (index == arr.length) {
            if (builder.length() != 0 && Integer.parseInt(builder.toString()) % 3 == 0) {
                set.add(builder.toString());
            }
        } else {
            //不要当前数
            process1(arr, index + 1, builder, set);
            // 要当前数
            builder.append(arr[index]);
            process1(arr, index + 1, builder, set);
            // 恢复现场
            builder.deleteCharAt(builder.length() - 1);
        }
    }

    // 动态规划
    public static String max2(int[] arr) {
        if (arr == null || arr.length == 0) {
            return "";
        }
        Arrays.sort(arr);
        for (int l = 0, r = arr.length - 1; l < r; l++, r--) {
            int tmp = arr[l];
            arr[l] = arr[r];
            arr[r] = tmp;
        }
        if (arr[0] == 0) {
            return "0";
        }
        String ans = process2(arr, 0, 0);
        String res = ans.replaceAll("^(0+)", "");
        if (!res.equals("")) {
            return res;
        }
        return ans.equals("") ? ans : "0";
    }

    public static String process2(int[] arr, int index, int mod) {
        if (index == arr.length) {
            return mod == 0 ? "" : "$";
        }
        String p1 = "$";
        int nextMod = nextMod(mod, arr[index] % 3);
        String next = process2(arr, index + 1, nextMod);
        if (!next.equals("$")) {
            p1 = String.valueOf(arr[index]) + next;
        }
        String p2 = process2(arr, index + 1, mod);
        if (p1.equals("$") && p2.equals("$")) {
            return "$";
        }
        if (!p1.equals("$") && !p2.equals("$")) {
            return smaller(p1, p2) ? p2 : p1;
        }
        return p1.equals("$") ? p2 : p1;
    }

    public static int nextMod(int require, int current) {
        if (require == 0) {
            if (current == 0) {
                return 0;
            } else if (current == 1) {
                return 2;
            } else {
                return 1;
            }
        } else if (require == 1) {
            if (current == 0) {
                return 1;
            } else if (current == 1) {
                return 0;
            } else {
                return 2;
            }
        } else { // require == 2
            if (current == 0) {
                return 2;
            } else if (current == 1) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    public static boolean smaller(String p1, String p2) {
        if (p1.length() != p2.length()) {
            return p1.length() < p2.length();
        }
        return p1.compareTo(p2) < 0;
    }

    // 贪心
    public static String max3(int[] arr) {
        Arrays.sort(arr);
        //转换，从大到小排序
        for (int l = 0, r = arr.length - 1; l < r; l++, r--) {
            int tmp = arr[l];
            arr[l] = arr[r];
            arr[r] = tmp;
        }
        List<Integer> zeroBucket = new ArrayList<>();
        List<Integer> oneBucket = new ArrayList<>();
        List<Integer> twoBucket = new ArrayList<>();
        //分桶
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] % 3 == 0) {
                zeroBucket.add(arr[i]);
            } else if (arr[i] % 3 == 1) {
                oneBucket.add(arr[i]);
            } else {
                twoBucket.add(arr[i]);
            }
        }
        // 凑、贪心     贪心错误：：6 6 5 4 2 2 0
        List<Integer> ans = new ArrayList<>();
        ans.addAll(zeroBucket);
        if (oneBucket.size() == twoBucket.size()) {
            ans.addAll(oneBucket);
            ans.addAll(twoBucket);
        } else if (oneBucket.size() > twoBucket.size()) {
            ans.addAll(twoBucket);
            ans.addAll(oneBucket.subList(0, twoBucket.size()));
            for (int i = twoBucket.size(); i + 3 <= oneBucket.size(); i++) {
                ans.addAll(oneBucket.subList(i, i + 3));
            }
        } else {
            ans.addAll(oneBucket);
            ans.addAll(twoBucket.subList(0, oneBucket.size()));
            for (int i = oneBucket.size(); i + 3 <= twoBucket.size(); i++) {
                ans.addAll(twoBucket.subList(i, i + 3));
            }
        }
        return ans.stream().sorted((i1, i2) -> i2 - i1).map(Object::toString).collect(Collectors.joining());
    }
}
