package com.zy;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import io.debezium.connector.postgresql.PostgresOffsetContext;
import io.debezium.connector.postgresql.SourceInfo;
import io.debezium.connector.postgresql.connection.Lsn;
import io.debezium.data.Envelope;
import io.debezium.embedded.Connect;
import io.debezium.engine.ChangeEvent;
import io.debezium.engine.DebeziumEngine;
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
            + "        \"ts_usec\": 0,\n"
            //+ "        \"lsn\": " + "1183688263824" + ",\n"
            //+ "        \"txId\": " + "4579394" + ",\n"
            + "        \"lsn_proc\": " + "1830192984535"
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

        // 复制槽名称
        props.setProperty("slot.name", "di_dataxcdc_slot1");
        // publication
        props.setProperty("publication.name", "di_dataxcdc_pub");
        props.setProperty("table.include.list", "public.test1");

        props.setProperty("snapshot.mode", "never");
        props.setProperty("database.user", "jiangcheng");
        props.setProperty("database.password", "DWzengyao123");
        props.setProperty("offset.flush.interval.ms", "300000");
        props.setProperty("database.port", "5432");
        //props.setProperty("tombstones.on.delete", "false");
        props.setProperty("database.hostname", "pgm-2ze5rp8vl51319s4go.pg.rds.aliyuncs.com");
        // 表名
        //props.setProperty("table.include.list", Utils.generateTables(1, 1000));
        props.setProperty("database.dbname", "jiangcheng");
        props.setProperty("database.history", "io.debezium.relational.history.MemoryDatabaseHistory");
        props.setProperty("decimal.handling.mode", "string");
        // 在此无效
        props.setProperty("offset.flush.size.ms", "86400000");
        props.setProperty("plugin.name", "pgoutput");
        // engineName
        props.setProperty("name", "pgm-2ze5rp8vl51319s4go.pg.rds.aliyuncs.com_di_dataxcdc_slot");
        // 持久化连接器的Offset的类
        props.setProperty("offset.storage", "com.zy.InitializableOffsetBackingStore");
        props.setProperty("database.server.name", "pgm-2ze5rp8vl51319s4go.pg.rds.aliyuncs.com_di_dataxcdc_slot");
        props.setProperty("publication.autocreate.mode", "filtered");
        // 从哪个位点开始读
        //props.setProperty("offset.initial.position.json", initialPosition);
        props.setProperty("connector.class", "io.debezium.connector.postgresql.PostgresConnector");
        props.setProperty("heartbeat.interval.ms", "5000");
        props.setProperty("truncate.handling.mode", "include");
        props.setProperty("include.schema.changes", "true");
        //props.setProperty("schema.refresh.mode", "columns_diff_exclude_unchanged_toast");

        //props.setProperty("table.include.list", Utils.generateTables(1,100, "bigschema"));
        //props.setProperty("table.include.list", Constants.tables167withTest());
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

        logger.info("zengyao log execute end");
    }

    static int countRecord = 0;

    //static DebeziumEngine.RecordCommitter<ChangeEvent<SourceRecord, SourceRecord>> COMMITTER;

    public static class RecordConsumer implements DebeziumEngine.ChangeConsumer<ChangeEvent<SourceRecord, SourceRecord>> {
        public boolean supportsTombstoneEvents() {
            return true;
        }

        @Override
        public void handleBatch(List<ChangeEvent<SourceRecord, SourceRecord>> records, DebeziumEngine.RecordCommitter<ChangeEvent<SourceRecord, SourceRecord>> committer) throws InterruptedException {
            int i = 0;
            for (ChangeEvent<SourceRecord, SourceRecord> record : records) {
                logger.info("i = {}", i++);
                //Function<ChangeEvent<SourceRecord, SourceRecord>, SourceRecord> from = (events) -> {
                //    return ((EmbeddedEngineChangeEvent) events).sourceRecord();
                //};
                //SourceRecord rawRecord = from.apply(record);

                logger.info("record: {}", record);
                SourceRecord rawRecord = record.value();
                logger.info("rawRecord: {}", rawRecord);
                logger.info("rawRecord.value(): {}", rawRecord.value());

                logger.info("rawRecord.sourceOffset: {}", rawRecord.sourceOffset());
                logger.info("lsn = {}, lsn_proc = {}, lsn_commit = {}", Lsn.valueOf((Long) (rawRecord.sourceOffset().get(SourceInfo.LSN_KEY))), Lsn.valueOf((Long) (rawRecord.sourceOffset().get("lsn_proc"))), Lsn.valueOf((Long) (rawRecord.sourceOffset().get(PostgresOffsetContext.LAST_COMMIT_LSN_KEY))));
                logger.info("rawRecord.sourcePartition: {}", rawRecord.sourcePartition());
                Envelope.Operation op = null;
                if (rawRecord.value() != null) {
                    op = Envelope.operationFor(rawRecord);
                }
                logger.info("op: {}", op);
                if (op == null) {
                    logger.info("it is heartbeat");
                } else{
                    Struct value = (Struct) rawRecord.value();
                    logger.info("rawRecord.value().schema.fields(): {}", value.getStruct(Envelope.FieldName.AFTER).schema().fields());
                }
                countRecord++;
                logger.info("countRecord = {}", countRecord);
                logger.info("========================");

                committer.markProcessed(record);
            }
            committer.markBatchFinished();
        }
    }

    static LinkedList<DebeziumOffset> queue = new LinkedList<>();
}
