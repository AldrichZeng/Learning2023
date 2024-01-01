package com.zy;

import java.sql.SQLException;
import java.util.function.Consumer;

import de.bytefish.pgbulkinsert.exceptions.BinaryWriteFailedException;
import de.bytefish.pgbulkinsert.pgsql.handlers.ValueHandlerProvider;
import de.bytefish.pgbulkinsert.row.SimpleRow;
import de.bytefish.pgbulkinsert.row.SimpleRowWriter;
import org.postgresql.PGConnection;

/**
 * @author 匠承
 * @Date: 2023/6/27 15:43
 */
public class MyRowWriter extends SimpleRowWriter {

    private final ValueHandlerProvider myprovider;
    public MyRowWriter(Table table, PGConnection connection) throws SQLException {
        super(table, connection);
        myprovider = new ValueHandlerProvider();
    }

    public MyRowWriter(Table table, PGConnection connection, boolean usePostgresQuoting) throws SQLException {
        super(table, connection, usePostgresQuoting);
        myprovider = new ValueHandlerProvider();
    }





}
