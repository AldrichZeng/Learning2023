package com.example.pgjdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.PosixParser;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 匠承
 * @Date: 2024/1/2 11:10
 */
public class PolarDB {
    private static final Logger logger = LoggerFactory.getLogger(PolarDB.class);

    // 设置RDS PostgreSQL实例的连接地址
    static String hostname = "master-public.o.polardb.rds.aliyuncs.com";
    // 设置RDS PostgreSQL实例的连接端口
    static String port = "1521";
    // 设置待连接的数据库名
    static String dbname = "jiangcheng";

    static String username = "jiangcheng";
    static String password = "DWzengyao123";
    static String sslrootcert = "/Users/aldrichzeng/Downloads/ApsaraDB-CA-Chain (5)/ApsaraDB-CA-Chain.pem";

    public static void main(String[] args) {
        parseArgs(args);

        String jdbcUrl = "jdbc:polardb://" + hostname + ":" + port + "/" + dbname;

        Properties properties = getProperties(args);
        System.out.println(properties);

        try {
            Class.forName("com.aliyun.polardb.Driver");
            Connection connection = DriverManager.getConnection(jdbcUrl, properties);
            //本示例中，假设在postgres数据库中存在表example，此处以查询表example数据为例。
            PreparedStatement preparedStatement = connection.prepareStatement("select * from public.oracle_test");
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
        properties.setProperty("user", username);
        //设置连接数据库的密码
        properties.setProperty("password", password);
        // 设置证书存放路径
        //String path = "/Users/aldrichzeng/Downloads/ssl";

        if (StringUtils.isNotBlank(sslrootcert)) {
            properties.setProperty("ssl", "true");
            properties.setProperty("sslmode", "verify-ca");
            properties.setProperty("sslrootcert", sslrootcert);
        }

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

    public static void parseArgs(String[] args) {

        Options options = new Options();
        Option opt = new Option("h", "help", false, "Print help");
        opt.setRequired(false);
        options.addOption(opt);

        opt = new Option("h", "host", true, "数据库地址");
        opt.setRequired(true);
        options.addOption(opt);

        opt = new Option("P", "port", true, "端口");
        opt.setRequired(true);
        options.addOption(opt);

        opt = new Option("d", "database", true, "数据库名");
        opt.setRequired(true);
        options.addOption(opt);

        opt = new Option("p", "password", true, "密码");
        opt.setRequired(true);
        options.addOption(opt);

        opt = new Option("u", "username", true, "用户名");
        opt.setRequired(true);
        options.addOption(opt);

        opt = new Option("s", "sslrootcert", true, "证书存放位置");
        opt.setRequired(false);
        options.addOption(opt);

        CommandLine commandLine = null;
        CommandLineParser parser = new PosixParser();
        try {
            commandLine = parser.parse(options, args);
            password = commandLine.getOptionValue("password");
            username = commandLine.getOptionValue("username");
            hostname = commandLine.getOptionValue("host");
            port = commandLine.getOptionValue("port");
            dbname = commandLine.getOptionValue("database");
            System.out.println("parse successfully!");
            System.out.println("hostname:" + hostname);
            System.out.println("port:" + port);
            System.out.println("dbname:" + dbname);
            System.out.println("username:" + username);
            System.out.println("password:" + password);
        } catch (Exception e) {
        }
    }
}
