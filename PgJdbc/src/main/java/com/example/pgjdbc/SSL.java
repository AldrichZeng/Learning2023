package com.example.pgjdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author 匠承
 * @Date: 2023/12/16 22:19
 */
public class SSL {
    public static void main(String[] args) {
        // 设置RDS PostgreSQL实例的连接地址
        String hostname = "pgm-uf632em1flr129b8.pg.rds.aliyuncs.com";
        // 设置RDS PostgreSQL实例的连接端口
        String port = "5432";
        // 设置待连接的数据库名
        String dbname = "jiangcheng";

        String jdbcUrl = "jdbc:postgresql://" + hostname + ":" + port + "/" + dbname;

        Properties properties = getProperties(args);
        System.out.println(properties);

        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(jdbcUrl, properties);
            //本示例中，假设在postgres数据库中存在表example，此处以查询表example数据为例。
            PreparedStatement preparedStatement = connection.prepareStatement("select * from " +
                    "public.example");
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
        properties.setProperty("user", "jiangcheng");
        //设置连接数据库的密码
        properties.setProperty("password", "DWzengyao123");
        // 设置证书存放路径
        //String path = "/Users/aldrichzeng/Downloads/ssl";

        if (args.length == 4) {
            // 配置以SSL访问
            properties.setProperty("ssl", "true");
            //设置证书授权机构的公钥名
            properties.setProperty("sslrootcert", args[0]);
            //设置客户端证书私钥名
            properties.setProperty("sslkey", args[1]);
            //设置客户端证书名
            properties.setProperty("sslcert", args[2]);
            //填写将私钥key格式转换为pk8格式时设置的密码
            properties.setProperty("sslpassword", args[3]);
            // 配置SSL模式，可选值为require、verify-ca、verify-full
            properties.setProperty("sslmode", "verify-ca");
        }

        return properties;
    }
}
