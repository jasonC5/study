package com.jason.study.algorithm.sort;

import java.lang.reflect.Array;
import java.util.*;

/**
 * @author JasonC5
 */
public class ComparatorTest {

    public static void main(String[] args) {
//        List<Person>
//        Person p1 = new Person(1, 30, "张三");
//        Person p2 = new Person(3, 20, "张三2");
//        Person p3 = new Person(2, 10, "张三3");

//        Integer[] arr = { 5, 4, 3, 2, 7, 9, 1, 0 };
//        Arrays.sort(arr,(a,b)->a-b);
//        for (int i = 0; i < arr.length; i++) {
//            System.out.println(arr[i]);
//        }

//        List<ComparablePerson> comparablePersonList = new ArrayList<>();
//        comparablePersonList.add(new ComparablePerson(1, 30, "zhangsan"));
//        comparablePersonList.add(new ComparablePerson(3, 20, "zhangsan1"));
//        comparablePersonList.add(new ComparablePerson(2, 10, "zhangsan2"));
//        comparablePersonList.sort(new PersonComparator());
//        printComparablePerson(comparablePersonList);

//        TreeMap treeMap = new TreeMap();
        TreeMap<ComparablePerson,Integer> treeMap = new TreeMap<ComparablePerson,Integer>((a,b)->a.age - b.age);
        treeMap.put(new ComparablePerson(1, 30, "zhangsan"),1);
        treeMap.put(new ComparablePerson(3, 20, "zhangsan1"),2);
        treeMap.put(new ComparablePerson(2, 10, "zhangsan2"),3);
        printComparablePerson(treeMap.keySet());

    }

    private static void printComparablePerson(Collection<ComparablePerson> comparablePersonList) {
        comparablePersonList.forEach(a -> System.out.println("id:" + a.id + "\t age:" + a.age + "\t name:" + a.name));
    }


    public static class Person {
        int id;
        int age;
        String name;

        public Person() {
        }

        public Person(int id, int age, String name) {
            this.id = id;
            this.age = age;
            this.name = name;
        }
    }


    public static class ComparablePerson extends Person implements Comparable<Person> {
        public ComparablePerson() {
        }

        public ComparablePerson(int id, int age, String name) {
            super(id, age, name);
        }


        @Override
        public int compareTo(Person o) {
            return this.id - o.id;
        }
    }

    public static class PersonComparator implements Comparator<Person> {

        @Override
        public int compare(Person o1, Person o2) {
            return o1.id - o2.id;
        }
    }

}
