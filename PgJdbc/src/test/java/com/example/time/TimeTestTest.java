package com.example.time;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import jdk.management.resource.internal.inst.WindowsAsynchronousServerSocketChannelImplRMHooks;
import org.junit.Test;
import org.mockito.internal.verification.Times;

/**
 * @author 匠承
 * @Date: 2024/2/17 22:27
 */
public class TimeTestTest {
    @Test
    public void test() {
        //Long rawData = -1893456000000L;
        Long rawData = -2240553943000L;
        Date date = new Date((Long) rawData);
        System.out.println(date);//Sat Dec 31 23:54:17 CST 1898
        System.out.println(TimeZone.getDefault());//sun.util.calendar.ZoneInfo[id="Asia/Shanghai",offset=28800000,dstSavings=0,useDaylight=false,transitions=31,lastRule=null]
        System.out.println(TimeZone.getDefault().getOffset(-2240553943000L));//28800000
    }

    @Test
    public void test2() {
        long pos = -1600592400000L;
        Date date = new Date(pos);
        Timestamp timestamp = new Timestamp(date.getTime());
        System.out.println(timestamp);
        System.out.println(timestamp.toLocalDateTime());
    }

    @Test
    public void test3() {
        Instant instant = Instant.ofEpochMilli(-2240553943000L);
        ZonedDateTime dateTime = instant.atZone(ZoneId.systemDefault());
        System.out.println(dateTime);//1899-01-01T00:00+08:05:43[Asia/Shanghai]
        System.out.println(dateTime.toLocalDateTime());//1899-01-01T00:00
        System.out.println(ZoneId.systemDefault());//Asia/Shanghai
        System.out.println(instant.atZone(ZoneId.of("America/New_York")));//1898-12-31T10:54:17-05:00[America/New_York]
        System.out.println(instant.atZone(ZoneId.of(ZoneOffset.UTC.getId())));//1898-12-31T15:54:17Z
        System.out.println(instant.getEpochSecond());
        System.out.println(Timestamp.valueOf(dateTime.toLocalDateTime()));
    }

    @Test
    public void test4() {
        Date date = new Date(0, Calendar.JANUARY, 1);
        System.out.println(date);

        long secondsPerDay = 24 * 3600 * 1000;
        long millsOfDays = date.getTime() + TimeZone.getDefault().getOffset(date.getTime());
        System.out.println(millsOfDays);
        System.out.println(TimeZone.getDefault().getOffset(date.getTime()));
        System.out.println(new Date(millsOfDays - TimeZone.getDefault().getOffset(millsOfDays)));
    }

    @Test
    public void test5(){
        String regionId = "cn-beijing";
        String region = regionId.replace("-","_");
        System.out.println(region);
    }

    @Test
    public void test6(){
        String a = "[\"SYNC_STRUCT\",\"ONCE_OFFLINE\"]";
        JSONArray b= JSON.parseArray(a);
        System.out.println(b.get(0));
        System.out.println(b.get(1));
    }

    @Test
    public void test7(){
        System.out.println(ZoneId.systemDefault().toString());
    }


    @Test
    public void test8(){
        A a = null;
        change(a);
        System.out.println(a);
    }
    private void change(A a){
        a = new A();
        a.setX("123");
    }

    public static class A {
        private String x;
        public void setX(String x){
            this.x=x;
        }
        public String getX(){
            return this.x;
        }
        @Override
        public String toString(){
            return "x = " + x;
        }
    }

    @Test
    public void test9(){
        String s = String.format("%s.%s", "abc", null);
        System.out.println(s);
        System.out.println(s.length());
    }

    @Test
    public void test10(){
        System.out.println("4".compareTo("4")<=0);
    }







}