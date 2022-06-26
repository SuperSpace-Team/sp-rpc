package com.sp.rpc.protocol.definition;

import lombok.Data;

import java.io.Serializable;

/**
 * RPC响应体对象定义
 *
 * @author luchao Created in 6/26/22 11:53 AM
 */
@Data
public class SpRpcResponse implements Serializable {
    /**
     * RPC请求结果
     */
    private Object data;

    /**
     * RPC调用失败错误信息
     */
    private String message;
}
