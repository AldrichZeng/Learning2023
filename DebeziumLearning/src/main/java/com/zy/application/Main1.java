package com.zy.application;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.zy.MainSourceRecord;
import com.zy.MyOffsetCommitPolicy;
import io.debezium.embedded.Connect;
import io.debezium.engine.ChangeEvent;
import io.debezium.engine.DebeziumEngine;
import org.apache.kafka.connect.source.SourceRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 匠承
 * @Date: 2024/11/20 20:18
 */
public class Main1 {

    static String username = "jiangcheng";
    static String password = "DWzengyao123";
    static String host = "pgm-uf655ms44283f6266o.pg.rds.aliyuncs.com";
    static String db = "jiangcheng";
    static String port = "5432";

    // ==================
    static String slot = "di_slot1";
    static String pub = "di_pub1";
    static String tableList = "public.streamx_reader_test,public.streamx_reader_test_p1,public.streamx_reader_test_p2,public.streamx_reader_test_p3";

    private static final Logger logger = LoggerFactory.getLogger(MainSourceRecord.class);

    public static void main(String[] args) throws IOException {
        Properties props = new Properties();

        props.setProperty("database.user", username);
        props.setProperty("database.password", password);
        props.setProperty("database.port", port);
        props.setProperty("database.hostname", host);
        props.setProperty("database.dbname", db);
        props.setProperty("slot.name", slot);
        props.setProperty("publication.name", pub);
        props.setProperty("table.include.list", tableList);

        props.setProperty("snapshot.mode", "never");
        props.setProperty("offset.flush.interval.ms", "300000");
        //props.setProperty("tombstones.on.delete", "false");
        props.setProperty("database.history", "io.debezium.relational.history.MemoryDatabaseHistory");
        props.setProperty("decimal.handling.mode", "string");
        // 在此无效
        props.setProperty("offset.flush.size.ms", "10");
        props.setProperty("plugin.name", "pgoutput");
        // engineName
        props.setProperty("name", host + "_" + slot);
        // 持久化连接器的Offset的类
        props.setProperty("offset.storage", "com.zy.InitializableOffsetBackingStore");
        props.setProperty("database.server.name", host + "_" + slot);
        props.setProperty("publication.autocreate.mode", "filtered");
        // 从哪个位点开始读
        //props.setProperty("offset.initial.position.json", initialPosition);
        props.setProperty("connector.class", "io.debezium.connector.postgresql.PostgresConnector");
        props.setProperty("heartbeat.interval.ms", "5000000");
        props.setProperty("truncate.handling.mode", "include");
        props.setProperty("include.schema.changes", "true");
        //props.setProperty("schema.refresh.mode", "columns_diff_exclude_unchanged_toast");

        DebeziumEngine<ChangeEvent<SourceRecord, SourceRecord>> engine1 = DebeziumEngine.create(Connect.class)
                .using(props)
                .using(new MyOffsetCommitPolicy())
                .notifying(new MainSourceRecord.RecordConsumer())
                .build();

        ExecutorService debeziumExecutor = new ThreadPoolExecutor(
                6,
                6,
                0L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());
        debeziumExecutor.execute(engine1);
    }
}
