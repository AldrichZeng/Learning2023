
package com.zy;

import com.alibaba.fastjson.JSON;
import org.apache.kafka.connect.json.JsonConverter;
import org.apache.kafka.connect.runtime.WorkerConfig;
import org.apache.kafka.connect.storage.Converter;
import org.apache.kafka.connect.storage.MemoryOffsetBackingStore;
import org.apache.kafka.connect.storage.OffsetStorageWriter;
import org.apache.kafka.connect.util.Callback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class InitializableOffsetBackingStore extends MemoryOffsetBackingStore {

    private static final Logger logger = LoggerFactory.getLogger(InitializableOffsetBackingStore.class);

    public static final String OFFSET_INITIAL_POSITION_JSON = "offset.initial.position.json";
    private static final long FLUSH_TIMEOUT_SECONDS = 1;

    @Override
    public void configure(WorkerConfig config) {
        super.configure(config);

        // This serialized data is the raw position data, which is serialized by FastJson
        // Deserialize it will give a PostgresPosition object
        String serializedPosition = (String) config.originals().get(OFFSET_INITIAL_POSITION_JSON);
        if (serializedPosition == null) {
            logger.info("No initial offset was set");
            return;
        }

        PostgresPosition initialPosition = JSON.parseObject(serializedPosition, PostgresPosition.class);

        // Construct converters and offsetWriter
        String engineName = (String) config.originals().get("name");
        Converter keyConverter = new JsonConverter();
        Converter valueConverter = new JsonConverter();
        keyConverter.configure(config.originals(), true);
        Map<String, Object> valueConfigs = new HashMap<>(config.originals());
        valueConfigs.put("schemas.enable", false);
        valueConverter.configure(valueConfigs, true);
        OffsetStorageWriter offsetWriter = new OffsetStorageWriter(this, engineName, keyConverter, valueConverter);

        // Write offset
        try {
            offsetWriter.offset(initialPosition.getSourcePartition(engineName), initialPosition.getSourceOffset(engineName));
        } catch (Exception e) {
            logger.warn("Cannot write offset to InitialOffsetBackingStore. Configurations might be changed after last checkpoint. Ignoring offset initialization. ", e);
            return;
        }

        this.start();

        // Flush immediately
        if (!offsetWriter.beginFlush()) {
            // If nothing is needed to be flushed, there must be something wrong with the initialization
            logger.info("Offset is initialized with empty offset");
            return;
        }

        // Trigger flushing. This should be very fast since the destination is the memory
        Future<Void> flushFuture = offsetWriter.doFlush(new Callback<Void>() {
            @Override
            public void onCompletion(Throwable error, Void result) {
                if (error != null) {
                    logger.error("Failed to flush initial offset", error);
                } else {
                    logger.trace("Successfully flush initial offset");
                }
            }
        });

        // Wait until flushing finishes
        try {
            flushFuture.get(FLUSH_TIMEOUT_SECONDS, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            logger.warn("Flush of {} offsets interrupted, cancelling", this);
            offsetWriter.cancelFlush();
        } catch (ExecutionException e) {
            logger.error("Flush of {} offsets threw an unexpected exception: ", this, e);
            offsetWriter.cancelFlush();
        } catch (TimeoutException e) {
            logger.error("Timed out waiting to flush {} offsets to storage", this);
            offsetWriter.cancelFlush();
        }

        this.stop();

        // If we reach here, the OffsetBackingStore should be ready, and Debezium is ready to go

    }

}