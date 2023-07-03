
package com.zy;

import io.debezium.connector.postgresql.SourceInfo;
import io.debezium.connector.postgresql.connection.Lsn;

import io.debezium.util.Collect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Map;

/**
 * 实际上该类并未被用到
 */
public class PostgresOffset {

    private static final Logger logger = LoggerFactory.getLogger(PostgresOffset.class);

    public static int compare(DebeziumOffset first, DebeziumOffset second) {
        Lsn firstLsn = Lsn.valueOf((Long) first.getSourceOffset().get(SourceInfo.LSN_KEY));
        Lsn secondLsn = Lsn.valueOf((Long) second.getSourceOffset().get(SourceInfo.LSN_KEY));
        return firstLsn.compareTo(secondLsn);
    }

    public PostgresPosition fromLSN(String engineName, Lsn lsn) throws SQLException, IOException {
        logger.info("fromLSN");
        final String PARTITION_KEY = "server";

        Map<String, String> partition = Collections.singletonMap(PARTITION_KEY, engineName);
        Map<String, Object> offset = Collect.hashMapOf(
                SourceInfo.LSN_KEY, lsn.asLong(),
                SourceInfo.TIMESTAMP_USEC_KEY, 0L //debezium pg 会对ts_usec做long转化，所以无法传null
        );

        PostgresPosition position = new PostgresPosition(engineName, partition, offset);

        return position;
    }
}