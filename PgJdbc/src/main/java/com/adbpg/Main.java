package com.adbpg;

import java.sql.Connection;

/**
 * @author 匠承
 * @Date: 2024/9/10 13:58
 */
public class Main {



    public static void main(String[] args) {
        String host = "gp-2ze6hg28iofa9ca1zo-master.gpdb.rds.aliyuncs.com";

        Connection connnection = Utils.getConnection(host, "jiangcheng", "jiangcheng", "DWzengyao123", 5432, 10);

        //Utils.searchTable("common", connnection);
        System.out.println("========");
        Utils.searchTable("", connnection);
    }
}
