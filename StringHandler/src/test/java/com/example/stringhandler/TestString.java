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

import org.apache.commons.lang3.RandomUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author 匠承
 * @Date: 2023/5/3 22:29
 */
public class TestString {
    @Test
    public void test1() throws IOException {
        //CSVReader parser = CSVReader.of("1.e-1,-123456789,10000.12345566,-3123123123123123123123123123123.0,4.abc,5,127.0.0.1,,abc,2021-08-27 15:30:00,2021-08-27,2021-08-27 15:30:00.001");
        CSVReader parser = CSVReader.of("2021-08-27 15:30:00\n2021-08-27 15:30:00.001");
        //parser.readHeader();

        parser.statAll(100);

        List<ColumnStat> columns = parser.getColumnStats();

        for (ColumnStat columnStat : columns) {
            System.out.println(columnStat.getInferSQLType());
            System.out.println(columnStat.getInferType());
        }
    }

    @Test
    public void test11() {
        for (int i = 0; i < 2000; i++) {
            System.out.print("a");
        }
    }

    @Test
    public void test2() {
        //-?表示可能有一个负号，d+表示有一个或多个数字，因此整个正则表达式的含义是：一个可选的负号，后面跟着一个或多个数字。
        //Pattern pattern = Pattern.compile("-?\\d+");
        Pattern pattern = Pattern.compile("-?[0-9]{1,9}");

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

        Assert.assertFalse(pattern.matcher("12345678900").matches());
        Assert.assertTrue(pattern.matcher("-1234567890").matches());
    }

    @Test
    public void testBigInt() {
        Pattern pattern = Pattern.compile("-?[0-9]{11,20}");
        Assert.assertFalse(pattern.matcher("123").matches());
        Assert.assertFalse(pattern.matcher("-123").matches());
        Assert.assertTrue(pattern.matcher("12345678900").matches());
        Assert.assertFalse(pattern.matcher("1234567890").matches());
        Assert.assertTrue(pattern.matcher("-123456789012345678").matches());
    }

    @Test
    public void testDecimal() {
        Pattern pattern1 = Pattern.compile("-?[0-9]{21,}");
        Pattern pattern2 = Pattern.compile("-?[0-9]{1,20}(\\.[0-9]{0,4})?");
        Pattern pattern3 = Pattern.compile("-?(\\d{1,20}|\\d+\\.\\d{0,4})?");

        Assert.assertFalse(pattern1.matcher("123.4").matches());
        Assert.assertTrue(pattern2.matcher("123.4").matches());
        Assert.assertTrue(pattern3.matcher("123.4").matches());
        Assert.assertFalse(pattern1.matcher("-123.4").matches());
        Assert.assertTrue(pattern2.matcher("-123.4").matches());
        Assert.assertTrue(pattern3.matcher("-123.4").matches());

        Assert.assertFalse(pattern1.matcher("123.123456").matches());
        Assert.assertFalse(pattern2.matcher("123.123456").matches());
        Assert.assertFalse(pattern3.matcher("123.123456").matches());
        Assert.assertFalse(pattern1.matcher("-123.123456").matches());
        Assert.assertFalse(pattern2.matcher("-123.123456").matches());
        Assert.assertFalse(pattern3.matcher("-123.123456").matches());

        Assert.assertFalse(pattern1.matcher("123.").matches());
        Assert.assertTrue(pattern2.matcher("123.").matches());
        Assert.assertTrue(pattern3.matcher("123.").matches());
        Assert.assertFalse(pattern1.matcher("-123.").matches());
        Assert.assertTrue(pattern2.matcher("-123.").matches());
        Assert.assertTrue(pattern3.matcher("-123.").matches());

        Assert.assertFalse(pattern1.matcher("-12345678912345678900").matches());
        Assert.assertTrue(pattern2.matcher("-12345678912345678900").matches());
        Assert.assertTrue(pattern3.matcher("-12345678912345678900").matches());
        Assert.assertFalse(pattern1.matcher("12345678912345678900").matches());
        Assert.assertTrue(pattern2.matcher("12345678912345678900").matches());
        Assert.assertTrue(pattern3.matcher("12345678912345678900").matches());
    }

