package com.example.pgjdbc;

import java.math.BigDecimal;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author 匠承
 * @Date: 2023/4/18 15:22
 */
public class NormalTest {
    @Test
    public void test() {
        System.out.println(Integer.MAX_VALUE);
        System.out.println(Integer.MIN_VALUE);
    }

    @Test
    public void test0() {
        String jsonStr = "{\"age\":20}";
        JSONObject jsonObject = JSON.parseObject(jsonStr);
    }

    @Test
    public void test1() {
        //DefaultJSONParser defaultJSONParser = new DefaultJSONParser("{\"id\":1,\"name\":\"John\",\"age\":20}");
        DefaultJSONParser defaultJSONParser = new DefaultJSONParser("192.168.0.1");
        Object res = defaultJSONParser.parse(null);
        System.out.println(res);
        Assert.assertEquals(BigDecimal.valueOf(192.168), res);
        System.out.println(res.getClass());
        Assert.assertEquals(java.math.BigDecimal.class, res.getClass());
    }

    @Test
    public void test2() {
        try {
            Double.parseDouble("1.0.0.abc");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Test
    public void test3() {
        String jsonStr = "20";
        JSONObject jsonObject = JSON.parseObject(jsonStr);
    }
}
