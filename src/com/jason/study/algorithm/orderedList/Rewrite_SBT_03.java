package com.jason.study.algorithm.orderedList;

import java.util.ArrayList;

/**
 * 设计一个结构包含如下两个方法：
 * void add(int index, int num)：把num加入到index位置
 * int get(int index) ：取出index位置的值
 * void remove(int index) ：把index位置上的值删除
 * 要求三个方法时间复杂度O(logN)
 * --节点上放，这个子树之前放过多少个节点（子树内的最大自然时序）
 * --当前位置的自然时序=左.子树的val + 1
 * --会把我往后挤，就往左放，不挤我，就往右放，往右走的时候记得减去（左子树的数量+1）
 * --平衡因子size也参与业务
 *
 * @author JasonC5
 */
public class Rewrite_SBT_03 {

    public static class SBTNode<T> {
        public T value;
        public SBTNode<T> l;
        public SBTNode<T> r;
        public int size;

        public SBTNode(T value) {
            this.value = value;
            size = 1;
        }
    }

    private static class SbtList<T> {
        SBTNode<T> root;


        public Integer size() {
            return root == null ? 0 : root.size;
        }


        public void add(int index, T value) {
            SBTNode<T> cur = new SBTNode<T>(value);
            if (root == null) {
                root = cur;
            } else {
                root = add(root, index, cur);
            }
        }

        private SBTNode<T> maintain(SBTNode<T> node) {
            if (node == null) {
                return null;
            }
            int l = node.l == null ? 0 : node.l.size;
            int r = node.r == null ? 0 : node.r.size;
            int ll = node.l == null || node.l.l == null ? 0 : node.l.l.size;
            int lr = node.l == null || node.l.r == null ? 0 : node.l.r.size;
            int rl = node.r == null || node.r.l == null ? 0 : node.r.l.size;
            int rr = node.r == null || node.r.r == null ? 0 : node.r.r.size;
            if (ll > r) {
                node = rightRotate(node);
                node.r = maintain(node.r);
                node = maintain(node);
            } else if (lr > r) {
                node.l = leftRotate(node.l);
                node = rightRotate(node);
                node.l = maintain(node.l);
                node.r = maintain(node.r);
                node = maintain(node);
            } else if (rl > l) {
                node.r = rightRotate(node.r);
                node = leftRotate(node);
                node.l = maintain(node.l);
                node.r = maintain(node.r);
                node = maintain(node);
            } else if (rr > l) {
                node = leftRotate(node);
                node.l = maintain(node.l);
                node = maintain(node);
            }
            return node;
        }

        private SBTNode<T> leftRotate(SBTNode<T> node) {
            SBTNode<T> next = node.r;
            node.r = next.l;
            next.l = node;
            next.size = node.size;
            node.size = (node.l == null ? 0 : node.l.size) + (node.r == null ? 0 : node.r.size) + 1;
            return next;
        }

        private SBTNode<T> rightRotate(SBTNode<T> node) {
            SBTNode<T> next = node.l;
            node.l = next.r;
            next.r = node;
            next.size = node.size;
            node.size = (node.l == null ? 0 : node.l.size) + (node.r == null ? 0 : node.r.size) + 1;
            return next;
        }

        private SBTNode<T> add(SBTNode<T> cur, int index, SBTNode<T> adder) {
            if (cur == null) {
                return adder;
            }
            cur.size++;
            int leftSize = cur.l == null ? 0 : cur.l.size;
            if (index <= leftSize) {
                //往左树上挂
                cur.l = add(cur.l, index, adder);
            } else {
                //往右树上挂
                cur.r = add(cur.r, index - leftSize - 1, adder);
            }
            return maintain(cur);
        }

        public void remove(Integer index) {
            if (index >= 0 && index < size()) {
                root = remove(root, index);
            }
        }

        private SBTNode<T> remove(SBTNode<T> cur, Integer index) {
            cur.size--;
            int leftSize = cur.l == null ? 0 : cur.l.size;
            //index是从第0个开始的，删除第3个，其实就是第四个，
            if (index < leftSize) {
                cur.l = remove(cur.l, index);
                return cur;
            } else if (index > leftSize) {
                cur.r = remove(cur.r, index - leftSize - 1);
                return cur;
            }
            //else == ,删除当前节点
            if (cur.l == null && cur.r == null) {
                return null;
            } else if (cur.l == null || cur.r == null) {
                return cur.l == null ? cur.r : cur.l;
            } else {
                //右树的最左节点来替
                SBTNode<T> pre = null;
                SBTNode<T> next = cur.r;
                next.size--;//要从这个子树上删掉一个节点
                while (next.l != null) {
                    pre = next;
                    next = next.l;
                    next.size--;
                }
                //右树的最左子树，不一定是叶子节点，
                if (pre != null) {
                    pre.l = next.r;
                    //不能放下面，否则就相互引用了，导致死循环
                    next.r = cur.r;
                }
                next.l = cur.l;
                next.size = cur.size;//next.size = (next.l == null ? 0:next.l.size )+(next.r == null ? 0:next.r.size)+1
//                cur = next;
                return next;
            }
        }

