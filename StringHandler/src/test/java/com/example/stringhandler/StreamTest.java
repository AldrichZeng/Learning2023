package com.example.stringhandler;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;

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
        inner(1, (A) null);
    }

    private void inner(int n, A a) {
        System.out.println("inner 1");
    }

    @Test
    public void test4() {
        String a = "{\n"
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
        public String toString() {
            return "my to string: domain:" + domain + ",product:" + product;
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

    @Test
    public void test111() {
        String a = "abc.abc";
        System.out.println(a.contains("\\."));
    }

    @Test
    public void test112() {
        List<SchemaInfo> schemaInfos = new ArrayList<>();
        schemaInfos.add(new SchemaInfo("a"));
        schemaInfos.add(new SchemaInfo("b"));

        System.out.println(schemaInfos.contains(new SchemaInfo("a")));
        schemaInfos.remove(new SchemaInfo("a"));

        System.out.println(schemaInfos.size());
        schemaInfos.stream().forEach(System.out::println);
    }

    public static class SchemaInfo {
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        String name;

        public SchemaInfo(String name) {
            this.name = name;
        }

        public boolean equals(Object obj) {
            if (obj != null) {
                SchemaInfo schemaInfo = (SchemaInfo) obj;
                return schemaInfo.getName().equals(this.getName());
            } else {
                return false;
            }
        }

        public String toString(){
            return this.name;
        }
    }

    @Test
    public void test113(){

    }
}
