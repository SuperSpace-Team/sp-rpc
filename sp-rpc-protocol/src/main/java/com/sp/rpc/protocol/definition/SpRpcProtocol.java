package com.sp.rpc.protocol.definition;

import lombok.Data;

import java.io.Serializable;

/**
 * 自定义RPC协议结构定义
 *
 * @author luchao Created in 6/26/22 11:45 AM
 */
@Data
public class SpRpcProtocol<T> implements Serializable {
    /**
     * 协议头
     */
    private MsgHeader msgHeader;

    /**
     * 协议体
     */
    private T body;
}
