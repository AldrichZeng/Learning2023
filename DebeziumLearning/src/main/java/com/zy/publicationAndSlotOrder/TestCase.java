package com.zy.publicationAndSlotOrder;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.zy.MyOffsetCommitPolicy;
import com.zy.RecordConsumer;
import com.zy.Utils;
import com.zy.model.ReplicationSlot;
import io.debezium.engine.ChangeEvent;
import io.debezium.engine.DebeziumEngine;
import io.debezium.engine.format.Json;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 匠承
 * @Date: 2024/9/8 12:11
 */
@Slf4j
public class TestCase {
    String slot = "zengyao_slot";
    String pub = "zengyao_pub";
    List<String> tables = new ArrayList<String>() {
        {
            add("public.test_bit_n");
        }
    };
    String host;
    String port;
    String username;
    String password;
    String database;
    Connection conn;

    public TestCase() {
        JSONObject object = Utils.getParameter("postgresql.json");
        host = object.getString("host");
        port = object.getString("port");
        username = object.getString("username");
        password = object.getString("password");
        database = object.getString("database");

        conn = Utils.getConnection(host, database, username, password, Integer.parseInt(port), 10000);
    }

    public Properties generateProps() {
        Properties props = new Properties();
        props.setProperty("database.hostname", host);
        props.setProperty("database.port", port);
        props.setProperty("database.user", username);
        props.setProperty("database.password", password);
        props.setProperty("database.dbname", database);

        props.setProperty("snapshot.mode", "never");
        props.setProperty("offset.flush.interval.ms", "300000");
        // 复制槽名称
        props.setProperty("slot.name", slot);
        // publication
        props.setProperty("publication.name", pub);
        props.setProperty("tombstones.on.delete", "false");
        // 表名
        log.info("tables: {}", tables.stream().collect(Collectors.joining(",")));
        props.setProperty("table.include.list", tables.stream().collect(Collectors.joining(",")));
        props.setProperty("database.history", "io.debezium.relational.history.MemoryDatabaseHistory");
        props.setProperty("decimal.handling.mode", "string");
        // 在此无效
        props.setProperty("offset.flush.size.ms", "86400000");
        props.setProperty("plugin.name", "pgoutput");
        // engineName
        props.setProperty("name", host + "_" + database + "_" + slot);
        // 持久化连接器的Offset的类
        props.setProperty("offset.storage", "com.zy.InitializableOffsetBackingStore");
        props.setProperty("database.server.name", host + "_" + database + "_" + slot);
        //props.setProperty("publication.autocreate.mode", "filtered");
        // 从哪个位点开始读
        //props.setProperty("offset.initial.position.json", initialPosition);
        props.setProperty("connector.class", "io.debezium.connector.postgresql.PostgresConnector");
        return props;
    }

    public static void main(String[] args) {
        new TestCase().test1();
    }

    public void test1() {
        ReplicationSlot replicationSlot = Utils.slotInfo(conn, slot);
        log.info("replicationSlot, restart_lsn: {}, confirmed_flush_lsn: {}", replicationSlot.getRestartLsn(), replicationSlot.getConfirmFlushLsn());
        DebeziumEngine<ChangeEvent<String, String>> engine = DebeziumEngine.create(Json.class)
                .using(new TestCase().generateProps())
                .using(new MyOffsetCommitPolicy())
                .notifying(new RecordConsumer())
                .build();
        ExecutorService debeziumExecutor = new ThreadPoolExecutor(
                1,
                1,
                0L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());
        debeziumExecutor.execute(engine);

        log.info("end");
    }
}
