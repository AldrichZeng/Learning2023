package com.zy.model;

import io.debezium.connector.postgresql.connection.Lsn;
import lombok.Data;

/**
 * @author 匠承
 * @Date: 2024/9/8 14:08
 */
@Data
public class ReplicationSlot {
    private String slotName;

    private String plugin;

    private String slotType;

    private Boolean active;

    private Lsn restartLsn;

    private Lsn confirmFlushLsn;
}
