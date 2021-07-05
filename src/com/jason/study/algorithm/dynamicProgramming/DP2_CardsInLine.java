package com.jason.study.algorithm.dynamicProgramming;

/**
 * 给定一个数组，元素是纸牌大小，两个选手只能先后从最左或者最右的纸牌中选择一张，设定两个人都是聪明人，返回最后获胜者的点数
 *
 * @author JasonC5
 */
public class DP2_CardsInLine {
    //递归版本
    //两个方法，一个先手，一个后手
    public static void main(String[] args) {
        int[] cards = {1, 4, 3, 5, 2, 6};
        int winner = win1(cards);
        System.out.println(winner);
        int winner2 = win2(cards);
        System.out.println(winner2);
        int winner3 = win3(cards);
        System.out.println(winner3);
    }

    private static int win1(int[] cards) {
        int length = cards.length;
        int firstPoint = firstHand(cards, 0, length - 1);
        int backPoint = backHand(cards, 0, length - 1);
        return Math.max(firstPoint, backPoint);
    }

    /**
     * 后手能拿到的最大分数
     *
     * @param cards
     * @return
     */
    private static int backHand(int[] cards, int start, int end) {
        if (start == end) {
            //就剩最后一个，但是后手拿不到
            return 0;
        }
        //下一位拿走了start，此时变为先手
        int ans1 = firstHand(cards, start + 1, end);
        //下一位拿走了end，此时变为先手
        int ans2 = firstHand(cards, start, end - 1);
        //下一位挑完之后，只会给你留下最小的【被动选择】
        return Math.min(ans1, ans2);
    }

    /**
     * 先手能拿到的最大分数
     *
     * @param cards
     * @return
     */
    private static int firstHand(int[] cards, int start, int end) {
        //拿左边界和右边界得到的最终结果中最好的
        if (start == end) {
            //Base Case
            return cards[start];
        }
        int ans1 = cards[start] + /*拿完就变成后手了*/ backHand(cards, start + 1, end);
        int ans2 = cards[end] + /*拿完就变成后手了*/ backHand(cards, start, end - 1);
        //主动选择最大的
        return Math.max(ans1, ans2);
    }

    //改动态规划
    //1.【记忆搜索缓存递归】--两张缓存表
    private static int win2(int[] cards) {
        int length = cards.length;
        int[][] firstDP = new int[length][length];
        int[][] backDP = new int[length][length];
        init(firstDP);
        init(backDP);
        int firstPoint = firstHand2(cards, 0, length - 1, firstDP, backDP);
        int backPoint = backHand2(cards, 0, length - 1, firstDP, backDP);
        return Math.max(firstPoint, backPoint);
    }

    private static void init(int[][] arr) {
        int row = arr.length;
        int clown = arr[0].length;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < clown; j++) {
                arr[i][j] = -1;
            }
        }
    }

    /**
     * 后手能拿到的最大分数
     *
     * @param cards
     * @return
     */
    private static int backHand2(int[] cards, int start, int end, int[][] firstDP, int[][] backDP) {
        if (backDP[start][end] != -1) {
            return backDP[start][end];
        }
        if (start == end) {
            //就剩最后一个，但是后手拿不到
            return 0;
        }
        //下一位拿走了start，此时变为先手
        int ans1 = firstHand2(cards, start + 1, end, firstDP, backDP);
        //下一位拿走了end，此时变为先手
        int ans2 = firstHand2(cards, start, end - 1, firstDP, backDP);
        //下一位挑完之后，只会给你留下最小的【被动选择】
        return backDP[start][end] = Math.min(ans1, ans2);
    }

    /**
     * 先手能拿到的最大分数
     *
     * @param cards
     * @return
     */
    private static int firstHand2(int[] cards, int start, int end, int[][] firstDP, int[][] backDP) {
        if (firstDP[start][end] != -1) {
            return firstDP[start][end];
        }
        //拿左边界和右边界得到的最终结果中最好的
        if (start == end) {
            //Base Case
            return cards[start];
        }
        int ans1 = cards[start] + /*拿完就变成后手了*/ backHand2(cards, start + 1, end, firstDP, backDP);
        int ans2 = cards[end] + /*拿完就变成后手了*/ backHand2(cards, start, end - 1, firstDP, backDP);
        //主动选择最大的
        return firstDP[start][end] = Math.max(ans1, ans2);
    }


    //2.严格dp表依赖的动态规划【推dp表】

    private static int win3(int[] cards) {
        int length = cards.length;
        //维度：变化点--起点到终点
        int[][] firstDP = new int[length][length];
        int[][] backDP = new int[length][length];
        //最初赋值：baseCase
        for (int i = 0; i < length; i++) {
            firstDP[i][i] = cards[i];
            backDP[i][i] = 0;
        }
        //推导：根据递归逻辑
        //从倒数第二行开始，倒数第一行已经被对角线填好了
        for (int i = length - 2; i >= 0; i--) {
            for (int j = i + 1; j < length; j++) {
                //自然智慧，从下往上推，从左往右推
                firstDP[i][j] = Math.max(cards[i] + backDP[i + 1][j], cards[j] + backDP[i][j - 1]);
                backDP[i][j] = Math.min(firstDP[i + 1][j], firstDP[i][j - 1]);
            }
        }
        return Math.max(firstDP[0][length - 1], backDP[0][length - 1]);
    }

}
