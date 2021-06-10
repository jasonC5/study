package com.jason.study.algorithm.tree;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

/**
 * 前缀树
 *
 * @author JasonC5
 */
public class PrefixTree {

    // 前缀树节点类型
    public static class Node1 {
        public int pass;
        public int end;
        public Node1[] nexts;//数组写法--针对元素固定的场景

        public Node1() {
            pass = 0;
            end = 0;
            nexts = new Node1[26];//26个字母
        }
    }

    public static class Trie1 {
        Node1 root;

        public Trie1() {
            this.root = new Node1();
        }

        //添加
        public void insert(String word) {
            if (word == null) {
                return;
            }
            char[] chars = word.toCharArray();
            Node1 point = root;
            point.pass++;//根节点先加一
            for (int i = 0; i < word.length(); i++) {
                //第几个
                if (point.nexts[chars[i] - 'a'] == null) {
                    point.nexts[chars[i] - 'a'] = new Node1();
                }
                point = point.nexts[chars[i] - 'a'];
                point.pass++;//经过数+1
            }
            point.end++;//以改节点结尾+1
        }

        //查询一个字符串出现了几次
        public int search(String word) {
            if (word == null) {
                return 0;
            }
            char[] chars = word.toCharArray();
            Node1 point = root;
            if (point.pass <= 0) {
                return 0;
            }
            for (int i = 0; i < word.length(); i++) {
                //第几个
                if ((point = point.nexts[chars[i] - 'a']) == null) {
                    return 0;
                }
            }
            return point.end;
        }

        //查询以指定字符串为前缀的开头的字符串的个数
        public int prefix(String word) {
            if (word == null) {
                return 0;
            }
            char[] chars = word.toCharArray();
            Node1 point = root;
            if (point.pass <= 0) {
                return 0;
            }
            for (int i = 0; i < word.length(); i++) {
                //第几个
                if ((point = point.nexts[chars[i] - 'a']) == null) {
                    return 0;
                }
            }
            return point.pass;
        }

        //删除
        public void delete(String word) {
            //没有，不删
            if (search(word) == 0) {
                return;
            }
            char[] chars = word.toCharArray();
            Node1 point = root;
            for (int i = 0; i < word.length(); i++) {
                //==1的时候，本次减完就该删了
                if (point.nexts[chars[i] - 'a'].pass == 1) {
                    point.nexts[chars[i] - 'a'] = null;
                    return;
                }
                point = point.nexts[chars[i] - 'a'];
                point.pass--;
            }
            point.end--;
        }
    }

    public static class Node2<T> {
        public int pass;
        public int end;
        public HashMap<T, Node2> nexts;//哈希表写法，针对出现元素较多的场景

        public Node2() {
            pass = 0;
            end = 0;
            nexts = new HashMap<>();
        }
    }

    public static class Trie2 {
        Node2<Character> root;

        public Trie2() {
            this.root = new Node2();
        }

        //添加
        public void insert(String word) {
            if (word == null) {
                return;
            }
            char[] chars = word.toCharArray();
            Node2<Character> point = root;
            point.pass++;//根节点先加一
            for (int i = 0; i < word.length(); i++) {
                //第几个
                if (point.nexts.get(chars[i]) == null) {
                    point.nexts.put(chars[i], new Node2());
                }
                point = point.nexts.get(chars[i]);
                point.pass++;//经过数+1
            }
            point.end++;//以改节点结尾+1
        }

        //查询一个字符串出现了几次
        public int search(String word) {
            if (word == null) {
                return 0;
            }
            char[] chars = word.toCharArray();
            Node2<Character> point = root;
            if (point.pass <= 0) {
                return 0;
            }
            for (int i = 0; i < word.length(); i++) {
                //第几个
                if ((point = point.nexts.get(chars[i])) == null) {
                    return 0;
                }
            }
            return point.end;
        }

        //查询以指定字符串为前缀的开头的字符串的个数
        public int prefix(String word) {
            if (word == null) {
                return 0;
            }
            char[] chars = word.toCharArray();
            Node2<Character> point = root;
            if (point.pass <= 0) {
                return 0;
            }
            for (int i = 0; i < word.length(); i++) {
                //第几个
                if ((point = point.nexts.get(chars[i])) == null) {
                    return 0;
                }
            }
            return point.pass;
        }

        //删除
        public void delete(String word) {
            //没有，不删
            if (search(word) == 0) {
                return;
            }
            char[] chars = word.toCharArray();
            Node2<Character> point = root;
            for (int i = 0; i < word.length(); i++) {
                //==1的时候，本次减完就该删了
                if (point.nexts.get(chars[i]).pass == 1) {
                    point.nexts.remove(chars[i]);
                    return;
                }
                point = point.nexts.get(chars[i]);
                point.pass--;
            }
            point.end--;
        }
    }

    public static void main(String[] args) {
        //添加
        //查询一个字符串出现了几次
        //查询以指定字符串为前缀的开头的字符串的个数
        //删除
//        Trie2 trie2 = new Trie2();
//        trie2.insert("abc");
//        trie2.insert("abc");
//        trie2.insert("abd");
//        trie2.insert("ace");
//        System.out.println(trie2.search("abc"));
//        System.out.println(trie2.search("ace"));
//        System.out.println(trie2.search("111"));
//        trie2.delete("abc");
//        System.out.println(trie2.search("abc"));
//        System.out.println(trie2.prefix("a"));


        Trie1 trie1 = new Trie1();
        trie1.insert("abc");
        trie1.insert("abc");
        trie1.insert("abd");
        trie1.insert("ace");
        System.out.println(trie1.search("abc"));
        System.out.println(trie1.search("ace"));
        System.out.println(trie1.search("xxx"));
        trie1.delete("abc");
        System.out.println(trie1.search("abc"));
        System.out.println(trie1.prefix("a"));
    }


}
