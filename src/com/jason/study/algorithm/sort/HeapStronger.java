package com.jason.study.algorithm.sort;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * 加强堆--T需要是非基础类型
 * 线程不安全--尝试改成线程安全的？
 *
 * @author JasonC5
 */
public class HeapStronger<T> {
    //实现加强堆需要的数据结构

    //小根堆【或者大根堆，根据比较器而定】
    private ArrayList<T> heap;
    //反向索引
    private HashMap<T, Integer> indexMap;
    //堆大小
    private int heapSize;
    //比较器
    private Comparator<? super T> comparator;

    public HeapStronger(Comparator<? super T> comparator) {
        heap = new ArrayList<>();
        indexMap = new HashMap<>();
        heapSize = 0;
        this.comparator = comparator;
    }

    //是否为空
    public boolean isEmpty() {
        return heapSize == 0;
    }

    //大小
    public int size() {
        return heapSize;
    }

    //是否包含某节点
    public boolean contains(T obj) {
        return indexMap.containsKey(obj);
    }

    //查看堆顶
    public T peek() {
        return heap.get(0);
    }

    //推入
    public void push(T obj) {
        //1.放入堆尾
        //2.向上浮动
        //3.浮动过程中，每次交换，修改交换值中反向索引
        heap.add(obj);
        indexMap.put(obj, heapSize++);
        //指定位置向上浮动
        heapInsert(heapSize - 1);
    }

    private void heapInsert(int index) {
        //如果比自己的父节点小，那么需要交换【小根堆】
        while (comparator.compare(heap.get(index), heap.get((index - 1) >> 1)) < 0) {
            swap(index, (index - 1) >> 1);
            //位置上去了
            index = (index - 1) >> 1;
        }
    }

    //浮动过程中，每次交换，修改交换值中反向索引
    private void swap(int index1, int index2) {
        T t1 = heap.get(index1);
        T t2 = heap.get(index2);
        //交换位置
        heap.set(index1, t2);
        heap.set(index2, t1);
        //交换索引
        indexMap.put(t1, index2);
        indexMap.put(t2, index1);
    }

    //弹出
    public T pop() {
        T top = heap.get(0);
        swap(0, heapSize - 1);
        //删除索引
        indexMap.remove(top);
        //删除元素并收缩size
        heap.remove(--heapSize);
        //将放到堆顶的元素下沉到相应位置
        heapify(0);
        return top;
    }

    private void heapify(int index) {
        int leftIndex = index * 2 + 1;
        //有左孩子的话，就能对比，左孩子都没有了，说明沉到底了
        while (leftIndex < heapSize) {
            //找到左右孩子中最小的，和自己比较，最小的上浮【小根堆】
            int bestIndex = leftIndex + 1 < heapSize && comparator.compare(heap.get(leftIndex + 1), heap.get(leftIndex)) < 0 ? leftIndex + 1 : leftIndex;
            bestIndex = comparator.compare(heap.get(index), heap.get(bestIndex)) < 0 ? index : bestIndex;
            //如果自己不是最小的，需要和最小的进行交换
            if (bestIndex != index) {
                swap(index, bestIndex);
                index = bestIndex;
                leftIndex = index * 2 + 1;
            }
        }
    }

    //删除指定元素，并整理小堆
    public void remove(T obj) {
        Integer index = indexMap.get(obj);
        //先从反向索引中删除
        indexMap.remove(obj);
        //取出最后一个,并收缩容量
        T lastOne = heap.get(--heapSize);
        //把最后一个位置清掉【反正已经拿出来了】
        heap.remove(heapSize);
        if (obj != lastOne){
            //把最后一个元素塞到指定元素上
            heap.set(index,lastOne);
            //修改索引
            indexMap.put(lastOne, index);
            //整理堆
            resign(lastOne);
        } else {
            //删的就是你，完事
        }
    }

    //指定元素可能发生变化，整理堆
    public void resign(T obj) {
        Integer index = indexMap.get(obj);
        //只要存在，要么上浮，要么下沉，要么不动
        if (index != null) {
            heapInsert(index);
            heapify(index);
        }
    }

    // 请返回堆上的所有元素
    public List<T> getAllElements() {
        List<T> ans = new ArrayList<>();
        for (T c : heap) {
            ans.add(c);
        }
        return ans;
    }

}
