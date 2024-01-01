package com.zy;

import java.util.List;

import io.debezium.engine.ChangeEvent;
import io.debezium.engine.DebeziumEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 匠承
 * @Date: 2023/7/13 10:13
 */
public class RecordConsumer implements DebeziumEngine.ChangeConsumer<ChangeEvent<String, String>> {
    private static final Logger logger = LoggerFactory.getLogger(RecordConsumer.class);

    @Override
    public void handleBatch(List<ChangeEvent<String, String>> records, DebeziumEngine.RecordCommitter<ChangeEvent<String, String>> committer) throws InterruptedException {
        for (ChangeEvent<String, String> record : records) {
            // Put the record in record queue
            logger.info("handle batch: {}", Utils.JSONFormat(record.value()));
            committer.markProcessed(record);
        }
        committer.markBatchFinished();
        logger.info("mark batch finished");
    }
}
