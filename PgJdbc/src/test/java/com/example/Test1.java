package com.example;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * @author 匠承
 * @Date: 2023/10/23 10:14
 */
public class Test1 {

    @Test
    public void test1(){
        List<String> list = new ArrayList<>();

        list.add("abc");
        list.add("xyz");
        System.out.println(list.size());
        list.clear();
        System.out.println(list.size());
    }
}
