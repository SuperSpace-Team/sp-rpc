package com.sp.rpc.registry;

import com.sp.rpc.registry.model.ServiceMeta;

import java.io.IOException;

/**
 * 通用注册中心接口
 *
 * @author luchao Created in 6/22/22 2:09 AM
 */
public interface RegistryService {
    /**
     * 注册节点
     * @param serviceMeta
     * @throws Exception
     */
    void register(ServiceMeta serviceMeta) throws Exception;

    /**
     * 下线节点
     * @param serviceMeta
     * @throws Exception
     */
    void unRegister(ServiceMeta serviceMeta) throws Exception;

    /**
     * 服务发现
     * @param serviceName
     * @param invokerHashCode
     * @return
     * @throws Exception
     */
    ServiceMeta discovery(String serviceName, int invokerHashCode) throws Exception;

    /**
     * 服务销毁
     * @throws IOException
     */
    void destroy() throws IOException;
}
