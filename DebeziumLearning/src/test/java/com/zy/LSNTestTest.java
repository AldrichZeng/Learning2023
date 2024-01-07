package com.zy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSON;

import io.debezium.connector.postgresql.connection.Lsn;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

/**
 * @author 匠承
 * @Date: 2023/7/13 09:59
 */
public class LSNTestTest {

    @Test
    public void test7() {
        String a = "{\n"
                + "    \"enginePostions\":\n"
                + "    {\n"
                + "        \"pgm-uf6780sk00vfe752co.pg.rds.aliyuncs.com_jctest_di_slot_2611\":\n"
                + "        {\n"
                + "            \"sourceOffset\":\n"
                + "            {\n"
                + "                \"lsn_proc\": 1844084481032,\n"
                + "                \"lsn_commit\": 1844084481032,\n"
                + "                \"lsn\": 1844084481032,\n"
                + "                \"txId\": 7286452,\n"
                + "                \"ts_usec\": 1701335515834435\n"
                + "            },\n"
                + "            \"sourcePartition\":\n"
                + "            {\n"
                + "                \"server\": \"pgm-uf6780sk00vfe752co.pg.rds.aliyuncs.com_jctest_di_slot_2611\"\n"
                + "            }\n"
                + "        }\n"
                + "    },\n"
                + "    \"engineWindows\":\n"
                + "    {\n"
                + "        \"pgm-uf6780sk00vfe752co.pg.rds.aliyuncs.com_jctest_di_slot_2611\":\n"
                + "        [\n"
                + "            {\n"
                + "                \"sourceOffset\":\n"
                + "                {\n"
                + "                    \"lsn_proc\": 1844017370256,\n"
                + "                    \"lsn_commit\": 1843732376048,\n"
                + "                    \"lsn\": 1844017370256,\n"
                + "                    \"txId\": 7286190,\n"
                + "                    \"ts_usec\": 1701334369345933\n"
                + "                },\n"
                + "                \"sourcePartition\":\n"
                + "                {\n"
                + "                    \"server\": \"pgm-uf6780sk00vfe752co.pg.rds.aliyuncs.com_jctest_di_slot_2611\"\n"
                + "                },\n"
                + "                \"timestampMills\": 1701334369345\n"
                + "            },\n"
                + "            {\n"
                + "                \"sourceOffset\":\n"
                + "                {\n"
                + "                    \"lsn_proc\": 1844034148104,\n"
                + "                    \"lsn_commit\": 1844017376600,\n"
                + "                    \"lsn\": 1844034148104,\n"
                + "                    \"txId\": 7286298,\n"
                + "                    \"ts_usec\": 1701334833858784\n"
                + "                },\n"
                + "                \"sourcePartition\":\n"
                + "                {\n"
                + "                    \"server\": \"pgm-uf6780sk00vfe752co.pg.rds.aliyuncs.com_jctest_di_slot_2611\"\n"
                + "                },\n"
                + "                \"timestampMills\": 1701334833858\n"
                + "            },\n"
                + "            {\n"
                + "                \"sourceOffset\":\n"
                + "                {\n"
                + "                    \"lsn_proc\": 1844050923112,\n"
                + "                    \"lsn_commit\": 1844050920336,\n"
                + "                    \"lsn\": 1844050923112,\n"
                + "                    \"txId\": 7286349,\n"
                + "                    \"ts_usec\": 1701335039300867\n"
                + "                },\n"
                + "                \"sourcePartition\":\n"
                + "                {\n"
                + "                    \"server\": \"pgm-uf6780sk00vfe752co.pg.rds.aliyuncs.com_jctest_di_slot_2611\"\n"
                + "                },\n"
                + "                \"timestampMills\": 1701335039300\n"
                + "            },\n"
                + "            {\n"
                + "                \"sourceOffset\":\n"
                + "                {\n"
                + "                    \"lsn_proc\": 1844084474336,\n"
                + "                    \"lsn_commit\": 1844050923288,\n"
                + "                    \"lsn\": 1844084474336,\n"
                + "                    \"txId\": 7286451,\n"
                + "                    \"ts_usec\": 1701335514766348\n"
                + "                },\n"
                + "                \"sourcePartition\":\n"
                + "                {\n"
                + "                    \"server\": \"pgm-uf6780sk00vfe752co.pg.rds.aliyuncs.com_jctest_di_slot_2611\"\n"
                + "                },\n"
                + "                \"timestampMills\": 1701335514766\n"
                + "            }\n"
                + "        ]\n"
                + "    }\n"
                + "}";
        PostgresPosition p = JSON.parseObject(a, PostgresPosition.class);
        System.out.println(p);
        Map map = p.getEnginePostions();
        System.out.println(p.getSourceOffset("pgm-uf6780sk00vfe752co.pg.rds.aliyuncs.com_jctest_di_slot_2611"));
    }

