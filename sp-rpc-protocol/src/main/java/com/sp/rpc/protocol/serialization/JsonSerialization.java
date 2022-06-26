package com.sp.rpc.protocol.serialization;

import lombok.Data;

import java.io.IOException;

/**
 * [Add Description Here]
 *
 * @author luchao Created in 6/26/22 4:59 PM
 */
@Data
public class JsonSerialization implements RpcSerialization {
    @Override
    public <T> byte[] serialize(T obj) throws IOException {
        return new byte[0];
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> clz) throws IOException {
        return null;
    }
}
