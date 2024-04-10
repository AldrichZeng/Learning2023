package com.zy;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Logger;
import com.jcraft.jsch.Session;

/**
 * @author 匠承
 * @Date: 2024/4/7 15:29
 */
public class JschTest {
    public static void main(String[] args) throws JSchException {

        String username = args[0];
        String host = args[1];
        String port = args[2];
        String password = args[3];
        JSch jsch = new JSch();
        JSch.setLogger(new Logger() {

            public boolean isEnabled(int level) {
                return true;
            }

            public void log(int level, String message) {
                switch (level) {
                    case Logger.DEBUG:
                        System.out.println("DEBUG: " + message); // 或者使用您的日志框架记录 debug 日志
                        break;
                    case Logger.INFO:
                        System.out.println("INFO: " + message);
                        break;
                    case Logger.WARN:
                        System.out.println("WARN: " + message);
                        break;
                    case Logger.ERROR:
                        System.out.println("ERROR: " + message);
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown log level: " + level);
                }
            }
        });
        Session session = jsch.getSession(username, host, Integer.parseInt(port));
        session.setPassword(password);
        System.out.println(String.format("username: %s, host: %s, port: %s", username, host, port));
        session.setConfig("StrictHostKeyChecking", "no");
        session.connect();

        System.out.println("session.connect ");
        Channel channel = session.openChannel("exec");

        channel.disconnect();
        session.disconnect();
    }
}
