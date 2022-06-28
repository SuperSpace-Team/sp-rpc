package com.sp.rpc.registry.model;

import lombok.Data;

/**
 * 服务元数据
 *
 * @author luchao Created in 6/22/22 2:06 AM
 */
@Data
public class ServiceMeta {

    /**
     * RPC服务名称
     */
    private String serverName;

    /**
     * RPC服务版本
     */
    private String serverVersion;

    /**
     * RPC服务地址
     */
    private String serverAddr;

    /**
     * RPC服务端口
     */
    private Integer serverPort;
}
