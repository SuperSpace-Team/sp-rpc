package com.sp.rpc.consumer.annotation;

import org.springframework.beans.factory.annotation.Autowired;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * RPC请求引用注解
 *
 * @author luchao Created in 6/25/22 1:30 PM
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Autowired
public @interface RpcReference {
    /**
     * 服务版本
     * @return
     */
    String serviceVersion() default "1.0";

    /**
     * 注册中心类型
     * @return
     */
    String registryType() default "ZooKeeper";

    /**
     * 注册中心地址
     * @return
     */
    String registryAddr() default "localhost:2181";

    /**
     * 超时时长
     * @return
     */
    long timeout() default 5000;
}


