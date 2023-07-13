package com.zy;

import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import io.debezium.engine.ChangeEvent;
import io.debezium.engine.DebeziumEngine;
import io.debezium.engine.format.Json;
import io.debezium.engine.spi.OffsetCommitPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 匠承
 * @Date: 2023/6/24 14:33
 */
public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static int confirm_interval_second = 600;

    public static String lsnInHex = "10E/5F004F90";
    public static String lsnInDec = Utils.lsnHexToDec(lsnInHex);

    public static String initialPosition = "{\n"
            + "  \"enginePostions\": {\n"
            + "    \"pgm-uf6780sk00vfe752co.pg.rds.aliyuncs.com_jctest_di_slot\": {\n"
            + "      \"sourceOffset\": {\n"
            + "        \"ts_usec\": 0,\n"
            + "        \"lsn\": " + "1161318921160" + "\n"
            + "      },\n"
            + "      \"sourcePartition\": {\n"
            + "        \"server\": \"pgm-uf6780sk00vfe752co.pg.rds.aliyuncs.com_jctest_di_slot\"\n"
            + "      }\n"
            + "    }\n"
            + "  }\n"
            + "}";

    public static void main(String[] args) throws IOException {
        Properties props = new Properties();
        props.setProperty("snapshot.mode", "never");
        props.setProperty("database.user", "jctest");
        props.setProperty("database.password", "DWzengyao1234");
        props.setProperty("offset.flush.interval.ms", "300000");
        props.setProperty("database.port", "5432");
        // 复制槽名称
        props.setProperty("slot.name", "di_slot");
        // publication
        props.setProperty("publication.name", "di_pub");
        props.setProperty("tombstones.on.delete", "false");
        props.setProperty("database.hostname", "pgm-uf6780sk00vfe752co.pg.rds.aliyuncs.com");
        // 表名
        props.setProperty("table.include.list", "public.debezium_test");
        props.setProperty("database.dbname", "jctest");
        props.setProperty("database.history", "io.debezium.relational.history.MemoryDatabaseHistory");
        props.setProperty("decimal.handling.mode", "string");
        // 在此无效
        props.setProperty("offset.flush.size.ms", "86400000");
        props.setProperty("plugin.name", "pgoutput");
        // engineName
        props.setProperty("name", "pgm-uf6780sk00vfe752co.pg.rds.aliyuncs.com_jctest_di_slot");
        // 持久化连接器的Offset的类
        props.setProperty("offset.storage", "com.zy.InitializableOffsetBackingStore");
        props.setProperty("database.server.name", "pgm-uf6780sk00vfe752co.pg.rds.aliyuncs.com_jctest_di_slot");
        props.setProperty("publication.autocreate.mode", "filtered");
        // 从哪个位点开始读
        props.setProperty("offset.initial.position.json", initialPosition);
        props.setProperty("connector.class", "io.debezium.connector.postgresql.PostgresConnector");
        DebeziumEngine<ChangeEvent<String, String>> engine = DebeziumEngine.create(Json.class)
                .using(props)
                .using(new MyOffsetCommitPolicy())
                .notifying(new MyConsumer())
                .build();
        ExecutorService debeziumExecutor = new ThreadPoolExecutor(
                1,
                1,
                0L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());
        debeziumExecutor.execute(engine);
    }

    public static class MyOffsetCommitPolicy implements OffsetCommitPolicy {
        @Override
        public boolean performCommit(long numberOfMessagesSinceLastCommit, Duration timeSinceLastCommit) {
            logger.info("numberOfMessagesSinceLastCommit: " + numberOfMessagesSinceLastCommit + "\ttimeSinceLastCommit: " + timeSinceLastCommit.getSeconds());
            // 定义超过多长时间则confirm LSN
            if (timeSinceLastCommit.getSeconds() > confirm_interval_second) {
                logger.info("commit LSN");
                return true;
            } else {
                logger.info("Do not commit LSN");
                return false;
            }
        }
    }

    public static class MyConsumer implements Consumer<ChangeEvent<String, String>> {
        @Override
        public void accept(ChangeEvent<String, String> stringStringChangeEvent) {
            String value = stringStringChangeEvent.value();
            logger.info("读取到变更: \n" + Utils.JSONFormat(value));
        }
    }

}