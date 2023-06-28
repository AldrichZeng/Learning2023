package com.zy;

import java.sql.SQLException;

import de.bytefish.pgbulkinsert.row.SimpleRowWriter;
import org.postgresql.PGConnection;

/**
 * @author 匠承
 * @Date: 2023/6/26 18:38
 */
public class SimpleRowWriterExt extends SimpleRowWriter {

    public SimpleRowWriterExt(Table table, PGConnection connection) throws SQLException {
        super(table, connection);
    }

    public SimpleRowWriterExt(Table table, PGConnection connection, boolean usePostgresQuoting) throws SQLException {
        super(table, connection, usePostgresQuoting);
    }

    public void test(){

    }
}
