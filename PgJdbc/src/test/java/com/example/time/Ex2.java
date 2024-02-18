package com.example.time;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.TimeZone;

import org.junit.Test;

/**
 * @author 匠承
 * @Date: 2024/2/18 10:52
 */
public class Ex2 {
    /**
     * 实际时间是北京时间1899-01-01 00:00:00
     */
    Long rawData = -2209017943000L;

    /**
     * 模拟DataX的时间处理过程。
     * CST：中国标准时间
     */
    @Test
    public void testMockDatax() {
        System.out.println(rawData);
        // 这一步就出错了
        Date date = new Date(rawData);
        System.out.println(date.getTime());
        System.out.println("============");
        //TimeZone.setDefault(TimeZone.getTimeZone(ZoneId.of("America/New_York")));
        System.out.println(date);//Sat Dec 31 23:54:17 CST 1898
        Long time = date.getTime();
        Timestamp timestamp = new Timestamp(time);
        System.out.println(timestamp);//1898-12-31 23:54:17.0
    }

    /**
     * 使用Instant可以矫正时间
     */
    @Test
    public void testInstant() {
        //TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));

        Instant instant = Instant.ofEpochMilli(rawData);
        System.out.println(instant);
        ZonedDateTime dateTime = instant.atZone(ZoneId.systemDefault());
        System.out.println(dateTime.toInstant().getEpochSecond());

        System.out.println("====");

        LocalDateTime localDateTime = dateTime.toLocalDateTime();
        Timestamp timestamp = Timestamp.valueOf(localDateTime);
        System.out.println(timestamp.getTime());
        //Timestamp timestamp = Timestamp.from(instant);
        System.out.println(timestamp);
    }

    /**
     * 使用Instant不会偏移的原因
     * 上海时区，时差是+08:05:43
     * 纽约时区，时差是-05:00
     */
    @Test
    public void testInstant2() {
        Instant instant = Instant.ofEpochMilli(rawData);
        // 使用当前时区：Asia/Shanghai
        ZonedDateTime dateTime = instant.atZone(ZoneId.systemDefault());
        System.out.println(dateTime);//1899-01-01T00:00+08:05:43[Asia/Shanghai]
        System.out.println(Timestamp.valueOf(dateTime.toLocalDateTime()));//1899-01-01 00:00:00.0

        // 使用America/New_York
        dateTime = instant.atZone(ZoneId.of("America/New_York"));
        System.out.println(dateTime);//1898-12-31T10:54:17-05:00[America/New_York]
        System.out.println(Timestamp.valueOf(dateTime.toLocalDateTime()));//1898-12-31 10:54:17.0

        // 使用UTC时区
        dateTime = instant.atZone(ZoneId.of(ZoneOffset.UTC.getId()));
        System.out.println(dateTime);//1898-12-31T15:54:17Z
        System.out.println(Timestamp.valueOf(dateTime.toLocalDateTime()));//1898-12-31 15:54:17.0
    }

    /**
     * 处理纳秒
     */
    @Test
    public void testNanos() {
        Instant instant = Instant.ofEpochMilli(-8520365142877L);
        ZonedDateTime dateTime = instant.atZone(ZoneId.systemDefault());
        System.out.println(dateTime);//1700-01-01T00:00:00.123+08:05:43[Asia/Shanghai]
        Timestamp timestamp = Timestamp.valueOf(dateTime.toLocalDateTime());
        timestamp.setNanos(123456000);
        System.out.println(timestamp);//1700-01-01 00:00:00.123456
    }

}
