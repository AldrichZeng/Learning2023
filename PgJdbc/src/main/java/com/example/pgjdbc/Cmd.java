package com.example.pgjdbc;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * @author 匠承
 * @Date: 2023/9/21 11:02
 */
public class Cmd {

    public static void main(String[] args) {
        BasicParser parser = new BasicParser();
        Options options = new Options();
        options.addOption("doTrans", false, "begin doTrans for DataSource2023 odps");
        options.addOption("j", true, "json text");
        options.addOption("f", true, "file path");
        options.addOption("c", true, "criteria");
        options.addOption("t", true, "interval time");
        options.addOption("p", true, "pipeline");
        options.addOption("v", true, "variable");
        try {
            CommandLine cl = parser.parse(options, args);
        } catch (ParseException e) {
            System.exit(1);
        }
        System.out.println("hello");
    }
}
