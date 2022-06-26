package com.sp.rpc.protocol.definition;

import lombok.Data;

import java.io.Serializable;

/**
 * RPC请求体对象定义
 *
 * @author luchao Created in 6/26/22 11:51 AM
 */
@Data
public class SpRpcRequest implements Serializable {
    /**
     * 服务版本
     */
    private String serviceVersion;

    /**
     * 服务接口名
     */
    private String className;

    /**
     * 服务方法名
     */
    private String methodName;

    /**
     * 方法参数列表
     */
    private Object[] params;

    /**
     * 方法参数类型列表
     */
    private Class<?>[] paramTypes;
}
