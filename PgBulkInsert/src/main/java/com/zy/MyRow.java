package com.zy;

import java.util.Map;
import java.util.function.Function;

import de.bytefish.pgbulkinsert.pgsql.handlers.ValueHandlerProvider;
import de.bytefish.pgbulkinsert.row.SimpleRow;

/**
 * @author 匠承
 * @Date: 2023/6/27 15:41
 */
public class MyRow extends SimpleRow {

    public MyRow(ValueHandlerProvider provider, Map<String, Integer> lookup, Function<String, String> nullCharacterHandler) {
        super(provider, lookup, nullCharacterHandler);
    }


}