    @Test
    public void test4() {
        System.out.println(Lsn.valueOf("1D2/F2001240").asLong());
        System.out.println(Lsn.valueOf("1D2/F5000518").asLong());
        System.out.println(Lsn.valueOf("1AD/5C001C08").asLong() < Lsn.valueOf("1AD/63001E30").asLong());
        System.out.println(Lsn.valueOf("1AD/5C001C08").compareTo(Lsn.valueOf("1AD/63001E30")));
    }

    @Test
    public void test() {
        Lsn lsn = Lsn.valueOf(1844285819448L);
        System.out.println(lsn.asString());

        System.out.println(MainSourceRecord.initialPosition);
    }

    @Test
    public void test1() {
        Lsn lsn = Lsn.valueOf(1161268569152L);
        System.out.println(lsn.asString());
        System.out.println(lsn.asLong());

        System.out.println("==================");
        ByteBuffer buf = ByteBuffer.allocate(8);
        buf.putLong(1159741874432L);
        buf.position(0);
        System.out.println(buf.position());
        int a = buf.getInt();
        System.out.println(a);
        System.out.println(String.format("%X", a));
        System.out.println(buf.position());
        int b = buf.getInt();
        System.out.println(b);
        System.out.println(String.format("%X", b));
    }

    @Test
    public void test2() throws IOException {
        ClassPathResource classPathResource = new ClassPathResource("startPosition.json");
        InputStream inputStream = classPathResource.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        String initialPosition = sb.toString();
        br.close();

        System.out.println(initialPosition);
    }

    @Test
    public void test3() {
        Lsn lsn = Lsn.valueOf(1892587408656L);
        System.out.println(lsn.asString());
        System.out.println(lsn.asLong());

        lsn = Lsn.valueOf("112/AB001938");
        System.out.println(lsn.asLong());
        System.out.println(lsn.asString());
    }

    @Test
    public void test5() {
        Lsn lsn = Lsn.valueOf("1B8/C90052F8");
        System.out.println(lsn.asLong());//1162829616128
    }

    @Test
    public void test6() {
        AtomicReference<Boolean> successDeleteSlot = new AtomicReference<>(true);
        successDeleteSlot.set(false);
        System.out.println(successDeleteSlot.get());
        if (!successDeleteSlot.get()) {
            System.out.println("hallo");
        }

        AtomicReference<String> str = new AtomicReference<>();
        System.out.println(str);
        Assert.assertNull(str.get());
        str.set("abc");
        System.out.println(str.get());
    }

    @Test
    public void test8() {

        Map<String, Object> map = new HashMap<>();
        String a = null;
        System.out.println(StringUtils.isBlank(a));
        a = "";
        System.out.println(StringUtils.isBlank(a));

        a = a + "";
        System.out.println(a);
        System.out.println(a.length());

        String x = (String) map.get("a");
        System.out.println(x);
        System.out.println(x.length());
    }

    @Test
    public void test19() {
        String jdbcUrl = "jdbc:oceanbase://t534p4fky9fgg.oceanbase.aliyuncs.com:3306/?yearIsDateType=false&zeroDateTimeBehavior=convertToNull&rewriteBatchedStatements=true&tinyInt1isBit=false";
        Pattern pattern = Pattern.compile("//([\\w\\.\\-]+:\\d+)/([\\w]+)\\?");
        Matcher matcher = pattern.matcher(jdbcUrl);
        if (matcher.find()) {
            System.out.println("hahaha");
        } else {
            System.out.println("abc");
        }
    }

