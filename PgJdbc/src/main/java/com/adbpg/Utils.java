package com.adbpg;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author 匠承
 * @Date: 2024/9/10 13:58
 */
public class Utils {

    public static void searchTable(String tableKeyword, Connection conn) {
        try {
            String sql = searchTableSql(tableKeyword);
            System.out.println(sql);
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String schemaName = rs.getString("TABLE_SCHEM");
                // tbName本身仅含有tableName
                String tbName = rs.getString("TABLE_NAME");
                System.out.println(String.format("schemaName: %s, tableName: %s", schemaName, tbName));
            }
        } catch (SQLException e) {

        }
    }

    public static String searchTableSql(String tableKeyword) {
        String sql = " SELECT  NULL AS TABLE_CAT\n"
                + "        ,n.nspname AS TABLE_SCHEM\n"
                + "        ,c.relname AS TABLE_NAME\n"
                + "        ,CASE n.nspname ~ '^pg_' OR n.nspname = 'information_schema' WHEN TRUE THEN CASE WHEN n.nspname = 'pg_catalog' OR n.nspname = 'information_schema' THEN CASE c.relkind WHEN 'r' THEN 'SYSTEM TABLE' WHEN 'v' THEN 'SYSTEM VIEW' WHEN 'i' THEN 'SYSTEM INDEX' ELSE NULL END WHEN n.nspname = 'pg_toast' THEN CASE c.relkind WHEN 'r' THEN 'SYSTEM TOAST TABLE' WHEN 'i' THEN 'SYSTEM TOAST INDEX' ELSE NULL END ELSE CASE c.relkind WHEN 'r' THEN 'TEMPORARY TABLE' WHEN 'p' THEN 'TEMPORARY TABLE' WHEN 'i' THEN 'TEMPORARY INDEX' WHEN 'S' THEN 'TEMPORARY SEQUENCE' WHEN 'v' THEN 'TEMPORARY VIEW' ELSE NULL END END WHEN FALSE THEN CASE c.relkind WHEN 'r' THEN 'TABLE' WHEN 'p' THEN 'TABLE' WHEN 'i' THEN 'INDEX' WHEN 'S' THEN 'SEQUENCE' WHEN 'v' THEN 'VIEW' WHEN 'c' THEN 'TYPE' WHEN 'f' THEN 'FOREIGN TABLE' WHEN 'm' THEN 'MATERIALIZED VIEW' ELSE NULL END ELSE NULL END AS TABLE_TYPE\n"
                + "        ,di.inhrelid as inhrelid\n"
                + "FROM    pg_catalog.pg_namespace n\n"
                + "        ,pg_catalog.pg_class c\n"
                + "LEFT JOIN pg_catalog.pg_description d\n"
                + "ON      (c.oid = d.objoid AND d.objsubid = 0) LEFT\n"
                + "JOIN    pg_catalog.pg_class dc\n"
                + "ON      (d.classoid = dc.oid AND dc.relname = 'pg_class')\n"
                + "LEFT JOIN pg_catalog.pg_namespace dn\n"
                + "ON      (dn.oid = dc.relnamespace AND dn.nspname = 'pg_catalog')\n"
                + "LEFT JOIN pg_catalog.pg_inherits di\n"
                + "ON      c.oid = di.inhrelid\n"
                + "WHERE   c.relnamespace = n.oid\n"
                + " AND     c.relname LIKE '%" + tableKeyword + "%' AND     (\n"
                + "                FALSE\n"
                + "            OR  (\n"
                + "                        c.relkind IN ('r','p')\n"
                + "                    AND n.nspname !~ '^pg_'\n"
                + "                    AND n.nspname <> 'information_schema'\n"
                + "                )\n"
                + "            OR  (\n"
                + "                        c.relkind = 'v'\n"
                + "                    AND n.nspname <> 'pg_catalog'\n"
                + "                    AND n.nspname <> 'information_schema'\n"
                + "                )\n"
                + "            OR  ( c.relkind = 'f' )\n"
                + "        )\n"
                + "AND      inhrelid is null\n"
                + "AND n.nspname NOT IN ('IMV_delta','laser','queryprofile','adbpg_automergestatus','adbpg_restore','adbpg_toolkit','gp_toolkit') ORDER BY TABLE_TYPE\n"
                + "         ,TABLE_SCHEM\n"
                + "         ,TABLE_NAME";
        return sql;
    }

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
}
