package com.example.time;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.junit.Test;

/**
 * @author 匠承
 * @Date: 2024/2/18 20:34
 */
public class Ex3 {

    /**
     * JDK的zoneOffset是错误的
     */
    @Test
    public void test1() {
        System.out.println(TimeZone.getDefault().getOffset(-2209017943000L));
        System.out.println(TimeZone.getDefault().getOffset(-2240553943000L));

        // 查看各个时间对应的时差
        Date date1 = new Date(0, Calendar.JANUARY, 1);
        Date date2 = new Date(0, Calendar.JANUARY, 3);
        Date date3 = new Date(100, Calendar.JANUARY, 2);

        System.out.println(date1);//Mon Jan 01 00:00:00 CST 1900
        System.out.println(TimeZone.getDefault().getOffset(date1.getTime()));//28800000

        System.out.println(date2);//Wed Jan 03 00:00:00 CST 1900
        System.out.println(TimeZone.getDefault().getOffset(date2.getTime()));//29143000

        System.out.println(date3);//Sun Jan 02 00:00:00 CST 2000
        System.out.println(TimeZone.getDefault().getOffset(date3.getTime()));//28800000
    }

    /**
     * 模拟PG JDBC驱动处理的方式
     */
    @Test
    public void test2() {
        Long rawData = -2240553943000L;
        // 用一个时间戳传入进去
        Timestamp timestamp = new Timestamp(rawData);
        // 初始化为GregorianCalendar
        Calendar calendar = new GregorianCalendar();
        // 内部设置时区
        calendar.setTimeZone(TimeZone.getDefault());
        System.out.println("timeZone: " + TimeZone.getDefault());
        // 设置时间
        calendar.setTimeInMillis(timestamp.getTime());
        // 获取各个值，用于序列化
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);
        int seconds = calendar.get(Calendar.SECOND);
        // offset是错误的
        int offset = (calendar.get(Calendar.ZONE_OFFSET) + calendar.get(Calendar.DST_OFFSET)) / 1000;
        System.out.println("year:" + year);
        System.out.println("month:" + month);
        System.out.println("day:" + day);
        System.out.println("offset:" + offset);
    }

    /**
     * 在没有时区的情况下，Instant也是错误的
     */
    @Test
    public void test3(){
        Long rawData = -2240553943000L;
        Timestamp timestamp = new Timestamp(rawData);
        Instant instant = timestamp.toInstant();
        System.out.println(instant);
    }

    /**
     * 用于debug的程序，查看{@link java.util.Calendar#setTimeInMillis}中做了什么。
     */
    @Test
    public void test4(){
        java.util.Calendar cal = new GregorianCalendar();
        cal.setTimeInMillis(-2209017943000L);
        int offset = (cal.get(Calendar.ZONE_OFFSET) + cal.get(Calendar.DST_OFFSET));
        // 这个offset是不对的
        System.out.println(offset);
    }

}
