package com.zy;

import java.io.DataOutputStream;

import de.bytefish.pgbulkinsert.pgsql.handlers.BaseValueHandler;

/**
 * @author 匠承
 * @Date: 2023/6/28 14:38
 */
public class MyBitValueHandler extends BaseValueHandler<byte[]> {

    @Override
    protected void internalHandle(DataOutputStream buffer, final byte[] value) throws Exception {
        // 数据长度5字节
        buffer.writeInt(5);
        System.out.println("Write using MyBitValueHandler");
        buffer.write(value, 0, value.length);
    }

    @Override
    public int getLength(byte[] value) {
        return value.length;
    }
}
