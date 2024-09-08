package com.zy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import com.zy.model.ReplicationSlot;
import io.debezium.connector.postgresql.connection.Lsn;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

/**
 * @author 匠承
 * @Date: 2023/7/12 17:50
 */
@Slf4j
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

    /**
     * 从URL指定的文件中，读取文件，解析为JSON Object
     *
     * @param filePath
     * @return
     */
    public static JSONObject getParameter(String filePath) {
        try {
            URL url = Utils.class.getClassLoader().getResource(filePath);
            JSONObject parameter = JSON.parseObject(FileUtils.readFileToString(new File(url.getPath())));
            return parameter;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取PostgreSQL的连接
     *
     * @param dbHost
     * @param dbName
     * @param username
     * @param password
     * @param port
     * @param timeout
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static Connection getConnection(String dbHost, String dbName, String username, String password, int port, int timeout) {
        try {
            String jdbcUrl = String.format("jdbc:postgresql://%s:%s/%s?connectTimeout=%d", dbHost, port, dbName, timeout);

            Class aClass = Class.forName("org.postgresql.Driver");
            Driver driver = (Driver) aClass.newInstance();
            Properties prop = new Properties();
            prop.put("user", username);
            prop.put("password", password);
            Connection connection = driver.connect(jdbcUrl, prop);
            return connection;
        } catch (Exception e) {
            throw new RuntimeException(String.format("Failed to get connection, host: %s, port: %s, database: %s, username: %s, password: %s, error: %s", dbHost, port, dbName, username, password, e.toString()));
        }
    }

    public static void createSlot(Connection conn, String slotName, String plugin) {
        try {
            String sql = String.format("SELECT * FROM pg_create_logical_replication_slot('%s', '%s')", slotName, plugin);
            Statement stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            stmt.executeQuery(sql);
        } catch (SQLException e) {
            log.error("Failed to create replication slot, slot: {}, error: {}", slotName, e.toString());
        }
    }

    public static ReplicationSlot slotInfo(Connection conn, String slotName) {
        try {
            String sql = String.format("select * from pg_replication_slots where slot_name = '%s'", slotName);
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            if (!rs.next()) {
                return null;
            }
            ReplicationSlot replicationSlot = new ReplicationSlot();
            replicationSlot.setSlotName(rs.getString("slot_name"));
            replicationSlot.setPlugin(rs.getString("plugin"));
            replicationSlot.setSlotType(rs.getString("slot_type"));
            replicationSlot.setActive(rs.getBoolean("active"));
            replicationSlot.setRestartLsn(Lsn.valueOf(rs.getString("restart_lsn")));
            replicationSlot.setConfirmFlushLsn(Lsn.valueOf(rs.getString("confirmed_flush_lsn")));
            return replicationSlot;
        } catch (SQLException e) {
            throw new RuntimeException(String.format("Failed to get replication slot, slot: %s, error: %s", slotName, e.toString()));
        }
    }

    public static void createPublication(Connection conn, String publicationName, String tableParams) {
        try {
            String sql = String.format("create publication %s for table %s", publicationName, tableParams);
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {

            log.error("Faied to create publication, publication: {}, error: {}", publicationName, e.toString());
        }
    }
}
