package com.zy;

import io.debezium.connector.postgresql.connection.Lsn;

/**
 * @author 匠承
 * @Date: 2024/5/5 15:20
 */
public class LsnUtils {
    public static void main(String[] args) {

        if (args.length == 0) {
            throw new RuntimeException("请输入lsn");
        }
        if (args.length == 1) {
            String lsn = args[0];

            if (lsn.contains("/")) {
                System.out.println(lsn + "转化为Long：");
                System.out.println(toLong(lsn));
            } else {
                System.out.println(lsn + "转化后：");
                System.out.println(toString(lsn));
            }
        }

        if (args.length == 2) {
            String lsn1 = args[0];
            String lsn2 = args[1];

            Long lsn1Long = null;
            Long lsn2Long = null;
            if (lsn1.contains("/")) {
                lsn1Long = toLong(lsn1);
            } else {
                lsn1Long = Long.valueOf(lsn1);
            }
            if (lsn2.contains("/")) {
                lsn2Long = toLong(lsn2);
            } else {
                lsn2Long = Long.valueOf(lsn2);
            }
            if (lsn1Long.compareTo(lsn2Long) > 0) {
                System.out.println(lsn1 + " > " + lsn2);
            } else if (lsn1Long.compareTo(lsn2Long) == 0) {
                System.out.println(lsn1 + " == " + lsn2);
            } else {
                System.out.println(lsn1 + " < " + lsn2);
            }
        }
    }

    public static Long toLong(String lsn) {
        return Lsn.valueOf(lsn).asLong();
    }

    public static String toString(String lsn) {
        return Lsn.valueOf(Long.valueOf(lsn)).asString();
    }
}
