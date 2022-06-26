package com.sp.rpc.protocol.serialization;

import java.io.IOException;

/**
 *自定义RPC序列化算法
 *
 * @author luchao Created in 6/26/22 11:56 AM
 */
public interface RpcSerialization {
    <T> byte[] serialize(T obj) throws IOException;

    <T> T deserialize(byte[] data, Class<T> clz) throws IOException;
}
