package com.zy;

import java.lang.reflect.Field;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;
import java.util.function.Consumer;

import de.bytefish.pgbulkinsert.pgsql.constants.DataType;
import de.bytefish.pgbulkinsert.pgsql.handlers.ValueHandlerProvider;
import de.bytefish.pgbulkinsert.row.SimpleRow;
import de.bytefish.pgbulkinsert.row.SimpleRowWriter;
import org.postgresql.jdbc.PgConnection;

/**
 * @author 匠承
 * @Date: 2023/6/26 11:53
 */
public class Main {
    public static void main(String[] args) throws SQLException {
        // Schema of the Table:
        String schemaName = "public";

        // Name of the Table:
        String tableName = "bit1_test";

        List<String> list = new ArrayList<>();
        list.add("bit1_col");
        //list.add("double_col");
        //list.add("boolean_col");
        //list.add("bool_col");

        // Define the Columns to be inserted:
        //String[] columnNames = new String[]{
        //        "bit1_col",
        //        "col"
        //};

        String[] columnNames = list.toArray(new String[list.size()]);

        // Create the Table Definition:
        SimpleRowWriter.Table table = new SimpleRowWriter.Table(schemaName, tableName, columnNames);
        //System.out.println(Arrays.asList(table.getColumns()));

        String url = "jdbc:postgresql://pgm-uf6780sk00vfe752co.pg.rds.aliyuncs.com:5432/jctest";
        String username = "jctest";
        String password = "DWzengyao1234";

        PgConnection conn = (PgConnection) DriverManager.getConnection(url, username, password);

        System.out.println("Connect successfully.");

        //System.out.println(conn.getMetaData().getSchemas());
        //
        //Statement stmt = conn.createStatement();
        //String sql = "SELECT id, col FROM test_output";
        //ResultSet rs = stmt.executeQuery(sql);

        // Create the Writer:
        try {
            System.out.println("Start creating writer");
            SimpleRowWriter writer = new SimpleRowWriter(table, conn);

            MyBitValueHandler myBitValueHandler = new MyBitValueHandler();
            ValueHandlerProvider valueHandlerProvider = new ValueHandlerProvider();
            valueHandlerProvider.add(DataType.Bit, myBitValueHandler);

            Class c1 = writer.getClass();

            Field field = c1.getDeclaredField("provider");
            field.setAccessible(true);
            field.set(writer, valueHandlerProvider);


            System.out.println("Start writing data");
            // ... write your data rows:
            for (int rowIdx = 0; rowIdx < 1; rowIdx++) {

                //rs.next();
                //final int id = rs.getInt("id");
                //final byte[] col = rs.getBytes("col");
                //System.out.println("ID = " + id + ", col = " + col.toString());
                // ... using startRow and work with the row, see how the order doesn't matter:
                writer.startRow(new Consumer<SimpleRow>() {
                    public void accept(SimpleRow simpleRow) {

                        //simpleRow.setInteger("id", id);
                        // simpleRow.setText("str_col", "Hi");
                        // 两种方式均可
                        //simpleRow.setDate("date_col", LocalDate.parse("2023-06-09"));

                        //Boolean[] boolArray1 = new Boolean[1];
                        //Boolean[] boolArray2 = new Boolean[8];

                        //byte[] a = new byte[]{1,0,1,1,1,0,1,0};
                        //simpleRow.setCollection("bit1_col", DataType.Bit, Arrays.asList(boolArray1));
                        //simpleRow.setCollection("bit8_col", DataType.Bit, Arrays.asList(boolArray2));
                        //simpleRow.setBoolean("boolean_col", true);
                        //simpleRow.setVarChar("char_col", "");
                        //simpleRow.setDouble("money_col", 9.9);
                        //simpleRow.setDouble("double_col", 9.9);
                        byte[] data = new byte[]{1,0,2,2};
                        simpleRow.setValue("bit1_col", DataType.Bit, data);
                    }
                });
            }
            // 必须要close，否则不会写入
            writer.close();
            //rs.close();
            //stmt.close();
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }
}
