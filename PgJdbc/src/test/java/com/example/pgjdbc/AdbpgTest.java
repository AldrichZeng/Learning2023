package com.example.pgjdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 匠承
 * @Date: 2023/4/28 10:10
 */
public class AdbpgTest {
    private static final Logger logger = LoggerFactory.getLogger(DataTypeTest.class);
    Connection conn;
    Statement stmt;
    ResultSet rs;

    @Before
    public void init() {
        String url = "jdbc:postgresql://gp-uf6vciyech0o0l3huo-master.gpdb.rds.aliyuncs.com:5432/jiangcheng";
        String user = "jiangcheng";
        String password = "DW123456++";
        try {
            // 加载驱动程序
            Class.forName("org.postgresql.Driver");
            // 建立连接
            conn = DriverManager.getConnection(url, user, password);
            logger.info("Connect successfully! DB: {}, Driver: {} {}.", conn.getMetaData().getDatabaseProductName(), conn.getMetaData().getDriverName(), conn.getMetaData().getDriverVersion());
            stmt = conn.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test() throws SQLException {
        // 创建查询语句
        String sql = "SELECT * FROM public.testBool";
        rs = stmt.executeQuery(sql);

        for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
            System.out.println(i + " = " + rs.getMetaData().getColumnType(i + 1));
        }
        // 处理结果
        while (rs.next()) {
            int id = rs.getInt("id");
            Boolean bool_col = rs.getBoolean("bool_col");
            String data = rs.getString("bool_col");
            if(data == null){
                logger.info("id = {}, bool_col of String is null", id);
            }

            String bit_col_str = rs.getString("bit_col");
            Boolean bit_col = rs.getBoolean("bit_col");

            String bit_col_str_8 = rs.getString("bit_col_8");
            Boolean bit_col_8 = rs.getBoolean("bit_col_8");
            logger.info("id = {}, bool_col = {}, bit_col = {} {}, bit_col_8 = {} {}", id, bool_col, bit_col_str, bit_col, bit_col_str_8, bit_col_8);
        }

        //9.x驱动
        //0 = 4、
        //1 = -7
        //2 = -7
        //3 = -7
        //10:30:56.990 [main] INFO com.example.pgjdbc.DataTypeTest - id = 1, bool_col = true, bit_col = 0 false, bit_col_8 = 00011010 false
        //10:30:56.991 [main] INFO com.example.pgjdbc.DataTypeTest - id = 3, bool_col = true, bit_col = 0 false, bit_col_8 = 00000000 false
        //10:30:56.992 [main] INFO com.example.pgjdbc.DataTypeTest - id = 2, bool_col = false, bit_col = 0 false, bit_col_8 = 00011010 false
        //10:30:56.992 [main] INFO com.example.pgjdbc.DataTypeTest - id = 4, bool_col = false, bit_col = 0 false, bit_col_8 = 00000001 true
        //10:30:56.992 [main] INFO com.example.pgjdbc.DataTypeTest - id = 5, bool_col = false, bit_col = 0 false, bit_col_8 = 00000001 true

    }
}
