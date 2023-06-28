package com.zy;

import java.lang.reflect.Field;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import de.bytefish.pgbulkinsert.pgsql.constants.DataType;
import de.bytefish.pgbulkinsert.pgsql.handlers.ValueHandlerProvider;
import de.bytefish.pgbulkinsert.row.SimpleRow;
import de.bytefish.pgbulkinsert.row.SimpleRowWriter;
import org.postgresql.jdbc.PgConnection;

/**
 * @author 匠承
 * @Date: 2023/6/28 14:44
 */
public class BitTest {

    static String url = "jdbc:postgresql://pgm-uf6780sk00vfe752co.pg.rds.aliyuncs.com:5432/jctest";
    static String username = "jctest";
    static String password = "DWzengyao1234";

    public static void main(String[] args) throws SQLException {
        // Schema of the Table:
        String schemaName = "public";

        // Name of the Table:
        String tableName = "bit1_test";

        String columnName = "bit1_col";

        List<String> list = new ArrayList<>();
        list.add(columnName);

        String[] columnNames = list.toArray(new String[list.size()]);

        // Create the Table Definition:
        SimpleRowWriter.Table table = new SimpleRowWriter.Table(schemaName, tableName, columnNames);

        PgConnection conn = (PgConnection) DriverManager.getConnection(url, username, password);

        System.out.println("Connect successfully.");

        // Create the Writer:
        try {
            System.out.println("Start creating writer");
            SimpleRowWriter writer = new SimpleRowWriter(table, conn);

            // 通过反射 注入新的ValueHandlerProvider
            MyBitValueHandler myBitValueHandler = new MyBitValueHandler();
            ValueHandlerProvider valueHandlerProvider = new ValueHandlerProvider();
            valueHandlerProvider.add(DataType.Bit, myBitValueHandler);

            Class c1 = writer.getClass();

            Field field = c1.getDeclaredField("provider");
            field.setAccessible(true);
            field.set(writer, valueHandlerProvider);

            System.out.println("Start writing data");
            // ... write your data rows:
            for (int rowIdx = 0; rowIdx < 128; rowIdx++) {

                int finalRowIdx = rowIdx;
                writer.startRow(new Consumer<SimpleRow>() {
                    public void accept(SimpleRow simpleRow) {
                        byte[] data = new byte[1];
                        data[0]=0;
                        data[1]=0;
                        data[2]=0;

                        // 该字节表示有多少位，对应bit(n)的n
                        data[3]=1;

                        // 真实数据，取值范围是-128~127
                        data[4]= (byte) ((byte) -1 * finalRowIdx);
                        //data[4]= (byte) (finalRowIdx);
                        simpleRow.setValue(columnName, DataType.Bit, data);
                    }
                });
            }
            // 必须要close，否则不会写入
            writer.close();
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }
}
