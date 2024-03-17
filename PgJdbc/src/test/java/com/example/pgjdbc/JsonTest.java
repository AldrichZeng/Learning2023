package com.example.pgjdbc;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

/**
 * @author 匠承
 * @Date: 2023/4/18 22:42
 */
public class JsonTest {
    @Test
    public void test1() {
        String jsonStr = "{\"age\":20.0.0.abc}";
        JSONObject jsonObject = JSON.parseObject(jsonStr);
    }

    @Test
    public void test2() {
        //DefaultJSONParser defaultJSONParser = new DefaultJSONParser("{\"id\":1,\"name\":\"John\",\"age\":20}");
        DefaultJSONParser defaultJSONParser = new DefaultJSONParser("1.0.0.abc");
        Object res = defaultJSONParser.parse(null);
        System.out.println(res);
        System.out.println(res.getClass());
    }

    @Test
    public void test3() {
        Object a = 1L;
        System.out.println(a.getClass());
    }

    @Test
    public void test4() {
        jsonInference("1234");
        jsonInference("2023-04-19 16:03:03");
        jsonInference("100.1");
        jsonInference("100.2.3");
        jsonInference("abc.def.c.d");
        jsonInference("1.def.c.d");
        jsonInference("1.2.c.d");
    }

    @Test
    public void test6() {
        jsonInference("True");
    }

    private void jsonInference(String data) {
        System.out.println("origin: " + data);
        DefaultJSONParser defaultJSONParser = new DefaultJSONParser(data);
        Object res = defaultJSONParser.parse(null);
        System.out.println("res: " + res);
        System.out.println("type: " + res.getClass());
        System.out.println();
    }

    @Test
    public void test5() {
        System.out.println(StringUtils.isNumeric("abc"));
        System.out.println(StringUtils.isNumeric("123.4"));
        System.out.println(StringUtils.isNumeric("12345"));
        System.out.println(StringUtils.isNumeric("2.3.4"));
        System.out.println(StringUtils.isAlpha("2.3.4"));
        System.out.println(StringUtils.isAlpha("abc"));
        System.out.println(StringUtils.isAlpha("a"));
        System.out.println(StringUtils.isAlpha("ab3"));
    }

    @Test
    public void test7() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append("xyz");
        }
        String str = "1,name1,name1,name1,name1,name1,name1,name1,name1,name1";
        str = str.replace("name", sb.toString());
        for (int i = 1; i <= 9; i++) {
            System.out.println(str.replace("1", String.valueOf(i)));
        }
    }

    @Test
    public void test8() {
        for (int i = 1; i <= 100; i++) {
            System.out.println("drop table if exists public.test_performance_" + i + ";");
        }
    }
}
