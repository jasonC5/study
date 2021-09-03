package com.jason.study.algorithmQuestions.class38;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 字符串 S 由小写字母组成。我们要把这个字符串划分为尽可能多的片段，同一字母最多出现在一个片段中。返回一个表示每个字符串片段的长度的列表。
 *
 *  
 *
 * 示例：
 *
 * 输入：S = "ababcbacadefegdehijhklij"
 * 输出：[9,7,8]
 * 解释：
 * 划分结果为 "ababcbaca", "defegde", "hijhklij"。
 * 每个字母最多出现在一个片段中。
 * 像 "ababcbacadefegde", "hijhklij" 的划分是错误的，因为划分的片段数较少。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/partition-labels
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * @author JasonC5
 */
public class LC763 {

    public List<Integer> partitionLabels(String s) {
        char[] chars = s.toCharArray();
        int[] lastIndex = new int[26];
        // 每个字母最后出现的位置
        for (int i = 0; i < chars.length; i++) {
            lastIndex[chars[i] - 'a'] = i;
        }
        int cutIndex = -1;
        int len = 0;
        List<Integer> ans = new ArrayList<>();
        for (int i = 0; i < chars.length; i++) {
            len++;
            int curLast = lastIndex[chars[i] - 'a'];
            cutIndex = Math.max(cutIndex, curLast);
            if (cutIndex == i) {
                //当前位置可切
                ans.add(len);
                //归零
                len = 0;
            }
        }
        return ans;
    }
}
