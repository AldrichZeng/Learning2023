package com.example.pgjdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 匠承
 * @Date: 2024/5/13 23:36
 */
public class FetchSize {
    private static final Logger logger = LoggerFactory.getLogger(FetchSize.class);

    static String hostname = "pgm-2ze2il4nk8fs3657bo1.pg.rds.aliyuncs.com";
    // 设置RDS PostgreSQL实例的连接端口
    static String port = "5433";
    // 设置待连接的数据库名
    static String dbname = "testdb";
    static String username = "jiangcheng";
    static String password = "DWzengyao123";
    static String schema = "public";
    static String tableName = "test4";

    public static void main(String[] args) {

        //hostname = args[0];
        //schema = args[1];
        //tableName = args[2];

        String jdbcUrl = "jdbc:postgresql://" + hostname + ":" + port + "/" + dbname;

        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(jdbcUrl, username, password);

            String queryFullColumnSql = String.format("SELECT\n"
                    + "    col.column_name AS column_name,\n"
                    + "    d.description AS column_comment \n"
                    + "FROM \n"
                    + "    information_schema.columns col\n"
                    + "    JOIN pg_class c ON c.relname = col.table_name\n"
                    + "    LEFT JOIN pg_description d ON d.objoid = c.oid\n"
                    + "        AND d.objsubid = col.ordinal_position\n"
                    + "WHERE\n"
                    + "    col.table_schema = '%s'\n"
                    + "    AND col.table_name = '%s';", schema, tableName);

            logger.info("query table {} column comment sql: \n{}", tableName, queryFullColumnSql);

            int fetchSize = 150;
            PreparedStatement pstmt = conn.prepareStatement(queryFullColumnSql);
            pstmt.setFetchSize(fetchSize);
            ResultSet rs = pstmt.executeQuery();
            rs.setFetchSize(fetchSize);

            logger.info("columnType1: {} ", rs.getMetaData().getColumnTypeName(1));
            logger.info("columnType2: {} ", rs.getMetaData().getColumnTypeName(2));
            rs.getMetaData().getColumnType(2);
            while (rs.next()) {
                String columnName = rs.getString("column_name");
                String comment = rs.getString("column_comment");
                logger.info("columnName: {}, comment: {}", columnName, comment);
            }
            pstmt.close();
            rs.close();
            conn.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
