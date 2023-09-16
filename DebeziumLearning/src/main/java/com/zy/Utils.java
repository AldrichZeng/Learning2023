package com.zy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import io.debezium.connector.postgresql.connection.Lsn;

/**
 * @author 匠承
 * @Date: 2023/7/12 17:50
 */
public class Utils {

    public static String lsnHexToDec(String lsnInHex) {
        Lsn lsn = Lsn.valueOf(lsnInHex);
        return String.valueOf(lsn.asLong());
    }

    public static String JSONFormat(String s) {
        JSONObject object = JSONObject.parseObject(s);
        return JSON.toJSONString(object, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteDateUseDateFormat);
    }

    public static String generateTables(int start, int end, String table) {
        StringBuilder sb = new StringBuilder();
        for (int i = start; i <= end; i++) {
            if (i != end) {
                sb.append("public.").append(table).append(i).append(",");
            } else {
                sb.append("public.").append(table).append(i);
            }
        }
        return sb.toString();
    }

    public static String generateTables2() {
        StringBuilder sb = new StringBuilder();
        String origin = "myschema.tbl_cash_collection,myschema.tbl_company,myschema.tbl_dynamic_trail,myschema.tbl_invoice_product_record,myschema.tbl_lead,myschema.tbl_opportunity,myschema.tbl_opportunity_product,myschema.tbl_opportunity_stage_statistics,myschema.tbl_order,myschema.tbl_order_profit,myschema.tbl_product,myschema.tbl_quotation,myschema.tbl_report_group_data,myschema.tbl_statistic_work_report,myschema.tbl_task,";
        sb.append(origin);
        for (int i = 1; i <= 166; i++) {
            sb.append(origin.replace("myschema", "schema" + i));
        }
        if (sb.toString().endsWith(",")) {
            return sb.toString().substring(0, sb.toString().length() - 1);
        } else {
            return sb.toString();
        }
    }

    public static String generateTablesSchema(int tableId) {
        StringBuilder sb = new StringBuilder();
        sb.append("create table bigSchema").append(tableId).append("(\n");
        sb.append("\tid int primary key,\n");
        int n = 1000;
        for (int i = 1; i < n; i++) {
            sb.append("\tcol_").append(i).append(" varchar(100),\n");
        }
        sb.append("\tcol_").append(n).append(" varchar(100)\n);");
        return sb.toString();
    }

    public static void generateMultiTable() throws FileNotFoundException {
        FileOutputStream fileOutputStream = new FileOutputStream(new File("output.txt"));
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(fileOutputStream));
        for (int i = 1; i <= 100; i++) {
            pw.println(generateTablesSchema(i));
        }
        pw.close();
    }

    public static void main(String[] args) throws FileNotFoundException {
        //System.out.println(generateTables(1, 3000));
    }
}
