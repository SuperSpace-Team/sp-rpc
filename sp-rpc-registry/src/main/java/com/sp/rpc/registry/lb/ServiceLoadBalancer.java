package com.sp.rpc.registry.lb;

import java.util.List;

/**
 * 服务负载均衡器
 *
 * @author luchao Created in 6/29/22 12:19 AM
 */
public interface ServiceLoadBalancer<T> {

    /**
     * 选择服务 节点
     *
     * @param servers  服务节点列表
     * @param hashCode 客户端对象的Hash Code
     * @return
     */
    T select(List<T> servers, int hashCode);
}
