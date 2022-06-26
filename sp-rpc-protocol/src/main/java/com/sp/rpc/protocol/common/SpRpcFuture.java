package com.sp.rpc.protocol.common;

import io.netty.util.concurrent.Promise;
import lombok.Data;

/**
 * 异步RPC Future定义
 * 用于SpRpcResponse设置响应结果
 *
 * @author luchao Created in 6/26/22 6:26 PM
 */
@Data
public class SpRpcFuture<T> {
    /**
     * 异步任务结果凭证对象
     * 实现RPC请求的同步等待,待执行完可通过get()获取到结果
     */
    private Promise<T> promise;
    private Long timeout;

    public SpRpcFuture(Promise promise) {
        this.promise = promise;
    }

    public Promise getPromise(){
        return promise;
    }

    public SpRpcFuture(Promise<T> promise, Long timeout) {
        this.promise = promise;
        this.timeout = timeout;
    }
}
