package com.zy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 匠承
 * @Date: 2023/9/16 20:57
 */
public class Constants {

    public static String tables10() {
        StringBuilder sb = new StringBuilder();
        String str = "myschema.tbl_cash_collection,myschema.tbl_company,myschema.tbl_dynamic_trail,myschema.tbl_invoice_product_record,myschema.tbl_lead,myschema.tbl_opportunity,myschema.tbl_opportunity_product,myschema.tbl_opportunity_stage_statistics,myschema.tbl_order,myschema.tbl_order_profit,myschema.tbl_product,myschema.tbl_quotation,myschema.tbl_report_group_data,myschema.tbl_statistic_work_report,myschema.tbl_task";
        sb.append(str);
        for (int i = 1; i <= 9; i++) {
            sb.append("," + str.replaceAll("myschema", "schema" + i));
        }
        return sb.toString();
    }

    /**
     * 客户提供的表结构，所有表，167个schema，2505张表
     */
    public static String tables167() {
        StringBuilder sb = new StringBuilder();
        String str = "myschema.tbl_cash_collection,myschema.tbl_company,myschema.tbl_dynamic_trail,myschema.tbl_invoice_product_record,myschema.tbl_lead,myschema.tbl_opportunity,myschema.tbl_opportunity_product,myschema.tbl_opportunity_stage_statistics,myschema.tbl_order,myschema.tbl_order_profit,myschema.tbl_product,myschema.tbl_quotation,myschema.tbl_report_group_data,myschema.tbl_statistic_work_report,myschema.tbl_task";
        sb.append(str);
        for (int i = 1; i <= 166; i++) {
            sb.append("," + str.replaceAll("myschema", "schema" + i));
        }
        return sb.toString();
    }

    public static String tables167withTest() {
        return tables167() + ",public.debezium_test";
    }

    public static String tables10withTest() {
        return tables10() + ",public.debezium_test";
    }

    public static void main(String[] args) {
        Map<Integer, List<String>> map = new HashMap<>();
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        map.put(1, list);
        System.out.println(map.size());
        System.out.println(map.get(1).toString());

        Map<Integer, List<String>> map2 = new HashMap<>();
        //map2.put(1,list);
        map2.put(2, list);

        Map<Integer, List<String>> map3 = new HashMap<>();
        map3.putAll(map);
        System.out.println(map3);
        map3.putAll(map2);
        System.out.println(map3);
    }
}
