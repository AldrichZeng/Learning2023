package com.example.pgjdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 匠承
 * @Date: 2024/1/2 11:10
 */
public class PolarDB {
    private static final Logger logger = LoggerFactory.getLogger(PolarDB.class);

    public static void main(String[] args) {
        // 设置RDS PostgreSQL实例的连接地址
        String hostname = "pc-uf6834ro3065e495q-out.o.polardb.rds.aliyuncs.com";
        //String hostname = "pgm-uf6780sk00vfe752co.pg.rds.aliyuncs.com";
        // 设置RDS PostgreSQL实例的连接端口
        String port = "1521";
        //String port = "5432";
        // 设置待连接的数据库名
        //String dbname = "jctest";
        String dbname = "jiangcheng";

        String jdbcUrl = "jdbc:postgresql://" + hostname + ":" + port + "/" + dbname;

        Properties properties = getProperties(args);
        System.out.println(properties);

        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(jdbcUrl, properties);
            //本示例中，假设在postgres数据库中存在表example，此处以查询表example数据为例。
            PreparedStatement preparedStatement = connection.prepareStatement("select * from " +
                    "public.oracle_test");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ResultSetMetaData rsmd = resultSet.getMetaData();
                int columnCount = rsmd.getColumnCount();
                Map map = new HashMap();
                for (int i = 0; i < columnCount; i++) {
                    map.put(rsmd.getColumnName(i + 1).toLowerCase(), resultSet.getObject(i + 1));
                }
                System.out.println(map);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private static Properties getProperties(String[] args) {
        Properties properties = new Properties();
        // 设置连接数据库的用户名
        //properties.setProperty("user", "jctest");
        properties.setProperty("user", "jiangcheng");
        //设置连接数据库的密码
        //properties.setProperty("password", "DWzengyao1234");
        properties.setProperty("password", "DWzengyao123");
        // 设置证书存放路径
        //String path = "/Users/aldrichzeng/Downloads/ssl";

        //if (args.length == 4) {
        //    // 配置以SSL访问
        //    properties.setProperty("ssl", "true");
        //    //设置证书授权机构的公钥名
        //    properties.setProperty("sslrootcert", args[0]);
        //    //设置客户端证书私钥名
        //    properties.setProperty("sslkey", args[1]);
        //    //设置客户端证书名
        //    properties.setProperty("sslcert", args[2]);
        //    //填写将私钥key格式转换为pk8格式时设置的密码
        //    properties.setProperty("sslpassword", args[3]);
        //    // 配置SSL模式，可选值为require、verify-ca、verify-full
        //    properties.setProperty("sslmode", "verify-ca");
        //}

        return properties;
    }
}
