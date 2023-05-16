package com.example.stringhandler;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Pattern;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.stream.StreamReader.ColumnStat;
import com.alibaba.fastjson2.support.csv.CSVReader;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author 匠承
 * @Date: 2023/5/3 22:29
 */
public class TestString {
    @Test
    public void test1() throws IOException {
        CSVReader parser = CSVReader.of("-123456789,10000.12345566,-3123123123123123123123123123123.0,4.abc,5,127.0.0.1,,abc,2021-08-27 15:30:00,2021-08-27,2021-08-27 15:30:00.001");
        //parser.readHeader();

        parser.statAll(100);

        List<ColumnStat> columns = parser.getColumnStats();

        for (ColumnStat columnStat : columns) {
            System.out.println(columnStat.getInferSQLType());
            System.out.println(columnStat.getInferType());
        }
    }

    @Test
    public void test11(){

    }

    @Test
    public void test2() {
        //-?表示可能有一个负号，d+表示有一个或多个数字，因此整个正则表达式的含义是：一个可选的负号，后面跟着一个或多个数字。
        Pattern pattern = Pattern.compile("-?\\d+");

        Assert.assertTrue(pattern.matcher("123").matches());
        Assert.assertTrue(pattern.matcher("-123").matches());
        Assert.assertTrue(pattern.matcher("0").matches());
        Assert.assertTrue(pattern.matcher("100").matches());
        Assert.assertTrue(pattern.matcher("000").matches());

        Assert.assertFalse(pattern.matcher("12a3").matches());
        Assert.assertFalse(pattern.matcher("-12a3").matches());
        Assert.assertFalse(pattern.matcher("12a.3").matches());

        Assert.assertFalse(pattern.matcher("123.4").matches());
        Assert.assertFalse(pattern.matcher("-123.4").matches());
    }

    @Test
    public void test3() {
        //-?表示可能有一个负号，d+表示有一个或多个数字，(.d+)?表示可选的小数部分，因此整个正则表达式的含义是：一个可选的负号，后面跟着一个或多个数字，然后可能有一个小数点和一个或多个数字。
        Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

        Assert.assertTrue(pattern.matcher("123.45").matches());
        Assert.assertTrue(pattern.matcher("123").matches());
        Assert.assertTrue(pattern.matcher("123.0").matches());
        Assert.assertTrue(pattern.matcher("123.0").matches());
        Assert.assertTrue(pattern.matcher("0.001").matches());
        Assert.assertTrue(pattern.matcher("-0.001").matches());
        Assert.assertTrue(pattern.matcher("0.000").matches());
        Assert.assertTrue(pattern.matcher("-0.000").matches());
        Assert.assertTrue(pattern.matcher("-0.0").matches());

        Assert.assertFalse(pattern.matcher("12a3.45").matches());
        Assert.assertFalse(pattern.matcher("123.").matches());
        Assert.assertFalse(pattern.matcher("abc").matches());
        Assert.assertFalse(pattern.matcher("123.abc").matches());
    }

    @Test
    public void test4() {
        //d{4}表示4个数字表示的年份，-d{2}-表示两个数字表示的月份和日份，d{2}:d{2}:d{2}表示两个数字表示的小时、分钟和秒数，因此整个正则表达式的含义是：一个年份、一个中划线、两个数字表示的月份、一个中划线、两个数字表示的日份、一个空格、两个数字表示的小时、一个冒号、两个数字表示的分钟、一个冒号、两个数字表示的秒数。
        Pattern pattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}");
        Assert.assertTrue(pattern.matcher("2021-08-27 15:30:00").matches());
        Assert.assertFalse(pattern.matcher("2021/08/27 15:30:00").matches());
    }

    @Test
    public void test5() {
        Pattern pattern = Pattern.compile("\\d{4}/\\d{2}/\\d{2} \\d{2}:\\d{2}:\\d{2}");
        Assert.assertTrue(pattern.matcher("2021/08/27 15:30:00").matches());
        Assert.assertFalse(pattern.matcher("2021-08-27 15:30:00").matches());
    }

    @Test
    public void test6() {
        Pattern pattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
        Assert.assertTrue(pattern.matcher("2021-08-27").matches());
        Assert.assertFalse(pattern.matcher("2021-08-27 15:30:00").matches());
        Assert.assertFalse(pattern.matcher("2021/08/27").matches());
    }

    @Test
    public void test7() {
        // bigint
        Pattern patternInt = Pattern.compile("-?\\d+");
        // double
        Pattern patternDouble = Pattern.compile("-?\\d+(\\.\\d+)?");
        // date
        Pattern patternDate1 = Pattern.compile("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}");
        Pattern patternDate2 = Pattern.compile("\\d{4}/\\d{2}/\\d{2} \\d{2}:\\d{2}:\\d{2}");
        Pattern patternDate3 = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
        Map<Pattern, String> map = new HashMap<>();
        map.put(patternInt, "BIGINT");
        map.put(patternDouble, "DOUBLE");
        map.put(patternDate1, "DATE");
        map.put(patternDate2, "DATE");
        map.put(patternDate3, "DATE");
        //String input = "abc";
        String input = "123.9";
        for (Entry<Pattern, String> entry : map.entrySet()) {
            Pattern pattern = entry.getKey();
            if (pattern.matcher(input).matches()) {
                System.out.println(entry.getValue());
                break;
            }
        }
        System.out.println("STRING");
    }

    @Test
    public void test8(){
        String str1 = "a,b,c,d";
        Set<String> set1= new HashSet<>();
        set1.addAll(Arrays.asList(str1.split(",")));
        System.out.println(set1);

        String str2 = "a,b,";
        Set<String> set2= new HashSet<>();
        set2.addAll(Arrays.asList(str2.split(",")));
        System.out.println(set2);

        String str3 = "";
        Set<String> set3= new HashSet<>();
        set3.addAll(Arrays.asList(str3.split(",")));
        System.out.println(set3);

        String str4 = "a,b,x  y";
        Set<String> set4= new HashSet<>();
        set4.addAll(Arrays.asList(str4.split(",")));
        System.out.println(set4);

        String str5 = "a";
        Set<String> set5= new HashSet<>();
        set5.addAll(Arrays.asList(str5.split(",")));
        System.out.println(set5);
    }

    @Test
    public void test9(){
        System.out.println("\\x0A\\x09at");
    }

    @Test
    public void test10(){
        System.out.println(JSON.parseObject("{'$path':'article/1227327','$title':'超实用技巧之手机桌面小鹏App组件'}"));

        System.out.println(JSON.parseObject("{'$path':'/article/1227327','$title':'\\\\超\\\\实用技巧之手机桌面小鹏App组件'}"));
    }
}

