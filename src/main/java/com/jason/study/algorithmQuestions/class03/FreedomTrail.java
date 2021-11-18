package com.jason.study.algorithmQuestions.class03;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 电子游戏“辐射4”中，任务“通向自由”要求玩家到达名为“Freedom Trail Ring”的金属表盘，并使用表盘拼写特定关键词才能开门。
 * <p>
 * 给定一个字符串 ring，表示刻在外环上的编码；给定另一个字符串 key，表示需要拼写的关键词。您需要算出能够拼写关键词中所有字符的最少步数。
 * <p>
 * 最初，ring 的第一个字符与12:00方向对齐。您需要顺时针或逆时针旋转 ring 以使 key 的一个字符在 12:00 方向对齐，然后按下中心按钮，以此逐个拼写完 key 中的所有字符。
 * <p>
 * 旋转 ring 拼出 key 字符 key[i] 的阶段中：
 * <p>
 * 您可以将 ring 顺时针或逆时针旋转一个位置，计为1步。旋转的最终目的是将字符串 ring 的一个字符与 12:00 方向对齐，并且这个字符必须等于字符 key[i] 。
 * 如果字符 key[i] 已经对齐到12:00方向，您需要按下中心按钮进行拼写，这也将算作 1 步。按完之后，您可以开始拼写 key 的下一个字符（下一阶段）, 直至完成所有拼写。
 * 示例：
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * 输入: ring = "godding", key = "gd"
 * 输出: 4
 * 解释:
 * 对于 key 的第一个字符 'g'，已经在正确的位置, 我们只需要1步来拼写这个字符。
 * 对于 key 的第二个字符 'd'，我们需要逆时针旋转 ring "godding" 2步使它变成 "ddinggo"。
 * 当然, 我们还需要1步进行拼写。
 * 因此最终的输出是 4。
 * 提示：
 * <p>
 * ring 和 key 的字符串长度取值范围均为 1 至 100；
 * 两个字符串中都只有小写字符，并且均可能存在重复字符；
 * 字符串 key 一定可以由字符串 ring 旋转拼出。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/freedom-trail
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author JasonC5
 */
public class FreedomTrail {

    public int findRotateSteps(String ring, String key) {
        char[] ringChars = ring.toCharArray();
        int ringLength = ringChars.length;
        //维护好一个map，每个字母所在的位置
        Map<Character, List<Integer>> indexMap = new HashMap<>();
        for (int i = 0; i < ringLength; i++) {
            List<Integer> list = indexMap.getOrDefault(ringChars[i], new ArrayList<>());
            list.add(i);
            indexMap.put(ringChars[i], list);
        }
        char[] keyChars = key.toCharArray();
        int keyLength = keyChars.length;
        //每一次，可以正向转动，也可以逆向转动找到离得最近的next字符
        //动态规划(记忆化搜索)
        int[][] dp = new int[ringLength][keyLength + 1];
        // hashmap
        // dp[][] == -1 : 表示没算过！
        for (int i = 0; i < ringLength; i++) {
            for (int j = 0; j <= keyLength; j++) {
                dp[i][j] = -1;
            }
        }
        return process(0, 0, keyChars, indexMap, ringLength, dp);
    }

    private int process(int pre, int index, char[] keyChars, Map<Character, List<Integer>> indexMap, int ringLength, int[][] dp) {
        if (dp[pre][index] != -1) {
            return dp[pre][index];
        }
        int ans = Integer.MAX_VALUE;
        if (index == keyChars.length) {
            ans = 0;
        } else {
            //本次要转到的字符
            char next = keyChars[index];
            //电话上那几个位置有这个字符
            List<Integer> list = indexMap.get(next);
            for (Integer target : list) {
                int cost = cost(pre, target, ringLength) + 1 + process(target, index + 1, keyChars, indexMap, ringLength, dp);
                ans = Math.min(ans, cost);
            }
        }
        dp[pre][index] = ans;
        return ans;
    }

    private int cost(int source, Integer target, int ringLength) {
        //最小的
        return Math.min(Math.abs(target - source), Math.min(source, target) + ringLength - Math.max(source, target));
    }


}
