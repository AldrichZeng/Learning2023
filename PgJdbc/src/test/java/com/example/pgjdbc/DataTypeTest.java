package com.example.pgjdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 匠承
 * @Date: 2023/4/15 14:51
 */
public class DataTypeTest {

    private static final Logger logger = LoggerFactory.getLogger(DataTypeTest.class);
    Connection conn;
    Statement stmt;
    ResultSet rs;

    @Before
    public void init() {
        String url = "jdbc:postgresql://pgm-uf6780sk00vfe752co.pg.rds.aliyuncs.com:5432/jctest";
        String user = "jctest";
        String password = "DWzengyao1234";
        try {
            // 加载驱动程序
            Class.forName("org.postgresql.Driver");
            // 建立连接
            conn = DriverManager.getConnection(url, user, password);
            logger.info("Connect successfully!");
            stmt = conn.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test1() throws SQLException {
        // 创建查询语句
        String sql = "SELECT * FROM public.debezium_test";
        rs = stmt.executeQuery(sql);

        // 处理结果
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("addr");
            System.out.println("id: " + id + ", addr: " + name);
        }
    }

    @Test
    public void test3() throws SQLException {
        String sql = "drop table if exists public.my_test";
        stmt.executeUpdate(sql);
        sql = "CREATE TABLE public.my_test " +
                "(ID INT PRIMARY KEY NOT NULL," +
                " NAME TEXT NOT NULL, " +
                " AGE INT NOT NULL, " +
                " ADDRESS CHAR(50), " +
                " SALARY REAL)";
        stmt.executeUpdate(sql);

        sql = "INSERT INTO public.my_test (ID,NAME,AGE,ADDRESS,SALARY) " +
                "VALUES (1, 'Paul', 32, 'California', 20000.00)";
        stmt.executeUpdate(sql);

        sql = "select * from public.my_test";
        rs = stmt.executeQuery(sql);
        // 处理结果
        while (rs.next()) {
            for (int i = 1; i < rs.getMetaData().getColumnCount(); i++) {
                System.out.print(rs.getObject(i) + " ");
            }
            System.out.println();
        }
    }

    @Test
    public void testBIT1() throws SQLException {
        String sql = "drop table if exists public.my_test";
        stmt.executeUpdate(sql);
        sql = "CREATE TABLE public.my_test " +
                "(bit_col BIT(1))";
        stmt.executeUpdate(sql);

        sql = "INSERT INTO public.my_test (bit_col) " +
                "VALUES (null), (b'0'), (b'1')";
        stmt.executeUpdate(sql);

        sql = "select * from public.my_test";
        rs = stmt.executeQuery(sql);

        ResultSetMetaData resultSetMetaData = rs.getMetaData();
        logger.info("Column Type: {}. java.sql.Types.BIT: {}.", resultSetMetaData.getColumnType(1), Types.BIT);
        System.out.println();
        // 处理结果
        while (rs.next()) {
            for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                // pg9.x和pg42.x表现不同
                logger.info("Column_{} Row_{}, getBoolean: {}, getString: {}. ",
                        i, rs.getRow(), rs.getBoolean(i), rs.getString(i));
            }
        }
        logger.info("end");
    }

    @Test
    public void testBIT8() throws SQLException {
        String sql = "drop table if exists public.my_test";
        stmt.executeUpdate(sql);

        sql = "CREATE TABLE public.my_test " +
                "(bit_col BIT(8))";
        stmt.executeUpdate(sql);

        sql = "INSERT INTO public.my_test (bit_col) " +
                "VALUES (null), (b'00000000'), (b'00000001'), (b'00000010'), (b'00000011'), "
                + "(b'00000100'), (b'00000101'), (b'00000110'), (b'00000111')";
        stmt.executeUpdate(sql);

        sql = "select * from public.my_test";
        rs = stmt.executeQuery(sql);

        ResultSetMetaData resultSetMetaData = rs.getMetaData();
        Assert.assertEquals(resultSetMetaData.getColumnType(1), Types.BIT);
        // 处理结果
        while (rs.next()) {
            for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                // pg9.x和pg42.x表现不同
                logger.info("Column_{} Row_{}, getBoolean: {}, getString: {}. ",
                        i, rs.getRow(), rs.getBoolean(i), rs.getString(i));
            }
            //for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
            //    logger.info("Column_{} Row_{}, getString: {}. toBoolean: {}.",
            //            i, rs.getRow(), rs.getString(i), toBoolean(rs.getString(i)));
            //}
        }
        logger.info("end");
    }

    private Boolean toBoolean(String bit8) {
        return Double.parseDouble(bit8) == 1;
    }

    @Test
    public void parseDoubleTest() {
        Assert.assertEquals(Double.parseDouble("00000000"), 0.0, 2);
        Assert.assertEquals(Double.parseDouble("00000001"), 1.0, 2);
        Assert.assertTrue(Double.parseDouble("00000001") == 1);
        Assert.assertTrue(Double.parseDouble("00000010") == 10.0);
        Assert.assertTrue(Double.parseDouble("00000010") == 10);
        Assert.assertEquals(Double.parseDouble("00000010"), 10.0, 2);
        Assert.assertEquals(Double.parseDouble("00000011"), 11.0, 2);
        Assert.assertEquals(Double.parseDouble("00000100"), 100.0, 2);
        Assert.assertEquals(Double.parseDouble("00000101"), 101.0, 2);
        Assert.assertEquals(Double.parseDouble("00000110"), 110.0, 2);
        Assert.assertEquals(Double.parseDouble("00000111"), 111.0, 2);
    }

    @Test
    public void test5() {

    }

    @After
    public void destroy() {
        try {
            // 关闭资源
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
