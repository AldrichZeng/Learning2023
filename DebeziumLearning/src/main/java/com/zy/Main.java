package com.zy;

import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import io.debezium.engine.ChangeEvent;
import io.debezium.engine.DebeziumEngine;
import io.debezium.engine.format.Json;

/**
 * @author 匠承
 * @Date: 2023/6/24 14:33
 */
public class Main {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.setProperty("snapshot.mode", "never");
        props.setProperty("database.user", "jctest");
        props.setProperty("database.password", "DWzengyao1234");
        props.setProperty("offset.flush.interval.ms", "300000");
        props.setProperty("database.port", "5432");
        props.setProperty("slot.name", "di_slot");
        props.setProperty("publication.name", "di_pub");
        props.setProperty("tombstones.on.delete", "false");
        props.setProperty("database.hostname", "pgm-uf6780sk00vfe752co.pg.rds.aliyuncs.com");
        props.setProperty("table.include.list", "public.debezium_test");
        props.setProperty("database.dbname", "jctest");
        props.setProperty("database.history", "io.debezium.relational.history.MemoryDatabaseHistory");
        props.setProperty("decimal.handling.mode", "string");
        props.setProperty("offset.flush.size.ms", "86400000");
        props.setProperty("plugin.name", "pgoutput");
        props.setProperty("name", "pgm-uf6780sk00vfe752co.pg.rds.aliyuncs.com_jctest_di_slot");
        props.setProperty("offset.storage", "com.zy.InitializableOffsetBackingStore");
        props.setProperty("database.server.name", "pgm-uf6780sk00vfe752co.pg.rds.aliyuncs.com_jctest_di_slot");
        props.setProperty("publication.autocreate.mode", "filtered");
        props.setProperty("offset.initial.position.json", "{\"enginePostions\":{\"pgm-uf6780sk00vfe752co.pg.rds.aliyuncs.com_jctest_di_slot\":{\"sourceOffset\":{\"ts_usec\":0,\"lsn\":1072265630552},\"sourcePartition\":{\"server\":\"pgm-uf6780sk00vfe752co.pg.rds.aliyuncs.com_jctest_di_slot\"}}}}");
        props.setProperty("connector.class", "io.debezium.connector.postgresql.PostgresConnector");
        DebeziumEngine<ChangeEvent<String, String>> engine = DebeziumEngine.create(Json.class)
                .using(props)
                .notifying(record -> System.out.println("zengyao log: " + record))
                .build();
        ExecutorService debeziumExecutor = new ThreadPoolExecutor(
                1,
                1,
                0L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());
        debeziumExecutor.execute(engine);
    }
}