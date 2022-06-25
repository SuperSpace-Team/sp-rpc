package com.sp.rpc.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * [Add Description Here]
 *
 * @author luchao Created in 6/22/22 1:57 AM
 */
@EnableConfigurationProperties
@SpringBootApplication
public class RpcProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(RpcProviderApplication.class, args);
    }
}