    @Test
    public void testDecimal2() {
        Pattern pattern = Pattern.compile("-?(\\d{21,}|\\d+\\.\\d{0,4})?");
        Assert.assertTrue(pattern.matcher("123.4").matches());
        Assert.assertTrue(pattern.matcher("123.4").matches());
        Assert.assertTrue(pattern.matcher("123456789123456789000").matches());
        Assert.assertTrue(pattern.matcher("-123456789123456789000").matches());
        Assert.assertTrue(pattern.matcher("123.").matches());
        Assert.assertTrue(pattern.matcher("-123.").matches());

        Assert.assertFalse(pattern.matcher("12345678912345678900").matches());
        Assert.assertFalse(pattern.matcher("-12345678912345678900").matches());
        Assert.assertFalse(pattern.matcher("123.123456").matches());
        Assert.assertFalse(pattern.matcher("-123.123456").matches());

        String data = "123.4";
        if (data.contains(".")) {
            int decimalNum = data.split("\\.")[1].length();
            int totalNum = data.replace(".", "").replace("-", "").length();
            System.out.println(totalNum);
            System.out.println(decimalNum);
        } else {

        }
    }

    @Test
    public void testDOUBLE() {
        Pattern pattern = Pattern.compile("(-?[0-9]+(\\.[0-9]{5,})?)|(^[-+]?\\d+\\.?(\\d+)?[eE][-+]?\\d+$)");
        Assert.assertFalse(pattern.matcher("123.4").matches());
        Assert.assertFalse(pattern.matcher("123.4").matches());
        Assert.assertTrue(pattern.matcher("123456789123456789000").matches());
        Assert.assertTrue(pattern.matcher("-123456789123456789000").matches());
        Assert.assertFalse(pattern.matcher("123.").matches());
        Assert.assertFalse(pattern.matcher("-123.").matches());

        Assert.assertTrue(pattern.matcher("123.123456").matches());
        Assert.assertTrue(pattern.matcher("0.123456").matches());
        Assert.assertTrue(pattern.matcher("-123.123456").matches());
        Assert.assertTrue(pattern.matcher("-0.123456").matches());

        Assert.assertTrue(pattern.matcher("1e-4").matches());
        Assert.assertTrue(pattern.matcher("1.0e-4").matches());
        Assert.assertTrue(pattern.matcher("1.0e-123456").matches());
        Assert.assertTrue(pattern.matcher("-0.1e-123456").matches());
        Assert.assertTrue(pattern.matcher("-1.e-123456").matches());
        Assert.assertFalse(pattern.matcher("-0.1e-123.456").matches());
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
        Pattern pattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}( \\d{2}:\\d{2}:\\d{2})?");
        Assert.assertTrue(pattern.matcher("2021-08-27 15:30:00").matches());
        Assert.assertTrue(pattern.matcher("2021-08-27").matches());
        Assert.assertFalse(pattern.matcher("2021-08-27 ").matches());
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
    public void testTime() {
        Pattern pattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}.\\d+");
        Assert.assertTrue(pattern.matcher("2021-08-27 15:30:00.001").matches());
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
    public void test8() {
        String str1 = "a,b,c,d";
        Set<String> set1 = new HashSet<>();
        set1.addAll(Arrays.asList(str1.split(",")));
        System.out.println(set1);

        String str2 = "a,b,";
        Set<String> set2 = new HashSet<>();
        set2.addAll(Arrays.asList(str2.split(",")));
        System.out.println(set2);

        String str3 = "";
        Set<String> set3 = new HashSet<>();
        set3.addAll(Arrays.asList(str3.split(",")));
        System.out.println(set3);

        String str4 = "a,b,x  y";
        Set<String> set4 = new HashSet<>();
        set4.addAll(Arrays.asList(str4.split(",")));
        System.out.println(set4);

        String str5 = "a";
        Set<String> set5 = new HashSet<>();
        set5.addAll(Arrays.asList(str5.split(",")));
        System.out.println(set5);
    }

    @Test
    public void test9() {
        System.out.println("\\x0A\\x09at");
    }

    @Test
    public void test10() {
        System.out.println(JSON.parseObject("{'$path':'article/1227327','$title':'超实用技巧之手机桌面小鹏App组件'}"));

        System.out.println(JSON.parseObject("{'$path':'/article/1227327','$title':'\\\\超\\\\实用技巧之手机桌面小鹏App组件'}"));
    }

    @Test
    public void test() {
        A x = A.a;
        A y = A.a;
        System.out.println(x == y);

        x.setX(90);
        y.setX(80);
        System.out.println(x == y);
    }

    @Test
    public void test100() {
        Pattern pattern = Pattern.compile("^(?!_)[a-zA-Z_]+[0-9a-zA-Z_]*");
        Assert.assertTrue(pattern.matcher("couunt").matches());
        Assert.assertFalse(pattern.matcher("_couunt").matches());
        Assert.assertTrue(pattern.matcher("a_123").matches());
    }

    public enum A {
        a(0),
        b(1);

        A(int x) {
            this.x = x;
        }

        private int x;

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }
    }

    public enum Color{
        RED,
        BLUE
    }

    @Test
    public void testEnum(){
        String a = "RED";
        if(Color.RED.equals(a)){
            System.out.println("xyz");
        }
    }
}

