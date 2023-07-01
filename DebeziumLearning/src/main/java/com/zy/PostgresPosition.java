
package com.zy;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Different Debezium connectors have different format
 */
public class PostgresPosition extends Position {

    /**
     * Key: Engine name host:name:db
     * Value: Offset
     */
    private Map<String, LinkedList<DebeziumOffset>> engineWindows;
    private Map<String, DebeziumOffset> enginePostions;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    /* For JavaBean*/
    public Map<String, LinkedList<DebeziumOffset>> getEngineWindows() {
        return this.engineWindows;
    }
    public void setEngineWindows(Map<String, LinkedList<DebeziumOffset>> engineWindows) {
        this.engineWindows = engineWindows;
    }

    public Map<String, DebeziumOffset> getEnginePostions() {
        return enginePostions;
    }

    public void setEnginePostions(Map<String, DebeziumOffset> enginePostions) {
        this.enginePostions = enginePostions;
    }

    public PostgresPosition() {
        this.enginePostions = new HashMap<>();
        this.engineWindows = new HashMap<>();
    }

    public PostgresPosition(String engineName, Map<String, ?> sourcePartition, Map<String, ?> sourceOffset) {
        this.enginePostions = new HashMap<>();
        this.enginePostions.put(engineName, new DebeziumOffset(sourcePartition, sourceOffset));
    }

    public PostgresPosition(String engineName, Map<String, ?> sourcePartition, Map<String, ?> sourceOffset, LinkedList<DebeziumOffset> postionWindow) {
        this.enginePostions = new HashMap<>();
        this.engineWindows = new HashMap<>();
        this.enginePostions.put(engineName, new DebeziumOffset(sourcePartition, sourceOffset));
        this.engineWindows.put(engineName, (LinkedList<DebeziumOffset>)postionWindow.clone());
    }

    @Override
    public String merge(List<String> positions) {

        PostgresPosition mergedPosition = new PostgresPosition();

        for (String serializedPosition : positions) {

            // Deserialize position
            PostgresPosition position = JSON.parseObject(serializedPosition, this.getClass());

            for (Map.Entry<String, LinkedList<DebeziumOffset>> enginePosition : position.engineWindows.entrySet()) {
                if (!mergedPosition.engineWindows.containsKey(enginePosition.getKey())) {
                    mergedPosition.engineWindows.put(enginePosition.getKey(), enginePosition.getValue());

                } else {
                    DebeziumOffset existedOffset = mergedPosition.engineWindows.get(enginePosition.getKey()).getLast();
                    DebeziumOffset newOffset = enginePosition.getValue().getLast();
                    if (PostgresOffset.compare(existedOffset, newOffset) < 0) {
                        // Existed is less than current, should be replaced
                        mergedPosition.engineWindows.put(enginePosition.getKey(), enginePosition.getValue());
                    }
                }
            }

            for (Map.Entry<String, DebeziumOffset> enginePosition : position.enginePostions.entrySet()) {
                if (!mergedPosition.enginePostions.containsKey(enginePosition.getKey())) {
                    mergedPosition.enginePostions.put(enginePosition.getKey(), enginePosition.getValue());

                } else {
                    DebeziumOffset existedOffset = mergedPosition.enginePostions.get(enginePosition.getKey());
                    DebeziumOffset newOffset = enginePosition.getValue();
                    if (PostgresOffset.compare(existedOffset, newOffset) < 0) {
                        // Existed is less than current, should be replaced
                        mergedPosition.enginePostions.put(enginePosition.getKey(), enginePosition.getValue());
                    }
                }
            }
        }

        return JSON.toJSONString(mergedPosition);
    }

    public Map<String, ?> getSourcePartition(String engineName) {
        DebeziumOffset offset = this.enginePostions.get(engineName);
        if (offset == null) {
            throw new IllegalArgumentException("Invalid engine name " + engineName);
        }
        return offset.sourcePartition;
    }

    public Map<String, ?> getSourceOffset(String engineName) {
        DebeziumOffset offset = this.enginePostions.get(engineName);
        if (offset == null) {
            throw new IllegalArgumentException("Invalid engine name " + engineName);
        }
        return offset.sourceOffset;
    }

    public LinkedList<DebeziumOffset> getPostionWindow(String engineName) {
        if (!this.engineWindows.containsKey(engineName)) {
            throw new IllegalArgumentException("Invalid engine name " + engineName);
        }
        return this.engineWindows.get(engineName);
    }

}