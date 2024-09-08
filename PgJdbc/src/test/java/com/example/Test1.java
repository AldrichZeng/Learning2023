package com.example;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.flink.table.data.TimestampData;
import org.junit.Test;

/**
 * @author 匠承
 * @Date: 2023/10/23 10:14
 */
public class Test1 {

    @Test
    public void test1() {
        List<String> list = new ArrayList<>();

        list.add("abc");
        list.add("xyz");
        System.out.println(list.size());
        list.clear();
        System.out.println(list.size());
    }

    @Test
    public void test2() {
        String str = "${srcSchemaName}";
        String key = "srcSchemaName";
        String value = null;
        str = str.replaceAll("\\$\\{" + key + "\\}", value);
        System.out.println(str);
    }

    @Test
    public void test3() {
        JSONObject injectedResult = new JSONObject();
        System.out.println(injectedResult);
        injectedResult.put("a", "b");
        System.out.println(injectedResult);
        injectedResult.putAll(null);
        System.out.println(injectedResult);
    }

    @Test
    public void test4() {
        for (int i = 0; i < 2000; i++) {
            System.out.println("create table myschema.test" + i + " (id int primary key, col2 varchar(100));");
        }
    }

    @Test
    public void test5() {
        for (int i = 1; i < 2000; i++) {
            System.out.println("drop table test" + i + ";");
            System.out.println("create table test" + i + " (id int primary key, col2 varchar(100));");
        }
    }

