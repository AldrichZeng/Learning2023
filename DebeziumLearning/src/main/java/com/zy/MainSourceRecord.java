package com.zy;

import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import io.debezium.connector.postgresql.SourceInfo;
import io.debezium.data.Envelope;
import io.debezium.embedded.Connect;
import io.debezium.embedded.EmbeddedEngineChangeEvent;
import io.debezium.engine.ChangeEvent;
import io.debezium.engine.DebeziumEngine;
import org.apache.kafka.connect.data.Field;
import org.apache.kafka.connect.data.Struct;
import org.apache.kafka.connect.source.SourceRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 匠承
 * @Date: 2023/7/13 10:18
 */
public class MainSourceRecord {
    private static final Logger logger = LoggerFactory.getLogger(MainSourceRecord.class);

    public static String lsnInHex = "10E/BE0BD400";
    public static String lsnInDec = Utils.lsnHexToDec(lsnInHex);

    public static String initialPosition = "{\n"
            + "  \"enginePostions\": {\n"
            + "    \"pgm-uf6780sk00vfe752co.pg.rds.aliyuncs.com_jctest_di_slot\": {\n"
            + "      \"sourceOffset\": {\n"
            + "        \"ts_usec\": 1689573489732000,\n"
            + "        \"lsn\": " + "1183688263824" + ",\n"
            + "        \"txId\": " + "4579394"
            + "      },\n"
            + "      \"sourcePartition\": {\n"
            + "        \"server\": \"pgm-uf6780sk00vfe752co.pg.rds.aliyuncs.com_jctest_di_slot\"\n"
            + "      }\n"
            + "    }\n"
            + "  }\n"
            + "}";

    public static void main(String[] args) throws IOException {
        //Scanner scanner = new Scanner(System.in);
        //if (scanner.hasNext()) {
        //    int a = scanner.nextInt();
        //    if (a == 5) {
        //        return;
        //    }
        //}

        Properties props = new Properties();
        props.setProperty("snapshot.mode", "never");
        props.setProperty("database.user", "jctest");
        props.setProperty("database.password", "DWzengyao1234");
        props.setProperty("offset.flush.interval.ms", "300000");
        props.setProperty("database.port", "5432");
        // 复制槽名称
        props.setProperty("slot.name", "di_slot_mac");
        // publication
        props.setProperty("publication.name", "di_pub_mac");
        props.setProperty("tombstones.on.delete", "false");
        props.setProperty("database.hostname", "pgm-uf6780sk00vfe752co.pg.rds.aliyuncs.com");
        // 表名
        //props.setProperty("table.include.list", Utils.generateTables(1, 1000));
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
        //props.setProperty("heartbeat.interval.ms", "5000");
        props.setProperty("truncate.handling.mode", "include");
        //props.setProperty("schema.refresh.mode", "columns_diff_exclude_unchanged_toast");

        //props.setProperty("table.include.list", Utils.generateTables(1,100, "bigschema"));
        props.setProperty("table.include.list", Constants.tables167withTest());
        props.setProperty("table.include.list", Constants.tables167withTest());
        DebeziumEngine<ChangeEvent<SourceRecord, SourceRecord>> engine1 = DebeziumEngine.create(Connect.class)
                .using(props)
                .using(new MyOffsetCommitPolicy())
                .notifying(new MainSourceRecord.RecordConsumer())
                .build();

        //props.setProperty("table.include.list", Utils.generateTables(501, 1000));
        //DebeziumEngine<ChangeEvent<SourceRecord, SourceRecord>> engine2 = DebeziumEngine.create(Connect.class)
        //        .using(props)
        //        .using(new MyOffsetCommitPolicy())
        //        .notifying(new MainSourceRecord.RecordConsumer())
        //        .build();
        //
        //props.setProperty("table.include.list", Utils.generateTables(1001, 1500));
        //DebeziumEngine<ChangeEvent<SourceRecord, SourceRecord>> engine3 = DebeziumEngine.create(Connect.class)
        //        .using(props)
        //        .using(new MyOffsetCommitPolicy())
        //        .notifying(new MainSourceRecord.RecordConsumer())
        //        .build();
        //
        //props.setProperty("table.include.list", Utils.generateTables(1501, 2000));
        //DebeziumEngine<ChangeEvent<SourceRecord, SourceRecord>> engine4 = DebeziumEngine.create(Connect.class)
        //        .using(props)
        //        .using(new MyOffsetCommitPolicy())
        //        .notifying(new MainSourceRecord.RecordConsumer())
        //        .build();
        //
        //props.setProperty("table.include.list", Utils.generateTables(2001, 2500));
        //DebeziumEngine<ChangeEvent<SourceRecord, SourceRecord>> engine5 = DebeziumEngine.create(Connect.class)
        //        .using(props)
        //        .using(new MyOffsetCommitPolicy())
        //        .notifying(new MainSourceRecord.RecordConsumer())
        //        .build();
        //
        //props.setProperty("table.include.list", Utils.generateTables(2501, 3000));
        //DebeziumEngine<ChangeEvent<SourceRecord, SourceRecord>> engine6 = DebeziumEngine.create(Connect.class)
        //        .using(props)
        //        .using(new MyOffsetCommitPolicy())
        //        .notifying(new MainSourceRecord.RecordConsumer())
        //        .build();

        ExecutorService debeziumExecutor = new ThreadPoolExecutor(
                6,
                6,
                0L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());
        debeziumExecutor.execute(engine1);
        //debeziumExecutor.execute(engine2);
        //debeziumExecutor.execute(engine3);
        //debeziumExecutor.execute(engine4);
        //debeziumExecutor.execute(engine5);
        //debeziumExecutor.execute(engine6);

        // 单线程
        //engine.run();

        logger.info("zengyao log execute end");
    }

