package com.sp.rpc.protocol.definition;

import lombok.Data;

import java.io.Serializable;

/**
 * RPC协议头结构定义
 *
 * @author luchao Created in 6/26/22 11:46 AM
 */
@Data
public class MsgHeader implements Serializable {
    /**
     * 魔数
     */
    private Short magic;

    /**
     * 协议版本号
     */
    private Byte version;

    /**
     * 序列化算法
     */
    private Byte serialization;

    /**
     * 报文类型
     */
    private Byte msgType;

    /**
     * 消息状态
     */
    private Byte status;

    /**
     * 消息ID
     */
    private Long requestId;

    /**
     * 数据长度
     */
    private Integer msgLen;
}
