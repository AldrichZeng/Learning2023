package com.zy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.BitSet;

import de.bytefish.pgbulkinsert.row.SimpleRow;

/**
 * @author 匠承
 * @Date: 2023/6/27 11:19
 */
public class LocalDateTimeTest {

    public static void main(String[] args) {
        //DateTimeFormatter dtf = new DateTimeFormatterBuilder().parseCaseInsensitive()
        //        .append(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")).toFormatter();
        //LocalTime ldt = LocalTime.parse("01:01:01");
        //System.out.println(ldt);
        //
        //System.out.println(LocalDateTime.parse("2000-01-01T01:01:01.123"));

        BitSet bitSet = BitSet.valueOf("abc".getBytes());
        System.out.println(bitSet);
    }
}