    @Test
    public void test20() {
        String jdbcUrl = "jdbc:postgresql://10.0.0.1:3306/db?a=b";
        System.out.println(jdbcUrl.indexOf("?"));//34
        System.out.println(jdbcUrl.substring(0, 35));
    }




    @Test
    public void test21() {
        String url = "";
        int separator = url.indexOf("//");
        String urlSecondPart = url.substring(separator + 2);
        int dbIndex = urlSecondPart.indexOf("/");
        int paramIndex = urlSecondPart.indexOf("?");
        String hostAddressesString;
        String additionalParameters;
        if ((dbIndex < paramIndex && dbIndex < 0) || (dbIndex > paramIndex && paramIndex > -1)) {
            hostAddressesString = urlSecondPart.substring(0, paramIndex);
            additionalParameters = urlSecondPart.substring(paramIndex);
        } else if (dbIndex < paramIndex || dbIndex > paramIndex) {
            hostAddressesString = urlSecondPart.substring(0, dbIndex);
            additionalParameters = urlSecondPart.substring(dbIndex);
        } else {
            hostAddressesString = urlSecondPart;
            additionalParameters = null;
        }

        parseHost(hostAddressesString);

        Pattern URL_PARAMETER = Pattern.compile("(\\/([^\\?]*))?(\\?(.+))*", Pattern.DOTALL);
        String database;
        Map<String, String> properties = null;
        if (additionalParameters != null) {
            Matcher matcher = URL_PARAMETER.matcher(additionalParameters);
            boolean unused = matcher.find();
            database = matcher.group(2);

            properties = parseProperties(matcher.group(4));
            if (database != null && database.isEmpty()) {
                database = null;
            }
        } else {
            database = null;
        }
        if (database != null && !URL_PARAMETER.matcher(database).matches()) {
            System.out.println("invalid database in url: " + database);
        }
        System.out.println("database:"+database);
        System.out.println("properties:"+properties);
    }

    @Test
    public void test22(){
        String a = "abc:100,abc:100,abc,,,d";
        String[] hostAndPorts = a.split(",");
        for (int i = 0; i < hostAndPorts.length; i++) {
            System.out.println(hostAndPorts[i]);
            System.out.println(i);
        }

        String b = "a,,b,,c";

    }

    private void parseHost(String hostAddressesString){
        String[] hostAndPorts = hostAddressesString.split(",");





        Pattern HOST_PATTERN = Pattern.compile("^[a-zA-Z0-9_\\-\\.:\\[\\],]+$");
        int portIndex = hostAddressesString.lastIndexOf(":");
        int bracketIndex = hostAddressesString.indexOf("]");
        String hostString;
        String portString;
        if ((bracketIndex > -1 && bracketIndex < portIndex) ||
                (portIndex > -1 && hostAddressesString.indexOf(":") == portIndex)) {
            hostString = hostAddressesString.substring(0, portIndex);
            portString = hostAddressesString.substring(portIndex + 1);
        } else {
            hostString = hostAddressesString;
            portString = null;
        }

        if (!HOST_PATTERN.matcher(hostString).matches()) {
            System.out.println("invalid host '" + hostString + "' in url: " + hostAddressesString);
            // throw
        }
        System.out.println(hostString);
        if (portString != null && !portString.isEmpty()) {
            int port = -1;
            try {
                port = Integer.parseInt(portString);
            } catch (NumberFormatException e) {
                System.out.println("invalid host in url: " + hostAddressesString);
                // throw
            }

            if (port < 0 || port > 65535) {
                System.out.println("invalid port " + portString + " in url: " + portString);
                // throw
            }
        } else {
            System.out.println("port = -1");
        }
    }

    private Map<String, String> parseProperties(String urlParameters){
        Map<String, String> properties = new HashMap<>();
        if (urlParameters != null && !urlParameters.isEmpty()) {
            String[] parameters = urlParameters.split("&");
            for (String parameter : parameters) {
                int pos = parameter.indexOf('=');
                if (pos == -1) {
                    if (!properties.containsKey(parameter)) {
                        properties.put(parameter.trim(), "");
                    }
                } else {
                    properties.put(parameter.substring(0, pos).trim(), parameter.substring(pos + 1).trim());
                }
            }
        }
        return properties;

    }

}