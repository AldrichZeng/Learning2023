package com.example.stringhandler;

import java.util.List;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;

import org.junit.Test;

/**
 * @author 匠承
 * @Date: 2024/1/1 12:13
 */
public class StreamTest {

    @Test
    public void test() {

        String a = "[\"pgr-uf65l3bwae8w8r35\"]";
        JSONArray arr = JSONArray.parse(a);
        System.out.println(arr);
        List<String> list = arr.toList(String.class);
        System.out.println(list);
    }

    @Test
    public void testNull() {
        inner(1, (A)null);
    }

    private void inner(int n, A a) {
        System.out.println("inner 1");
    }

    @Test
    public void test4(){
        String a= "{\n"
                + "    \"cn-beijing\":\n"
                + "    {\n"
                + "        \"domain\": \"a\",\n"
                + "        \"product\": \"b\"\n"
                + "    }\n"
                + "}";
        B x = JSON.parseObject(a, B.class);
        System.out.println(x);
    }

    public static class B {
        String domain;
        String product;
        @Override
        public String toString(){
            return "my to string: domain:"+domain+",product:"+product;
        }

    }

    private void inner(int n, String s) {
        System.out.println("inner 2");
    }

    public static class A {
        public int x;
        public int y;

        public A() {
            this.x = 1;
            this.y = 2;
        }

        public A(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
