package com.example.pgjdbc;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;

import org.apache.commons.lang3.time.FastDateFormat;
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

    @Test
    public void test4(){
        String a = "ROLE_1234";
        System.out.println(a.substring(5));
    }

    @Test
    public void test5(){
        Long timestamp = 1691825938458L;
        //Date date = new Date(timestamp);
        //SimpleDateFormat  dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //System.out.println(dateFormat.format(date));
        System.out.println(FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss").format(timestamp));
    }

    @Test
    public void test6(){
        Long a = 123L;
        System.out.println(String.format("abc %s", a));
    }
}
