package com.example.stocker;

import androidx.annotation.NonNull;

import com.example.stocker.ui.fragLibrary.chartsFragment;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    public static void main(String[] args) {
  /*      Person[] ps = new Person[3]; // 声明一个对象数组，里面有三个对象
        System.out.println("---------------数组声明后-------------------");
        for (Person person : ps) {
            System.out.print(person + " "); // 循环输出
        }
        // 创建3个人，并实例化
        ArrayList<String> stringList = new ArrayList<String>();
        assert false;
        for (int i = 0; i < 3; i++) {
            stringList.add("first");
        }
        for (int i = 0; i < 3; i++) {
            Person person = new Person(stringList.get(i));
            ps[i] = person;
        }
        System.out.println("\n---------------对象实例化-------------------");
        for (Person p : ps) {
            System.out.println(p.name);
        }

        Object[] x = new Object[]{
                "Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"
        };
        System.out.println(x.length);*/

//  此处用来存放-
        List<Map<String, Object>> listMaps = new ArrayList<Map<String, Object>>();

        Map<String, Object> myMap = new HashMap<String, Object>();
        myMap.put("1", "a");
        myMap.put("2", "b");
        myMap.put("3", "c");
        listMaps.add(myMap);

        Map<String, Object> map2 = new HashMap<String, Object>();
        map2.put("11", "aa");
        map2.put("22", "bb");
        map2.put("33", "cc");
        listMaps.add(map2);

        for (Map<String, Object> map : listMaps) {
            for (String s : map.keySet()) {
                System.out.print(map.get(s) + "  ");
            }
        }
        System.out.println();
        System.out.println("========================");
        for (int i = 0; i < listMaps.size(); i++) {
            Map<String, Object> map = listMaps.get(i);
            for (String string : map.keySet()) {
                System.out.println(map.get(string));
            }
        }
        System.out.println("++++++++++++++++++++++++++++");
        for (Map<String, Object> map : listMaps) {
            for (Map.Entry<String, Object> m : map.entrySet()) {
//                键
                System.out.print(m.getKey() + "    ");
//                值
                System.out.println(m.getValue());
            }
        }
        System.out.println("-----------------------------");
    }
}


class Person {
    public String name;

    public Person(String name) { // 通过构造方法设置内容
        this.name = name; // 为姓名赋值
    }
}