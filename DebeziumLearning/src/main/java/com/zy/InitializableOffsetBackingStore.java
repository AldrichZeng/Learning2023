
package com.zy;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.alibaba.fastjson.JSON;

import org.apache.kafka.connect.json.JsonConverter;
import org.apache.kafka.connect.runtime.WorkerConfig;
import org.apache.kafka.connect.storage.Converter;
import org.apache.kafka.connect.storage.MemoryOffsetBackingStore;
import org.apache.kafka.connect.storage.OffsetStorageWriter;
import org.apache.kafka.connect.util.Callback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class InitializableOffsetBackingStore extends MemoryOffsetBackingStore {

    private static final Logger logger = LoggerFactory.getLogger(InitializableOffsetBackingStore.class);

    public static final String OFFSET_INITIAL_POSITION_JSON = "offset.initial.position.json";
    private static final long FLUSH_TIMEOUT_SECONDS = 1;

    @Override
    public void configure(WorkerConfig config) {
        logger.info("InitializableOffsetBackingStore正在读取配置: {}", config);
        super.configure(config);

        // This serialized data is the raw position data, which is serialized by FastJson
        // Deserialize it will give a PostgresPosition object
        String serializedPosition = (String) config.originals().get(OFFSET_INITIAL_POSITION_JSON);
        if (serializedPosition == null) {
            logger.info("No initial offset was set，这是不符合预期的");
            return;
        }

        logger.info("序列化initialPosition，原始字符串：{}", serializedPosition);
        PostgresPosition initialPosition = JSON.parseObject(serializedPosition, PostgresPosition.class);

        // Construct converters and offsetWriter
        String engineName = (String) config.originals().get("name");
        logger.info("===engineName = {}", engineName);
        Converter keyConverter = new JsonConverter();
        Converter valueConverter = new JsonConverter();
        keyConverter.configure(config.originals(), true);
        Map<String, Object> valueConfigs = new HashMap<>(config.originals());
        valueConfigs.put("schemas.enable", false);
        valueConverter.configure(valueConfigs, true);
        // 初始化一个OffsetStorageWriter，将其中的OffsetBackingStore设置为当前对象（MemoryOffsetBackingStore的子类）
        OffsetStorageWriter offsetWriter = new OffsetStorageWriter(this, engineName, keyConverter, valueConverter);

        // Write offset
        try {
            logger.info("初始化位点 sourcePartition: {}.", initialPosition.getSourcePartition(engineName));
            logger.info("初始化位点 sourceOffset: {}.", initialPosition.getSourceOffset(engineName));
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
                logger.info("Initializable Offset Backing Store, on Completion");
                if (error != null) {
                    logger.error("Failed to flush initial offset", error);
                } else {
                    logger.info("Successfully flush initial offset");
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
        logger.info("Initializable Offset Backing Store, stop");

        // If we reach here, the OffsetBackingStore should be ready, and Debezium is ready to go

    }

}