    @Test
    public void test6() {
        String filePath = "/Users/aldrichzeng/test.sql";
        try {
            for (int i = 101; i <= 1000; i++) {
                for (int j = 11; j <= 100; j++) {
                    String content = "alter table test_mapping_" + i + " add column str" + j + " varchar(100);\n";
                    Files.write(Paths.get(filePath), content.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                }
                System.out.println("File written successfully. for " + i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test7() {
        for (int i = 3; i <= 1000; i++) {
            System.out.println("insert into test_mapping_" + i + " (select * from test_mapping_1);");
        }
    }

    @Test
    public void test8() {
        String a = "[{\"port\":5432.0,\"host\":\"pgm-2zeit4eq73m69d71.pg.rds.aliyuncs.com\"}]";
        JSONArray jsonObject = JSONArray.parseArray(a);
        System.out.println(jsonObject);
    }

    @Test
    public void test9() {
        String a = "a:b";
        JSONArray jsonObject = JSONArray.parseArray(a);
        System.out.println(jsonObject);
    }

    @Test
    public void test10() {
        String oneInputHost = "jdbc:mysql://zengyao-public.mysql.polardb.rds.aliyuncs.com:3306,zengyao-public.mysql.polardb.rds.aliyuncs.com:3306/jiangcheng\n";
        Pattern pattern = Pattern.compile("[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]");
        Matcher matcher = pattern.matcher(oneInputHost);

        while (matcher.find()) {
            String oneUrl = matcher.group();
            if (oneUrl.contains("://")) {
                oneUrl = oneUrl.substring(oneUrl.indexOf("://") + StringUtils.length("://"));
            }

            if (!oneUrl.contains(":")) {
                oneUrl = oneUrl.concat(":8080");
            }

            oneUrl = "http://" + oneUrl;

            System.out.println("oneUrl: " + oneUrl);

            URI uri = URI.create(oneUrl);
            String oneHost = uri.getHost();
            System.out.println(oneHost);
        }
    }

    @Test
    public void test11() {
        Map<String, Object> map = new HashMap<>();
        map.put("a", "b");
        String c = String.valueOf(map.get("c"));
        System.out.println(c);
        System.out.println(c == null);
        System.out.println(StringUtils.isBlank(c));
    }

    @Test
    public void test12() {
        String endpoint = "http://cn-beijing.oss-dls.aliyuncs.com";
        if (StringUtils.isNotBlank(endpoint)) {
            if (endpoint.startsWith("https://")) {
                endpoint = endpoint.substring("https://".length() + 1);
            } else if (endpoint.startsWith("http://")) {
                endpoint = endpoint.substring("http://".length() + 1);
            }
        }
        System.out.println(endpoint);
    }

    /**
     * 经验证，TimestampData.fromEpochMillis和TimestampData.fromInstant结果一样
     */
    @Test
    public void test13() {
        Long timestamp = 1721064696712252L;
        //Instant instant = Instant.ofEpochSecond(1721064515L, 277000000L);
        //System.out.println(instant);
        TimestampData timestampData = TimestampData.fromEpochMillis(timestamp / 1000, (int) (timestamp % 1000 * 1000));
        System.out.println(timestampData);
        System.out.println(timestampData.toInstant());

        Instant instant = Instant.ofEpochSecond(timestamp / 1000000, (int) (timestamp % 1000000 * 1000));
        TimestampData timestampData1 = TimestampData.fromInstant(instant);
        System.out.println(timestampData1);
        System.out.println(timestampData1.toInstant());

        // instant1是UTC的时间戳
        Instant instant1 = Instant.ofEpochSecond(timestamp / 1000000, timestamp % 1000000 * 1000);
        // LocalDateTime不带有时区，参数时区表示用什么时区解释该时间，展示为对应时区的时间戳
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant1, ZoneId.systemDefault());
        System.out.println("localDateTime: " + localDateTime);
        // ZonedDateTime带有时区，给时间赋予一个时区，时间绝对值不变
        ZonedDateTime zdt = localDateTime.atZone(ZoneId.of("UTC"));
        System.out.println("ZonedDateTime: " + zdt);
        long microSec = zdt.toEpochSecond() * 1000 * 1000 + zdt.getNano() / 1000;
        TimestampData timestampData2 = TimestampData.fromLocalDateTime(localDateTime);
        System.out.println("TimestampData2: " + timestampData2);

        long milliSec = zdt.toEpochSecond() * 1000 + zdt.getNano() / 1000;
        TimestampData timestampData3 = TimestampData.fromEpochMillis(
                milliSec, zdt.getNano() % 1000000);

        System.out.println(timestampData3);
        System.out.println(TimestampData.fromInstant(Instant.ofEpochSecond(zdt.toEpochSecond(), zdt.getNano())));
    }

    @Test
    public void test14() {
        String value = "2024-07-15T12:46:58.619796Z";
        String patternString = "^(-|\\+)?\\d{4}-(\\d{2})-(\\d{2})T(\\d{2}):(\\d{2}):(\\d{2})(\\.\\d{6})?Z?$";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(value);
        if (matcher.matches()) {
            System.out.println("true");
        }
    }

    @Test
    public void test15() {
        String value = "2024-07-15T12:46:58.619796Z";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'");
        LocalDateTime ldt = LocalDateTime.parse(value, formatter);

        // LocalDateTime根据时区转换为ZonedDateTime
        // 时区计算是一个复杂的事情，同一个地方不同年份的时区不一样，冬令时和夏令时会导致时区经常变化
        ZonedDateTime zdt = ldt.atZone(ZoneId.of("Asia/Shanghai"));

        // ZonedDateTime可以计算出Instant，Instant包括epochSecond和nano
        Instant instant = zdt.toInstant();

        // java.sql.Timestamp支持从Instant构造
        Timestamp timestamp = Timestamp.from(instant);
        System.out.println(timestamp);
    }

    @Test
    public void test16() {
        //String value = "2024-07-15T12:46:58.619796Z";
        String value = "2024-07-15T12:46:58.619796+08:00";
        ZonedDateTime zdt = ZonedDateTime.parse(value);

        System.out.println("zdt: " + zdt);
        System.out.println(zdt.getOffset());
        LocalDateTime ldt = zdt.toLocalDateTime();
        // 时间的绝对值不变
        System.out.println("localDateTime: " + ldt);

        System.out.println(ldt.atZone(ZoneId.of("Australia/Sydney")).toInstant());

        Instant instant = zdt.toInstant();
        // 带有时区
        System.out.println("instant: " + instant);

        LocalDateTime ldt2 = LocalDateTime.ofInstant(instant, ZoneId.of("Australia/Sydney"));
        System.out.println(ldt2);

        // 以不同时区来展示
        System.out.println("instant.atZone: " + instant.atZone(ZoneId.systemDefault()));
        System.out.println("instant.atZone: " + instant.atZone(ZoneId.of("UTC")));
        Timestamp timestamp = Timestamp.from(instant);
        System.out.println(timestamp);
        System.out.println(timestamp.getTimezoneOffset());
        System.out.println(timestamp.toInstant());
        Timestamp timestamp2 = Timestamp.valueOf(ldt2);
        System.out.println("Timestamp.valueOf(ldt): "+ timestamp2);
        System.out.println(timestamp2.toInstant());
        System.out.println(timestamp2.toLocalDateTime());


    }

    @Test
    public void test17(){
        Long timestamp = 1721064696712252L;

    }
}
