package com.zy;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

/**
 * @author 匠承
 * @Date: 2023/9/10 11:54
 */
public class StringTest {

    @Test
    public void testTime(){
        Long timeInMillis = 1696767736467L;
        Date date = new Date(timeInMillis);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sdf.format(date));
    }

    @Test
    public void testScanner() {
        String lineDelimtier = "\\t";
        Scanner scanner = new Scanner("1\t2\t");
        Pattern pattern = Pattern.compile(lineDelimtier, Pattern.LITERAL);
        scanner.useDelimiter(pattern);
        while (scanner.hasNext()) {
            System.out.println("output:" + scanner.nextInt());
        }
    }

    @Test
    public void testEscape() {
        String lineDelimiter = "\\n";
        String escapeJava = StringEscapeUtils.escapeJava(lineDelimiter);
        System.out.println("origin:" + escapeJava);
        if (StringUtils.contains(escapeJava, "\\\\")) {
            System.out.println("true");
        }
        String unescape = StringEscapeUtils.unescapeJava(lineDelimiter);
        System.out.println(StringEscapeUtils.escapeJava(unescape));
        System.out.println("end");
    }

    @Test
    public void test() {
        StringBuilder sb = new StringBuilder();
        for (int i = 2001; i <= 3000; i++) {
            String str = "create table if not exists public.multi" + i + "(\n"
                    + "\tid int primary key,\n"
                    + "\tstr_col varchar(100)\n"
                    + ");\n";
            sb.append(str);
        }
        System.out.println(sb.toString());
    }

    @Test
    public void test2() {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= 3000; i++) {
            String str = "insert into public.multi" + i + " values (5,'abc'), (6,'xyz');\n";
            sb.append(str);
        }
        System.out.println(sb.toString());
    }

    @Test
    public void test3() {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= 100; i++) {
            String str = "ALTER TABLE public.bigSchema" + i + " REPLICA IDENTITY FULL;\n";
            sb.append(str);
        }
        System.out.println(sb.toString());
    }

    @Test
    public void test4() {
        String[] str = {"a", "b"};
        System.out.println(StringUtils.join(str, ","));
    }

    @Test
    public void test5() {
        String str = "myschema.tbl_cash_collection,myschema.tbl_company,myschema.tbl_dynamic_trail,myschema.tbl_invoice_product_record,myschema.tbl_lead,myschema.tbl_opportunity,myschema.tbl_opportunity_product,myschema.tbl_opportunity_stage_statistics,myschema.tbl_order,myschema.tbl_order_profit,myschema.tbl_product,myschema.tbl_quotation,myschema.tbl_report_group_data,myschema.tbl_statistic_work_report,myschema.tbl_task,";

        for (int i = 1; i <= 166; i++) {
            System.out.println(str.replace("myschema", "schema" + i));
        }
    }

    @Test
    public void test6() {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= 100; i++) {
            sb.append("public.bigschema").append(i).append(",");
        }
        System.out.println(sb.toString());
    }
}