        public T get(int index) {
            if (index >= 0 && index < size()) {
                SBTNode<T> node = get(root, index);
                return node.value;
            } else {
                return null;
            }
        }

        private SBTNode<T> get(SBTNode<T> cur, int index) {
            int leftSize = cur.l == null ? 0 : cur.l.size;
            if (index < leftSize) {
                return get(cur.l, index);
            } else if (index > leftSize) {
                return get(cur.r, index - leftSize - 1);
            } else {
                return cur;
            }
        }

    }

    // 通过以下这个测试，
    // 可以很明显的看到LinkedList的插入、删除、get效率不如SbtList
    // LinkedList需要找到index所在的位置之后才能插入或者读取，时间复杂度O(N)
    // SbtList是平衡搜索二叉树，所以插入或者读取时间复杂度都是O(logN)
    public static void main(String[] args) {
        // 功能测试
        int test = 50000;
        int max = 1000000;
        boolean pass = true;
        ArrayList<Integer> list = new ArrayList<>();
        SbtList<Integer> sbtList = new SbtList<>();
        for (int i = 0; i < test; i++) {
            if (list.size() != sbtList.size()) {
                pass = false;
                break;
            }
            if (list.size() > 1 && Math.random() < 0.5) {
                int removeIndex = (int) (Math.random() * list.size());
                list.remove(removeIndex);
                sbtList.remove(removeIndex);
            } else {
                int randomIndex = (int) (Math.random() * (list.size() + 1));
                int randomValue = (int) (Math.random() * (max + 1));
                list.add(randomIndex, randomValue);
                sbtList.add(randomIndex, randomValue);
            }
        }
        for (int i = 0; i < list.size(); i++) {
            if (!list.get(i).equals(sbtList.get(i))) {
                pass = false;
                break;
            }
        }
        System.out.println("pass : " + pass);

//        // 性能测试
//        test = 500000;
//        list = new ArrayList<>();
//        sbtList = new SbtList<>();
//        long start = 0;
//        long end = 0;
//
//        start = System.currentTimeMillis();
//        for (int i = 0; i < test; i++) {
//            int randomIndex = (int) (Math.random() * (list.size() + 1));
//            int randomValue = (int) (Math.random() * (max + 1));
//            list.add(randomIndex, randomValue);
//        }
//        end = System.currentTimeMillis();
//        System.out.println("ArrayList插入总时长(毫秒) ： " + (end - start));
//
//        start = System.currentTimeMillis();
//        for (int i = 0; i < test; i++) {
//            int randomIndex = (int) (Math.random() * (i + 1));
//            list.get(randomIndex);
//        }
//        end = System.currentTimeMillis();
//        System.out.println("ArrayList读取总时长(毫秒) : " + (end - start));
//
//        start = System.currentTimeMillis();
//        for (int i = 0; i < test; i++) {
//            int randomIndex = (int) (Math.random() * list.size());
//            list.remove(randomIndex);
//        }
//        end = System.currentTimeMillis();
//        System.out.println("ArrayList删除总时长(毫秒) : " + (end - start));
//
//        start = System.currentTimeMillis();
//        for (int i = 0; i < test; i++) {
//            int randomIndex = (int) (Math.random() * (sbtList.size() + 1));
//            int randomValue = (int) (Math.random() * (max + 1));
//            sbtList.add(randomIndex, randomValue);
//        }
//        end = System.currentTimeMillis();
//        System.out.println("SbtList插入总时长(毫秒) : " + (end - start));
//
//        start = System.currentTimeMillis();
//        for (int i = 0; i < test; i++) {
//            int randomIndex = (int) (Math.random() * (i + 1));
//            sbtList.get(randomIndex);
//        }
//        end = System.currentTimeMillis();
//        System.out.println("SbtList读取总时长(毫秒) :  " + (end - start));
//
//        start = System.currentTimeMillis();
//        for (int i = 0; i < test; i++) {
//            int randomIndex = (int) (Math.random() * sbtList.size());
//            sbtList.remove(randomIndex);
//        }
//        end = System.currentTimeMillis();
//        System.out.println("SbtList删除总时长(毫秒) :  " + (end - start));

    }

}
