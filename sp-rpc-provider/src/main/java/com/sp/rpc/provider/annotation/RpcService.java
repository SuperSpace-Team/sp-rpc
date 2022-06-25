package com.sp.rpc.provider.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * RPC服务引用注解
 *
 * @author luchao Created in 6/22/22 1:58 AM
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Component
public @interface RpcService {
    /**
     * 服务类型
     * @return
     */
    Class<?> serviceInterface() default Object.class;

    /**
     * 服务版本
     * @return
     */
    String serviceVersion() default "1.0";
}
