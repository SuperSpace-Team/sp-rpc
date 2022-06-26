package com.sp.rpc.protocol.common;

import com.sp.rpc.protocol.definition.SpRpcProtocol;
import com.sp.rpc.protocol.definition.SpRpcResponse;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * RPC请求Holder
 *
 * @author luchao Created in 6/26/22 6:29 PM
 */
public class SpRpcRequestHolder {
    public static final AtomicLong REQUEST_ID_GEN = new AtomicLong(0);

    public static Map<Long, SpRpcFuture<SpRpcResponse>> REQUEST_MAP = new ConcurrentHashMap();
}
