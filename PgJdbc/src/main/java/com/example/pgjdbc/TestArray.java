package com.example.pgjdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 匠承
 * @Date: 2024/4/23 14:20
 */
public class TestArray {
    private static final Logger logger = LoggerFactory.getLogger(TestArray.class);

    /**
     * 测试实例：qa3账号，北京
     */
    static String hostname = "pgm-2ze71531tgr926f0bo.pg.rds.aliyuncs.com";
    // 设置RDS PostgreSQL实例的连接端口
    static String port = "5432";
    // 设置待连接的数据库名
    static String dbname = "jiangcheng";
    static String username = "jiangcheng";
    static String password = "DWzengyao123";
    static String tableName = "public.test_array";

    public static void main(String[] args) {
        String jdbcUrl = "jdbc:postgresql://" + hostname + ":" + port + "/" + dbname;

        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(jdbcUrl, username, password);

            PreparedStatement pstmt = conn.prepareStatement("select * from " + tableName + " where id = any(?)");
            //pstmt.setArray(1, conn.createArrayOf("varchar", new Object[]{"abc", "xyz", "hello"}));
            pstmt.setArray(1, conn.createArrayOf("varchar", new Integer[]{1,2,3}));
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
        } catch (
                Exception exception) {
            exception.printStackTrace();
        }
    }
}
