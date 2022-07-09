package com.sp.rpc.provider;

import com.sp.rpc.core.utils.RpcServiceHelper;
import com.sp.rpc.provider.annotation.RpcService;
import com.sp.rpc.provider.config.RpcProperties;
import com.sp.rpc.registry.RegistryService;
import com.sp.rpc.registry.model.ServiceMeta;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * [Add Description Here]
 *
 * @author luchao Created in 6/22/22 2:02 AM
 */
@Slf4j
public class RpcProvider implements InitializingBean, BeanPostProcessor {
    /**
     * RPC服务地址
     */
    private String serverAddr;

    /**
     * RPC服务端口
     */
    private Integer serverPort;

    @Resource
    RpcProperties rpcProperties;

    private RegistryService serviceRegistry;

    public RpcProvider(Integer serverPort, RegistryService serviceRegistry) {
        this.serverPort = serverPort;
        this.serviceRegistry = serviceRegistry;
    }

    public void afterPropertiesSet() throws Exception {
        this.serverAddr = rpcProperties.getRegistryAddr();
    }

    private final Map<String, Object> rpcServiceMap = new HashMap<String, Object>();

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        //获取所有带有@RpcService注解的服务接口对象
        RpcService rpcService = bean.getClass().getAnnotation(RpcService.class);
        if (rpcService != null) {
            String serviceName = rpcService.serviceInterface().getName();
            String serviceVersion = rpcService.serviceVersion();

            try {
                ServiceMeta serviceMeta = new ServiceMeta();
                serviceMeta.setServerAddr(serverAddr);
                serviceMeta.setServerPort(serverPort);
                serviceMeta.setServerName(serviceName);
                serviceMeta.setServerVersion(serviceVersion);
                serviceRegistry.register(serviceMeta);

                //发布服务元数据到注册中心,在接收到RPC请求时直接取出对应的服务对象进行方法调用
                rpcServiceMap.put(RpcServiceHelper.buildServiceKey(
                        serviceMeta.getServerName(), serviceMeta.getServerVersion()), bean);
            } catch (Exception e) {
                log.error("Failed to register service {}#{}", serviceName, serviceVersion, e);
            }
        }

        return bean;
    }
}
