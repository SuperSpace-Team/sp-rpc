package com.sp.rpc.provider.config;

import com.sp.rpc.provider.RegistryFactory;
import com.sp.rpc.provider.RegistryService;
import com.sp.rpc.provider.RpcProvider;
import com.sp.rpc.provider.RpcServer;
import com.sp.rpc.provider.enums.RegistryType;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * RPC Provider自动配置类
 *
 * @author luchao Created in 6/22/22 1:43 AM
 */
@Configuration
@EnableConfigurationProperties(RpcProperties.class)
public class RpcProviderAutoConfiguration {

    @Resource
    private RpcProperties rpcProperties;

    @Bean
    public RpcProvider init() throws Exception {
        RegistryType type = RegistryType.valueOf(rpcProperties.getRegistryType());
        RegistryService serviceRegistry = RegistryFactory.getInstance(rpcProperties.getRegistryAddr(), type);
        return new RpcProvider(rpcProperties.getServicePort(), serviceRegistry);
    }


}
