package com.example;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

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

    @Test
    public void test2(){
        String str = "${srcSchemaName}";
        String key = "srcSchemaName";
        String value = null;
        str = str.replaceAll("\\$\\{" + key + "\\}", value);
        System.out.println(str);
    }

    @Test
    public void test3(){
        JSONObject injectedResult = new JSONObject();
        System.out.println(injectedResult);
        injectedResult.put("a", "b");
        System.out.println(injectedResult);
        injectedResult.putAll(null);
        System.out.println(injectedResult);

    }

    @Test
    public void test4(){
        for(int i=0;i<2000;i++){
            System.out.println("create table test"+i+" (id int);");
        }
    }
}
