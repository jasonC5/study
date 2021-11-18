package com.jason.study.algorithm.other;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * AC自动机
 * 思想：【前缀树 + KMP】
 *
 * @author JasonC5
 */
public class ACAutomation_01 {
    public static void main(String[] args) {
        ACAutomation ac = new ACAutomation();
        ac.insert("dhe");
        ac.insert("he");
        ac.insert("abcdheks");
        // 设置fail指针
        ac.build();
        List<String> contains = ac.contains("abcdhekskdjfafhasldkflskdjhwqaeruv");
        for (String word : contains) {
            System.out.println(word);
        }
    }

    /**
     * 前缀树
     */
    public static class Node {
        //如果该节点是结束节点，以该节点结束的字符串，存到end中
        public String end;
        //end不为空的时候有意义，表明之前在遍历fail指针的之后已经处理过该节点以及后面的fail链节点，无需在处理
        public Boolean endUsed;
        //fail指针
        public Node fail;
        //路径
        public Node[] ways;

        public Node() {
            end = null;
            endUsed = false;
            fail = null;
            ways = new Node[26];
        }
    }

    public static class ACAutomation {
        private Node root;

        public ACAutomation() {
            root = new Node();
        }

        //敏感词，生成前缀树
        public void insert(String s) {
            char[] chars = s.toCharArray();
            Node cur = root;
            for (int i = 0; i < chars.length; i++) {
                int idx = chars[i] - 'a';
                if (cur.ways[idx] == null) {
                    cur.ways[idx] = new Node();
                }
                cur = cur.ways[idx];
            }
            cur.end = s;
        }

        //处理fail指针
        public void build() {
            //宽度优先遍历
            Queue<Node> queue = new LinkedList<>();
            queue.offer(root);

            while(!queue.isEmpty()){
                //父节点弹出来的时候，帮所有子维护 fail 指针
                Node current = queue.poll();
                for (int i = 0; i < 26; i++) {
                    //如果有i号子节点
                    if (current.ways[i] != null) {
                        //先指向root，保底
                        current.ways[i].fail = root;
                        Node cfail = current.fail;
                        //fail链
                        while(cfail != null){
                            if (cfail.ways[i] != null) {
                                //这里要指向 cfail.ways[i] 而不是 cfail，指向和自己一样的元素
                                current.ways[i].fail = cfail.ways[i];
                                break;
                            }
                            cfail = cfail.fail;
                        }
                        //宽度优先遍历
                        queue.offer(current.ways[i]);
                    }
                }
            }


        }

        //文章扫描
        public List<String> contains(String content) {
            List<String> ans = new ArrayList<>();
            char[] chars = content.toCharArray();
            //前缀树指针
            Node cur = root;
            for (char c : chars) {
                int index = c - 'a';
                while (cur.ways[index] == null && cur != root) {
                    cur = cur.fail;
                }
                if (cur.ways[index] != null) {
                    cur = cur.ways[index];
                } //否则 cur就一定等于 root
                //扫一遍fail链，收集fail链 上的敏感词
                Node reader = cur;
                while(reader != root){
                    //之前扫过了，后面一定已经扫过
                    if (reader.endUsed) {
                        break;
                    }
                    //如果需求不同，改这一段
                    if (reader.end != null) {
                        //收集答案
                        ans.add(reader.end);
                        //为下次break做标记
                        reader.endUsed = true;
                    }
                    //如果需求不同，改这一段

                    //fail链下跳
                    reader = reader.fail;
                }
            }
            return ans;
        }
    }

}
