package com.example.mysqljdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Date: 2024/4/13 17:17
 */
public class TestArray {

    private static final Logger logger = LoggerFactory.getLogger(MySQL.class);

    /**
     * 测试实例：qa3账号，北京
     */
    static String hostname = "rm-2zeb4f2r403u3buspvo.mysql.rds.aliyuncs.com";
    // 设置RDS PostgreSQL实例的连接端口
    static String port = "3306";
    // 设置待连接的数据库名
    static String dbname = "jiangcheng";
    static String username = "jiangcheng";
    static String password = "DWzengyao123";
    static String tableName = "test_array";

    public static void main(String[] args) {

        String jdbcUrl = "jdbc:mysql://" + hostname + ":" + port + "/" + dbname;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(jdbcUrl, username, password);

            PreparedStatement pstmt = conn.prepareStatement("select * from " + tableName + " where str_col in (?)");
            pstmt.setArray(1, conn.createArrayOf("VARCHAR", new String[]{"abc", "xyz", "hello"}));
            ResultSet resultSet = pstmt.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            for (int i = 0; i < metaData.getColumnCount(); i++) {
                logger.info("columnName: {}, columnType: {},{}", metaData.getColumnName(i + 1), metaData.getColumnTypeName(i + 1), metaData.getColumnType(i + 1));
            }
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String str_col = resultSet.getString("str_col");
                logger.info("id = {}, str_col = {}", id, str_col);
            }
            resultSet.close();
            pstmt.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
