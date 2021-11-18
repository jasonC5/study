package com.jason.study.algorithmQuestions.class05;

/**
 * 给你两个单词 word1 和 word2，请你计算出将 word1 转换成 word2 所使用的最少操作数 。
 * <p>
 * 你可以对一个单词进行如下三种操作：
 * <p>
 * 插入一个字符
 * 删除一个字符
 * 替换一个字符
 *  
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/edit-distance
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author JasonC5
 */
public class LC72 {

    public static void main(String[] args) {
        String str1 = "";
        String str2 = "a";
        System.out.println(minDistance(str1, str2));
    }

    public static int minDistance(String word1, String word2) {
        //动态规划，样本对应模型
        char[] chars1 = word1.toCharArray();
        char[] chars2 = word2.toCharArray();
        //需要多少部才能将str1的i长度的前缀改成str2的j长度前缀
        int dp[][] = new int[chars1.length + 1][chars2.length + 1];
        for (int i = 1; i <= chars1.length; i++) {
            //全部删掉就行了
            dp[i][0] = i;
        }
        for (int j = 1; j <= chars2.length; j++) {
            //一个一个加上就行了
            dp[0][j] = j;
        }
        for (int i = 1; i <= chars1.length; i++) {
            for (int j = 1; j <= chars2.length; j++) {
                //每个普遍位置怎么转换
                //插入
                int p1 = dp[i][j - 1] + 1;
                //删除
                int p2 = dp[i - 1][j] + 1;
                //替换
                int p3 = dp[i - 1][j - 1] + (chars1[i - 1] == chars2[j - 1] ? 0 : 1);
                dp[i][j] = Math.min(p1, Math.min(p2, p3));
            }
        }
        return dp[chars1.length][chars2.length];
    }

}
