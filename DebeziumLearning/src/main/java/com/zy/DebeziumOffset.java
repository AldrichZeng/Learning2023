package com.zy;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yubing.lyb@（十禹）
 * @date 2021/5/18 2:18 下午
 */
public class DebeziumOffset {

    public Map<String, ?> sourcePartition;
    public Map<String, ?> sourceOffset;
    public Long timestampMills;

    public DebeziumOffset() {}

    public DebeziumOffset(Map<String, ?> sourcePartition, Map<String, ?> sourceOffset) {
        this.sourcePartition = new HashMap<>(sourcePartition);
        this.sourceOffset = new HashMap<>(sourceOffset);
        this.timestampMills = null;
    }

    public DebeziumOffset(Map<String, ?> sourcePartition, Map<String, ?> sourceOffset, Long timestampMills) {
        this.sourcePartition = new HashMap<>(sourcePartition);
        this.sourceOffset = new HashMap<>(sourceOffset);
        this.timestampMills = timestampMills;
    }

    public Map<String, ?> getSourcePartition() {
        return this.sourcePartition;
    }

    public void setSourcePartition(Map<String, ?> sourcePartition) {
        this.sourcePartition = sourcePartition;
    }

    public Map<String, ?> getSourceOffset() {
        return this.sourceOffset;
    }

    public void setSourceOffset(Map<String, ?> sourceOffset) {
        this.sourceOffset = sourceOffset;
    }

    public Long getTimestampMills() {
        return timestampMills;
    }

    public void setTimestampMills(Long timestampMills) {
        this.timestampMills = timestampMills;
    }
}