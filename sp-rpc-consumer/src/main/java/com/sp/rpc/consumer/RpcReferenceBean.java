package com.sp.rpc.consumer;

import com.sp.rpc.registry.RegistryFactory;
import com.sp.rpc.registry.RegistryService;
import com.sp.rpc.registry.RpcInvokerProxy;
import com.sp.rpc.registry.enums.RegistryType;
import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.Proxy;

/**
 * [Add Description Here]
 *
 * @author luchao Created in 6/23/22 12:41 AM
 */
public class RpcReferenceBean implements FactoryBean<Object> {
    private Class<?> interfaceClass;

    private String serviceVersion;

    private String registryType;

    private String registryAddr;

    private Long timeout;

    private Object object;

    public Object getObject(){
        return object;
    }

    public Class<?> getObjectType() {
        return interfaceClass;
    }

    public void init() throws Exception{
        //动态生成代理对象并赋值给object
        RegistryService registryService = RegistryFactory.getInstance(
                this.registryAddr, RegistryType.valueOf(this.registryType));
        this.object = Proxy.newProxyInstance(interfaceClass.getClassLoader(),new Class<?>[]{ interfaceClass },
                new RpcInvokerProxy(serviceVersion, timeout, registryService));
    }

    public void setInterfaceClass(Class<?> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    public void setServiceVersion(String serviceVersion) {
        this.serviceVersion = serviceVersion;
    }

    public void setRegistryType(String registryType) {
        this.registryType = registryType;
    }

    public void setRegistryAddr(String registryAddr) {
        this.registryAddr = registryAddr;
    }

//    public boolean isSingleton() {
//        return FactoryBean.super.isSingleton();
//    }

    public void setTimeout(Long timeout) {
        this.timeout = timeout;
    }
}
