package com.jason.study.algorithmQuestions.class35;

import java.util.Arrays;

// 来自小红书
// 一场电影开始和结束时间可以用一个小数组来表示["07:30","12:00"]
// 已知有2000场电影开始和结束都在同一天，这一天从00:00开始到23:59结束
// 一定要选3场完全不冲突的电影来观看，返回最大的观影时间
// 如果无法选出3场完全不冲突的电影来观看，返回-1
public class Code03_WatchMovieMaxTime {
    public static int maxEnjoy1(int[][] movies) {
        if (movies == null || movies.length < 3) {
            return -1;
        }
        //先把电影按开始时间从小到大排序，如果时间一致，按结束时间早的排前面
        Arrays.sort(movies, (o1, o2) -> (o1[0] == o2[0]) ? (o1[1] - o2[1]) : (o1[0] - o2[0]));
        //暴力枚举所有三个
        int ans = process(movies, 0, 3, 0);
        return ans < 0 ? -1 : ans;
    }

    private static int process(int[][] movies, int index, int rest, int endTime) {
        if (rest == 0) {
            return 0;
        }
        int ans = 0;
        if (movies.length == index) {
            //剩下的电影都不够了
            return Integer.MIN_VALUE;
        }
        //不看
        ans = process(movies, index + 1, rest, endTime);
        //上一场看完才有可能看下一场
        if (movies[index][0] >= endTime) {
            //看的分钟数
            int cost = movies[index][1] - movies[index][0];
            //不看和看的结果取最大
            ans = Math.max(ans, cost + process(movies, index + 1, rest - 1, movies[index][1]));
        }
        return ans;
    }


    // 为了测试
    public static int[][] randomMovies(int len, int time) {
        int[][] movies = new int[len][2];
        for (int i = 0; i < len; i++) {
            int a = (int) (Math.random() * time);
            int b = (int) (Math.random() * time);
            movies[i][0] = Math.min(a, b);
            movies[i][1] = Math.max(a, b);
        }
        return movies;
    }

    public static void main(String[] args) {
        int n = 10;
        int t = 20;
        int testTime = 10000;
        System.out.println("start");
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * n) + 1;
            int[][] movies = randomMovies(len, t);
            int ans1 = maxEnjoy1(movies);//对的
            int ans2 = maxEnjoy2(movies);//TODO 没调出来，有时间再看
            if (ans1 != ans2) {
                for (int[] m : movies) {
                    System.out.println(m[0] + " , " + m[1]);
                }
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println("error");
                return;
            }
        }
        System.out.println("end");
    }


    public static int maxEnjoy2(int[][] movies) {
        if (movies == null || movies.length < 3) {
            return -1;
        }
        //先把电影按开始时间从小到大排序，如果时间一致，按结束时间早的排前面
        Arrays.sort(movies, (o1, o2) -> (o1[0] == o2[0]) ? (o1[1] - o2[1]) : (o1[0] - o2[0]));
        int length = movies.length;
        // 电影个数、看3场、时间【这里只给0~19，所以20够了】
        int[][][] dp = new int[length + 1][4][21];
        for (int index = length - 1; index >= 0; index--) {
            for (int rest = 1; rest <= 3; rest++) {
                for (int endTime = 20; endTime >= 0; endTime--) {
                    dp[index][rest][endTime] = Integer.MIN_VALUE;
                }
            }
        }
        for (int index = length - 1; index >= 0; index--) {
            for (int rest = 1; rest <= 3; rest++) {
                for (int endTime = 20; endTime >= 0; endTime--) {
                    //不看
                    dp[index][rest][endTime] = dp[index + 1][rest][endTime];
                    //上一场看完才有可能看下一场
                    if (movies[index][0] >= endTime) {
                        //看的分钟数
                        int cost = movies[index][1] - movies[index][0];
                        //不看和看的结果取最大
                        dp[index][rest][endTime] = Math.max(dp[index][rest][endTime], cost + dp[index + 1][rest - 1][movies[index][1]]);
                    }
                }
            }
        }
        return dp[0][3][0] < 0 ? -1 : dp[0][3][0];
    }


}