    public static class RecordConsumer implements DebeziumEngine.ChangeConsumer<ChangeEvent<SourceRecord, SourceRecord>> {
        public boolean supportsTombstoneEvents() {
            return true;
        }

        @Override
        public void handleBatch(List<ChangeEvent<SourceRecord, SourceRecord>> records, DebeziumEngine.RecordCommitter<ChangeEvent<SourceRecord, SourceRecord>> committer) throws InterruptedException {
            for (ChangeEvent<SourceRecord, SourceRecord> record : records) {
                Function<ChangeEvent<SourceRecord, SourceRecord>, SourceRecord> from = (events) -> {
                    return ((EmbeddedEngineChangeEvent) events).sourceRecord();
                };
                SourceRecord rawRecord = from.apply(record);

                logger.info("rawRecord: {}", rawRecord);
                logger.info("rawRecord.value(): {}", rawRecord.value());
                logger.info("rawRecord.sourceOffset: {}", rawRecord.sourceOffset());
                logger.info("rawRecord.sourcePartition: {}", rawRecord.sourcePartition());
                Envelope.Operation op = Envelope.operationFor(rawRecord);
                logger.info("op: {}", op);
                if (op == null) {
                    logger.info("it is heartbeat");
                    Struct value = (Struct) rawRecord.value();
                    Long eventTimestampMills = value.getInt64(SourceInfo.TIMESTAMP_KEY);
                    logger.info("heartbeat value = {}", value);
                    logger.info("heartbeat eventTimestampMills: {}", eventTimestampMills);
                    logger.info("heartbeat schema: {}", value.schema());
                } else {
                    Struct value = (Struct) rawRecord.value();
                    logger.info("value = {}", value);
                    Struct source = value.getStruct(Envelope.FieldName.SOURCE);
                    Long eventTimestampMills = source.getInt64(SourceInfo.TIMESTAMP_KEY);
                    logger.info("schema: {}", value.schema());
                    logger.info("eventTimestampMills: {}", eventTimestampMills);
                    for (Field field : source.schema().fields()) {
                        // 每一个field
                        logger.info("schema.type: {}", field.schema().type());
                        logger.info("schema name: {}", field.schema().name());
                        logger.info("field name: {}", field.name());
                        logger.info("field toString: {}", field.toString());
                    }
                }

                committer.markProcessed(record);
            }
            committer.markBatchFinished();
        }
    }
}
