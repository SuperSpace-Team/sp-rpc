package com.sp.rpc.provider.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * RPC Provider配置对象
 *
 * @author luchao Created in 6/22/22 1:34 AM
 */
@Data
@ConfigurationProperties(prefix = "sp.rpc")
public class RpcProperties {
    /**
     * 服务端口
     */
    private Integer servicePort;

    /**
     * 注册中心地址
     */
    private String registryAddr;

    /**
     * 注册中心类型
     */
    private String registryType;
}
