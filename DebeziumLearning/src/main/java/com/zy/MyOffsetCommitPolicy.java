package com.zy;

import java.time.Duration;

import io.debezium.engine.spi.OffsetCommitPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 匠承
 * @Date: 2023/7/13 10:19
 */
public class MyOffsetCommitPolicy implements OffsetCommitPolicy {
    private static final Logger logger = LoggerFactory.getLogger(MyOffsetCommitPolicy.class);

    public static int confirm_interval_second = 5;

    @Override
    public boolean performCommit(long numberOfMessagesSinceLastCommit, Duration timeSinceLastCommit) {
        logger.info("numberOfMessagesSinceLastCommit: " + numberOfMessagesSinceLastCommit + "\ttimeSinceLastCommit: " + timeSinceLastCommit.getSeconds());
        // 定义超过多长时间则confirm LSN
        if (timeSinceLastCommit.getSeconds() > confirm_interval_second) {
            logger.info("Do commit LSN");
            return true;
        } else {
            logger.info("Do not commit LSN");
            return false;
        }
    }
}
